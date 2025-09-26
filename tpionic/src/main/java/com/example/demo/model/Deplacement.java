package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "deplacements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deplacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String motif;

    private String nouvelleAdresse;
    private String nouveauTelephone;
    private LocalDate dateDeplacement;
    private String statut; // EN_ATTENTE, VALIDE, REFUSE
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;
}
