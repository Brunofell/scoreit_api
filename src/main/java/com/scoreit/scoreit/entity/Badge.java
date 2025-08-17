package com.scoreit.scoreit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "badges")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // Ex: "Primeira Review"
    private String description;  // Ex: "Você publicou sua primeira review!"
    private String code;    // Identificador, ex: "FIRST_REVIEW"

    public Badge() {}

    public Badge(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
