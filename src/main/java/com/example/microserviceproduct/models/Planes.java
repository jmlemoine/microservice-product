package com.example.microserviceproduct.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "planes")
public class Planes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "costo")
    private float costo;

    //Venta
    /*@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "planes", referencedColumnName = "id")
    private Venta venta;*/




}
