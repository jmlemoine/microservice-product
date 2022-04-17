package com.example.microserviceproduct.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*@Column(name = "idusuario")
    private long idusuario;*/

    @Column(name = "username")
    private String username;

    @Column(name = "total")
    private float total;

    public Compra(String username, float total) {
        this.username = username;
        this.total = total;
    }

    public Compra() {

    }
}
