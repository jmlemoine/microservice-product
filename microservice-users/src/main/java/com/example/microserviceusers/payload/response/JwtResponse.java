package com.example.microserviceusers.payload.response;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String name;
    private String lastname;
    private String role;
    private List<String> rols;

    /*public JwtResponse(String accessToken, Long id, String username, String email, List<String> rols) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.rols = rols;
    }*/

    public JwtResponse(String accessToken, Long id, String username, String email, String name, String lastname, String role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        //this.rols = rols;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return rols;
    }



}
