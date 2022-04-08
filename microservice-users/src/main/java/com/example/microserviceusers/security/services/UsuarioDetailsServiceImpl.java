package com.example.microserviceusers.security.services;

import com.example.microserviceusers.models.Usuarios;
import com.example.microserviceusers.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuariosRepository usuariosRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = usuariosRepository.findByUsername(username)
                 .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UsuarioDetailsImpl.build(usuarios);
    }

}
