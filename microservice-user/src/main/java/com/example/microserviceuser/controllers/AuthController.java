package com.example.microserviceuser.controllers;

import com.example.microserviceuser.models.ERol;
import com.example.microserviceuser.models.Rol;
import com.example.microserviceuser.models.Usuario;
import com.example.microserviceuser.payload.request.SignupRequest;
import com.example.microserviceuser.payload.request.LoginRequest;
import com.example.microserviceuser.payload.response.JwtResponse;
import com.example.microserviceuser.payload.response.MessageResponse;
import com.example.microserviceuser.repository.RolRepository;
import com.example.microserviceuser.repository.UsuarioRepository;
import com.example.microserviceuser.security.jwt.JwtUtils;
import com.example.microserviceuser.security.services.UsuarioDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) authentication.getPrincipal();
        List<String> roles = usuarioDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                usuarioDetails.getId(),
                usuarioDetails.getUsername(),
                usuarioDetails.getEmail(),
                roles));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (usuarioRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Usuario usuario = new Usuario(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRols = signUpRequest.getRole();
        Set<Rol> rols = new HashSet<>();

        if (strRols == null) {
            Rol userRol = rolRepository.findByName(ERol.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rols.add(userRol);
        }
        else {
            strRols.forEach(rol -> {
                switch (rol) {
                    case "admin":
                        Rol adminRol = rolRepository.findByName(ERol.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        rols.add(adminRol);

                        break;
                    case "mod":
                        Rol modRol = rolRepository.findByName(ERol.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        rols.add(modRol);

                        break;
                    default:
                        Rol userRol = rolRepository.findByName(ERol.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        rols.add(userRol);
                }
            });
        }

        usuario.setRols(rols);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }

}
