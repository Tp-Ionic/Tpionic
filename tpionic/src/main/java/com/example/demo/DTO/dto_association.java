package com.example.demo.DTO;

import com.example.demo.model.Association.AssociationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_association {
    private int id;
    private String nom;
    private String adresse;
    private String numero_autorisation;
    private String email;
    private String motDePasse;
    private Boolean actif;
    private AssociationStatus status;
}