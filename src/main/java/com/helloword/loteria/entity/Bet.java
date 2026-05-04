package com.helloword.loteria.entity;

import jakarta.persistence.*;
import java.util.List;

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

    public Bet() {}

    public Bet(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public Long getId() { return id; }
    public List<Integer> getNumbers() { return numbers; }
    public void setNumbers(List<Integer> numbers) { this.numbers = numbers; }

    @Override
    public String toString() {
        return numbers.toString();
    }
}