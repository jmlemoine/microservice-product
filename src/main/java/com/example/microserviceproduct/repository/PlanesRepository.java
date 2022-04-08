package com.example.microserviceproduct.repository;

import com.example.microserviceproduct.models.Planes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanesRepository extends JpaRepository<Planes, Long> {
}
