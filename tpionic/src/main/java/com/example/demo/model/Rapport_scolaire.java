package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rapport_scolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String annee_scolaire;
    private String urlBulletin;
    private String urlPhotoactivite;
    private String urlPresence;
    private String date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;
}