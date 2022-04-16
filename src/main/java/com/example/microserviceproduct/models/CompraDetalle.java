package com.example.microserviceproduct.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "compradetalle")
public class CompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "monto")
    private float monto;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compra_factura",
            joinColumns = @JoinColumn(name = "compradetalle_id"),
            inverseJoinColumns = @JoinColumn(name = "compra_id"))
    private Set<Compra> compradetalle = new HashSet<>();

    @Column(name = "idpaquete")
    private long idpaquete;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))*/

}
