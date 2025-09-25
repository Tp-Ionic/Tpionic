package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_depense {
    private int id;
    private String libelle;
    private int montant;
    private String categorie;
    private LocalDate dateDepense;
}