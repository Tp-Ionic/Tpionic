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
public class Association extends Utilisateur {

    private String nom;
    private String adresse;
    private String numero_autorisation;

    @Enumerated(EnumType.STRING)
    private AssociationStatus status = AssociationStatus.EN_ATTENTE; // Nouveau champ

    @OneToMany(mappedBy = "association", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enfant> enfants = new ArrayList<>();

    public enum AssociationStatus {
        EN_ATTENTE,    // En attente de validation
        VALIDEE,       // Validée par l'admin
        REJETEE        // Rejetée par l'admin
    }
}