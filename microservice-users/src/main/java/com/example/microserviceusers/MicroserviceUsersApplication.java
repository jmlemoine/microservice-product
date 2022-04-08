package com.example.microserviceusers;

import com.example.microserviceusers.models.ERols;
import com.example.microserviceusers.models.Rols;
import com.example.microserviceusers.models.Usuarios;
import com.example.microserviceusers.repository.RolsRepository;
import com.example.microserviceusers.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceUsersApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RolsRepository rolsRepository;

	@Autowired
	static UsuariosRepository usuariosRepository;

	@Autowired
	static PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUsersApplication.class, args);
		System.out.println("Klk User");
	}

	@Override
	public void run(String... args) throws Exception {

		String sqlQuery = "INSERT INTO roles (name) VALUES (?)";

		long count = rolsRepository.count();
		System.out.println("Cantidad de Roles: "+count);

		if (count==0) {
			jdbcTemplate.update(sqlQuery, "ROLE_USER");
			jdbcTemplate.update(sqlQuery, "ROLE_MODERATOR");
			jdbcTemplate.update(sqlQuery, "ROLE_ADMIN");
		}

		/*Usuarios usuarios = new Usuarios("Popo",
				"popo@gmail.com",
				encoder.encode("kokykoky"));
		usuarios.setUsername("Popo");
		usuarios.setEmail("popo@gmail.com");
		usuarios.setPassword("kokykoky");
		Rols adminRol = rolsRepository.findByName(ERols.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		Set<Rols> rols = new HashSet<>();
		rols.add(adminRol);
		usuarios.setRols(rols);
		usuariosRepository.save(usuarios);*/

	}

}
