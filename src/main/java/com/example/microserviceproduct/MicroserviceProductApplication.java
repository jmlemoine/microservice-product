package com.example.microserviceproduct;

import com.example.microserviceproduct.repository.PlanesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class MicroserviceProductApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlanesRepository planesRepository;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceProductApplication.class, args);
        System.out.println("Klk Product");
    }

    @Override
    public void run(String... args) throws Exception {

        String sqlQuery = "INSERT INTO planes (nombre, costo) VALUES (?, ?)";
        long count = planesRepository.count();

        if (count==0) {
            jdbcTemplate.update(sqlQuery, "Pre-Boda", 1000.00);
            jdbcTemplate.update(sqlQuery, "Boda", 5000.00);
            jdbcTemplate.update(sqlQuery, "Cumpleaños", 3000.00);
            jdbcTemplate.update(sqlQuery, "Vídeo de Evento", 4000.00);
        }

    }

}
