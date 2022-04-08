package com.example.microserviceusers.repository;

import com.example.microserviceusers.models.ERols;
import com.example.microserviceusers.models.Rols;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolsRepository extends JpaRepository<Rols, Long> {

    Optional<Rols> findByName(ERols name);

}
