package com.example.configserv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigServer
@RestController
public class ConfigServApplication {

    @Value("${user.role:}")
    private String role;

    @Value("${user.password:}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(ConfigServApplication.class, args);
        System.out.println("Klk Config");
    }

    @GetMapping(
            value = "/whoami/{username}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        return String.format("Hello! You are %s and you'll become a(n) %s, " +
        "but only if your password is '%s'!\n",
                username, role, password);
    }

    /*@GetMapping(
            value = "/whoami/{username}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        return String.format("Hello! You are %s and you'll become a(n) %s...\n", username, role);
    }*/

}
