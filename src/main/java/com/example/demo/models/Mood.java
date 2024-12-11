package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mood")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "mood")
    private String mood;
    @Column(name = "description")
    private String description;
    @Column(name = "alkochol")
    private Boolean alkochol;
    @Column(name = "sugar")
    private Boolean sugar;
    @Column(name = "workout")
    private Boolean workout;
    @Column(name = "sleep")
    private Integer sleep;
    // Pole dodane dla identyfikacji właściciela `Mood`
    @Column(name = "user_id")
    private Long userId;
}
