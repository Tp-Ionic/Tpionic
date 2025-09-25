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
public class dto_paiement {
    private int id;
    private String libelle;
    private String typePaiement;
    private LocalDate datePaiement;
    private int montant;
    private LocalDate dateConfirmation;
    private boolean confirme;
    private int enfantId;
}