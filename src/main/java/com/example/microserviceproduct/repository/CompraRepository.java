package com.example.microserviceproduct.repository;

import com.example.microserviceproduct.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
}
