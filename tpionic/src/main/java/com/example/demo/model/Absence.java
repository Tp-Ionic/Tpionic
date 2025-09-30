package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "absences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, length = 500)
    private String raison;

    @Column(length = 1000)
    private String details;

    @Column(nullable = false)
    private String typeAbsence = "TEMPORAIRE"; // TEMPORAIRE, DEMENAGEMENT_DEFINITIF

    @Column(nullable = false)
    private String statut = "EN_ATTENTE"; // EN_ATTENTE, ACCEPTE, REFUSE

    @Column(nullable = false)
    private LocalDateTime dateDeclaration = LocalDateTime.now();

    @Column(length = 500)
    private String reponseAssociation;

    private LocalDateTime dateReponse;
}



