package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // ADMIN, STAFF, HEAD_OF_IT etc.

    public Object getName() {
      return name;
    }

    public void setName(Object name2) {
    
    }

    // getters and setters
}