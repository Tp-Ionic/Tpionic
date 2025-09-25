package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String adresse;
    private int age;
    private String aprpos_de_enfants;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parrainage> parrainages = new ArrayList<>();

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rapport_scolaire> rapportsScolaires = new ArrayList<>();

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Frais_scolaire> fraisScolaires = new ArrayList<>();

    @OneToMany(mappedBy = "enfant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiements = new ArrayList<>();
}