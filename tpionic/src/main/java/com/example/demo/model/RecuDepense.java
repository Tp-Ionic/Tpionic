package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "recus_depense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuDepense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDate dateDepense;

    private String justificatifUrl; // Facture, reçu, etc.
    private String notes;
    private String statut; // ENVOYE, LU, CONFIRME

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depense_id", nullable = false)
    private depense depense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parrain_id", nullable = false)
    private Parrain parrain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;
}
