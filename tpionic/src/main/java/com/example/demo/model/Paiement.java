package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String libelle;
    private String typePaiement;
    private LocalDate datePaiement;
    private int montant;
    private boolean confirme = false;
    private LocalDate dateConfirmation;

    @ManyToOne
    @JoinColumn(name = "enfant_id")
    private Enfant enfant;
}