package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "confirmations_paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationPaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String statut; // RECU, CONFIRME, REJETE

    @Column(nullable = false)
    private LocalDate dateConfirmation;

    private String messageConfirmation;
    private String notes;
    private String justificatifUrl; // Reçu bancaire, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id", nullable = false)
    private Paiement paiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;
}
