package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "alcohol") // Zmieniono nazwę i kolumnę
    private Boolean alcohol;

    @Column(name = "sugar")
    private Boolean sugar;

    @Column(name = "workout")
    private Boolean workout;

    @Column(name = "sleep")
    private Integer sleep;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}