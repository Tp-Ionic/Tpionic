package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_frais_scolaire {
    private int id;
    private String classe;
    private String anneeScolaire;
    private int montant;
    private int enfantId;
}