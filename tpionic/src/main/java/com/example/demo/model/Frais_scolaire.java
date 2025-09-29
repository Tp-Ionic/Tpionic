package com.example.demo.model;

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
public class Frais_scolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String classe;
    private String annee_scolaire;
    private int montant;


    @ManyToOne
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;
}
