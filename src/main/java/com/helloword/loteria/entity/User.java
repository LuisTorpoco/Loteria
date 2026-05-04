package com.helloword.loteria.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id; // Requisito 1: ID único

    private String name;

    // Relación OneToMany: Un usuario, muchas apuestas
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Bet> bets = new ArrayList<>();

    public User() {}

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Cambiado a getApuestas para que el servicio de tu compañero funcione
    public List<Bet> getApuestas() {
        return bets;
    }

    public void setApuestas(List<Bet> bets) {
        this.bets = bets;
    }

    // Requisito 3.c: Muestra datos detallados cuando el nivel sea DEBUG
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bets=" + bets +
                '}';
    }
}