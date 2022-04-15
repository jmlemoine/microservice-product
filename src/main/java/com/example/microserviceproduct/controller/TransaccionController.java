package com.example.microserviceproduct.controller;

import com.example.microserviceproduct.models.Transaccion;
import com.example.microserviceproduct.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TransaccionController {

    @Autowired
    TransaccionRepository transaccionRepository;

    @PostMapping("/transaccions")
    public ResponseEntity<Transaccion> createTransaccion(@RequestBody Transaccion transaccion) {
        try {
            Transaccion _transaccion = transaccionRepository
                    .save(new Transaccion(transaccion.getUsername(), transaccion.getPreboda(),
                        transaccion.getBoda(), transaccion.getBirthday(), transaccion.getEvento(), transaccion.getTotal()));
            return new ResponseEntity<>(_transaccion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
