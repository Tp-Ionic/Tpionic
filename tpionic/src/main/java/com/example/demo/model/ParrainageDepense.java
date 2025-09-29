package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "parrainage_depenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParrainageDepense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parrainage_id", nullable = false)
    private Parrainage parrainage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depense_id", nullable = false)
    private depense depense;

    @Column(nullable = false)
    private Double montantPriseEnCharge; // Montant que ce parrain prend en charge pour cette dépense

    @Column(nullable = false)
    private LocalDate datePriseEnCharge;

    private String statut; // EN_ATTENTE, CONFIRME, REFUSE
    private String notes;

    // Pourcentage de la dépense pris en charge par ce parrain
    @Column(nullable = false)
    private Double pourcentagePriseEnCharge;
}
