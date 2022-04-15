package com.example.microserviceproduct.repository;

import com.example.microserviceproduct.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
