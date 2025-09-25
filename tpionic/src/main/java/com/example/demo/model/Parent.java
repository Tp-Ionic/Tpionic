package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parents")
public class Parent extends Utilisateur{

    private String telephone;
    private String adresse;

    // Relation avec les enfants
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Enfant> enfants = new ArrayList<>();
}