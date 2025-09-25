package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_parrainage {
    private int id;
    private int parrainId;
    private int enfantId;
    private String dateDebut;
    private int montantContribue;
    private boolean actif;
}