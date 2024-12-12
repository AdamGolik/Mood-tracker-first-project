package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mood")
public class Mood {
    /**
     * Represents the unique identifier for the entity.
     * It is annotated with @Id indicating the primary key of the entity.
     * The value is automatically generated using the GenerationType.AUTO strategy.
     * This field is mapped to the "id" column in the database table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "mood") // nastrój
    private String mood;

    @Column(name = "description") // descriptcja
    private String description;

    @Column(name = "alcohol") // Zmieniono nazwę i kolumnę
    private String alcohol;

    @Column(name = "sugar") // cukier
    private String sugar;

    @Column(name = "health")
    private Integer health;

    @Column(name = "workout")
    private Integer workout;

    @Column(name = "sleep")
    private Integer sleep;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}