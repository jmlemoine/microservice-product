package com.example.microserviceproduct.controller;

import com.example.microserviceproduct.models.Compra;
import com.example.microserviceproduct.models.Planes;
import com.example.microserviceproduct.models.Transaccion;
import com.example.microserviceproduct.repository.CompraRepository;
import com.example.microserviceproduct.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TransaccionController {

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    CompraRepository compraRepository;

    @GetMapping("/compras")
    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
        //return employeeRepository.findAll();
    }

    @PostMapping("/compras")
    public ResponseEntity<Compra> createCompra(@RequestBody Compra compra) {
    /*void registrarCompra()*/
        //Insert Into Compra
        try {
            Compra _compra = compraRepository
                .save(new Compra(compra.getUsername(), compra.getTotal()));
            return new ResponseEntity<>(_compra, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //listPlanes
        /*Set<Planes> listPlanes = new HashSet<>();;
        for (Planes plane : listPlanes) {
            //Insert Into CompraDetalle

        }*/
    }

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
