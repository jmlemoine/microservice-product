package com.example.microserviceusers.security.services;

import com.example.microserviceusers.models.Usuarios;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    private String name;

    private String lastname;

    @JsonIgnore
    private String password;

    private String role;

    private Collection<? extends GrantedAuthority> authorities;

    /*public UsuarioDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }*/

    public UsuarioDetailsImpl(Long id, String username, String email, String password, String name, String lastname, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
    }

    public static UsuarioDetailsImpl build(Usuarios user) {

        /*List<GrantedAuthority> authorities = user.getRols().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());*/

        return new UsuarioDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getLastname(),
                user.getRole()
                /*authorities*/);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() { return id; }

    @Override
    public String getUsername() { return username; }

    public String getEmail() { return email; }

    @Override
    public String getPassword() { return password; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioDetailsImpl user = (UsuarioDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

}
