package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_parent {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String email;        // Hérité de Utilisateur
    private String motDePasse;   // Hérité de Utilisateur
    private Boolean actif;       // Hérité de Utilisateur
}