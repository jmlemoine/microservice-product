package com.example.microserviceusers.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Rols {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERols name;

    public Rols() {}

    public Rols(ERols name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERols getName() {
        return name;
    }

    public void setName(ERols name) {
        this.name = name;
    }

}
