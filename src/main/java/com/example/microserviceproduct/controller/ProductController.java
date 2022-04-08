package com.example.microserviceproduct.controller;

import com.example.microserviceproduct.models.Planes;
import com.example.microserviceproduct.repository.PlanesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    PlanesRepository planesRepository;

    @GetMapping("/planes")
    public List<Planes> getAllPlanes() {
        return planesRepository.findAll();
        //return employeeRepository.findAll();
    }

}
