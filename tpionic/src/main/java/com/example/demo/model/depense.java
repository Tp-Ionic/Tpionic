package com.example.demo.model;

import com.example.demo.Enumeration.type_depense;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "depenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private type_depense typeDepense;

    @Column(nullable = false)
    private LocalDate dateDepense;

    private String justificatifUrl;
    private String notes;

    // Relation avec le Paiement qui finance cette dépense
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;

    // Relation avec l'enfant concerné
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;

    // Relation avec l'association qui fait la dépense
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;

    // Relation avec les reçus de dépense
    @OneToMany(mappedBy = "depense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecuDepense> recus = new ArrayList<>();
}
