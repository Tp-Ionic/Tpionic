package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parrainage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "parrain_id")
    private Parrain parrain;

    @ManyToOne
    @JoinColumn(name = "enfant_id")
    private Enfant enfant;


    private String dateDebut;
    private int montant_contribue;
}
