package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parrains")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Parrain extends Utilisateur {

    private String nom;
    private String prenom;

    private String telephone;
    private String pays;
    private String ville;
    private String avatarUrl;
}