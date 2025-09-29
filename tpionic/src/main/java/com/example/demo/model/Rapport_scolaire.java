package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "rapports_scolaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rapport_scolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String trimestre;
    private String annee_scolaire;
    private Double moyenne;
    private String appreciation;
    private LocalDate dateRapport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;
}

