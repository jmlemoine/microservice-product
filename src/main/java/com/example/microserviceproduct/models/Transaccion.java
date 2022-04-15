package com.example.microserviceproduct.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transactions")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "preboda")
    private float preboda;

    @Column(name = "boda")
    private float boda;

    @Column(name = "birthday")
    private float birthday;

    @Column(name = "evento")
    private float evento;

    @Column(name = "total")
    private float total;

    public Transaccion(String username, float preboda, float boda, float birthday, float evento, float total) {
        this.username = username;
        this.preboda = preboda;
        this.boda = boda;
        this.birthday = birthday;
        this.evento = evento;
        this.total = total;
    }
}
