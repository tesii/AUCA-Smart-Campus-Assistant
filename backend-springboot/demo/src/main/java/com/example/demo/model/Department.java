package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String description;
    public Object getName() {
    return name;    }

    public void setName(Object name2) {

        }
    public Object getCode() {
    return code;
    }
    public void setCode(Object code2) {
       
    }
    public Object getDescription() {
      return description;
    }
    public void setDescription(Object description2) {
       
    }

    // getters and setters
}