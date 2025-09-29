package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "frais_scolaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Frais_scolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classe;
    private String annee_scolaire;
    private Double montant;
    private String description;
    private String statut;
    private java.time.LocalDate dateEcheance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;
}
