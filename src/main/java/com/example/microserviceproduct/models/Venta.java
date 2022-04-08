package com.example.microserviceproduct.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long codigo;

    @Column(name = "monto")
    private float monto;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "fecha_venta")
    private Date fecha_venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planes_id", referencedColumnName = "id")
    private Planes planes;

}
