package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enfants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String dateNaissance;

    @Column(nullable = false)
    private String adresse;

    private int age;
    private String apropos_de_enfant;

    // Champs pour les fichiers
    @Column(columnDefinition = "TEXT")
    private String bulletinsPdfUrls; // URLs des bulletins PDF séparés par virgule

    @Column(columnDefinition = "TEXT")
    private String photosActivitesUrls; // URLs des photos d'activités séparés par virgule

    @Column(columnDefinition = "TEXT")
    private String listesPresenceUrls; // URLs des listes de présence séparés par virgule

    // Relation avec l'association
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;

    // Relation avec le parent
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parrainage> parrainages = new ArrayList<>();

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rapport_scolaire> rapportsScolaires = new ArrayList<>();

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Frais_scolaire> fraisScolaires = new ArrayList<>();

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deplacement> deplacements = new ArrayList<>();
}
