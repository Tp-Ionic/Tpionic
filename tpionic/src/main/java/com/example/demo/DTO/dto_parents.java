package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_parents {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String email;
    private String password;
    private Boolean actif;
}