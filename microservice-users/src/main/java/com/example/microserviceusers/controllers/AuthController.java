package com.example.microserviceusers.controllers;

import com.example.microserviceusers.models.ERols;
import com.example.microserviceusers.models.Rols;
import com.example.microserviceusers.models.Usuarios;
import com.example.microserviceusers.payload.request.LoginRequest;
import com.example.microserviceusers.payload.request.SignupRequest;
import com.example.microserviceusers.payload.response.JwtResponse;
import com.example.microserviceusers.payload.response.MessageResponse;
import com.example.microserviceusers.repository.RolsRepository;
import com.example.microserviceusers.repository.UsuariosRepository;
import com.example.microserviceusers.security.jwt.JwtUtils;
import com.example.microserviceusers.security.services.UsuarioDetailsImpl;
import com.example.microserviceusers.security.services.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Component
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    RolsRepository rolsRepository;

    @Autowired
    PasswordEncoder encoder;

    //@Autowired
    @Autowired
    private JavaMailSender javaMailSender;

    //MimeMessage msg = null;

    @GetMapping("/usuarios")
    public List<Usuarios> getAllUsuarios() {
        return usuariosRepository.findAll();
        //return employeeRepository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> getTutorialById(@PathVariable("id") long id) {
        Optional<Usuarios> usuarioData = usuariosRepository.findById(id);
        if (usuarioData.isPresent()) {
            return new ResponseEntity<>(usuarioData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> updateUsuario(@PathVariable("id") long id, @RequestBody Usuarios usuario) {
        Optional<Usuarios> usuarioData = usuariosRepository.findById(id);
        if (usuarioData.isPresent()) {
            Usuarios _usuario = usuarioData.get();
            _usuario.setId(usuario.getId());
            _usuario.setUsername(usuario.getUsername());
            _usuario.setEmail(usuario.getEmail());
            return new ResponseEntity<>(usuariosRepository.save(_usuario), HttpStatus.OK);
            /*_tutorial.setTitle(usuario.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);*/
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") long id) {
        try {
            usuariosRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, IOException {

        if (usuariosRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (usuariosRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Usuarios usuarios = new Usuarios(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRols = signUpRequest.getRole();
        Set<Rols> rols = new HashSet<>();
        /*Rols adminRols = rolsRepository.findByName(ERols.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        if (signUpRequest.getEmail() == "popo@gmail.com") {

            rols.add(adminRols);

        }*/

        if (strRols == null) {
            if (signUpRequest.getEmail() == "popo@gmail.com") {
                Rols adminRols = rolsRepository.findByName(ERols.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                //rols.add(adminRol);
                rols.add(adminRols);

            }
            else {
                Rols userRol = rolsRepository.findByName(ERols.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                rols.add(userRol);
            }
        }
        else {
            strRols.forEach(rol -> {
                switch (rol) {
                    case "admin":
                        Rols adminRol = rolsRepository.findByName(ERols.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        rols.add(adminRol);

                        break;
                    case "mod":
                        Rols modRol = rolsRepository.findByName(ERols.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        rols.add(modRol);

                        break;
                    default:
                        Rols userRol = rolsRepository.findByName(ERols.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        rols.add(userRol);
                }
            });
        }

        usuarios.setRols(rols);
        usuariosRepository.save(usuarios);
        try {
            //sendEmail();
            sendEmailWithAttachment(signUpRequest.getEmail());
        } /*catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } */catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        //run();
        //sendEmailWithAttachment();

    }

    void sendEmailWithAttachment(String email) throws MessagingException, IOException {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(/*"moxy04@gmail.com"*/email);
            helper.setSubject("Testing from Spring Boot");
            // default = text/plain
            //helper.setText("Check attachment for image!");
            // true = text/html
            helper.setText("<h1>Check attachment for image!</h1>", true);
            helper.setText("<a href=\"http://localhost:4200/home\">Click here</a>", true);
            // hard coded a file path
            //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
            //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

}
