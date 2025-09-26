package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "associations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String pays;

    @Column(nullable = false)
    private String ville;

    private String description;
    private String logoUrl;
    private Boolean actif = true;

    @OneToMany(mappedBy = "association", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enfant> enfants = new ArrayList<>();
}
