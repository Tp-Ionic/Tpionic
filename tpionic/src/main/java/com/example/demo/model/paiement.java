package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String libelle;

    private LocalDate datePaiement;

    private double montantTotal;

    private boolean confirme = false;

    private LocalDate dateConfirmation;

    @ManyToOne
    @JoinColumn(name = "enfant_id")
    private Enfant enfant;


}