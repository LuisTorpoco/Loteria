package com.helloword.loteria.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    private String name;

    @ElementCollection
    private List<List<Integer>> apuestas=new ArrayList<>();

    protected User(String id,String name) {
        this.id = id;
        this.name = name;
    }

    public User(){

    }

}
