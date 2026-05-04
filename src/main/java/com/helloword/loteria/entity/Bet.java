package com.helloword.loteria.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "bet_numbers", joinColumns = @JoinColumn(name = "bet_id"))
    @Column(name = "number")
    private List<Integer> numbers;

    // Constructor vacío requerido por JPA
    public Bet() {}

    // Constructor para crear apuestas desde el Service
    public Bet(List<Integer> numbers) {
        this.numbers = numbers;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<Integer> getNumbers() { return numbers; }
    public void setNumbers(List<Integer> numbers) { this.numbers = numbers; }

    /**
     *Estos metodos permiten que user.getApuestas().contains(nuevaApuesta)
     *funcione comparando los números y no la dirección de memoria.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(numbers, bet.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }

    // Requisito 3.c: Para que se vea bonito en el log de nivel DEBUG
    @Override
    public String toString() {
        return "Apuesta" + numbers;
    }
}