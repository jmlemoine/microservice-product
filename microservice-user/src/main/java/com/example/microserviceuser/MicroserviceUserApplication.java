package com.example.microserviceuser;

import com.example.microserviceuser.models.ERol;
import com.example.microserviceuser.models.Rol;
import com.example.microserviceuser.models.Usuario;
import com.example.microserviceuser.repository.RolRepository;
import com.example.microserviceuser.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceUserApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    static UsuarioRepository usuarioRepository;

    @Autowired
    static PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceUserApplication.class, args);
        System.out.println("Klk User");

        //try {
            /*Usuario usuario = new Usuario("jean",
                    "jeanmelvinlp27@gmail.com" ,
                    /*encoder.encode(*//*"lolololo");//));
            //usuario.setRols(ERol.ROLE_USER);
            //usuarioRepository.save(usuario);
        /*}
        catch (NullPointerException npe) {
            npe.printStackTrace();
        }*/

    }

    @Override
    public void run(String... args) throws Exception {
        String sqlQuery = "INSERT INTO roles (name) VALUES (?)";
        String queryUsers = "INSERT INTO users (username, email, password, name, lastname) VALUES (?, ?, ?, ?, ?)";

        /*long userCount = usuarioRepository.count();
        System.out.println("Cantidad de Roles: "+userCount);
        if (userCount==0) {
            jdbcTemplate.update(queryUsers, "jean", "jeanmelvinlp27@gmail.com", "kkkkkkkk", "Jean", "Lemoine");
            //System.out.println("A new row has been inserted.");
        }*/

        /*Usuario u = new Usuario();
        u.setUsername("jean");
        u.setEmail("jeanmelvinlp27@gmail.com");
        u.setPassword("kkkkkkkk");
        u.setName("Jean");
        u.setLastname("Lemoine");
        usuariosRepository.save(u);*/

        /*Usuario usuario = new Usuario("jean",
                "jeanmelvinlp27@gmail.com" ,
                /*encoder.encode(*//*"lolololo");*/
        //usuarioRepository.save(usuario);

        //jdbcTemplate.update(queryUsers, "ROLE_MODERATOR");
        //int result = 0;
        /*if (result>0) {
            System.out.println("A new row has been inserted.");
        }*/

        long count = rolRepository.count();
        System.out.println("Cantidad de Roles: "+count);

        if (count==0) {
            jdbcTemplate.update(sqlQuery, "ROLE_USER");
            jdbcTemplate.update(sqlQuery, "ROLE_MODERATOR");
            jdbcTemplate.update(sqlQuery, "ROLE_ADMIN");
        }

    }

}
