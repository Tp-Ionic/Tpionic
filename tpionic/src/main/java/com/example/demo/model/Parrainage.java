package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parrainage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parrainage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parrain_id")
    private Parrain parrain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id")
    private Enfant enfant;

    @Column(nullable = false)
    private String statut; // EN_ATTENTE_PAIEMENT, EN_ATTENTE, ACCEPTE, REFUSE, ACTIF, TERMINE

    @Column(nullable = false)
    private LocalDate dateDemande;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double montantMensuel;
    private String messageDemande;
    private String messageReponse;
    private String notes;

    // Relation avec l'association qui fait la demande
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id")
    private Association association;

    // Relation avec les dépenses prises en charge par ce parrainage
    @OneToMany(mappedBy = "parrainage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParrainageDepense> depensesPrisesEnCharge = new ArrayList<>();
}
