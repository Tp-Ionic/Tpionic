package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_enfant {
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String adresse;
    private String email;
    private String password;
    private int age;
    private String aprpos_de_engants;
    private Integer associationId;
}