package com.example.demo.model;

import com.example.demo.DTO.DtoUtilisateur;
import com.example.demo.Enumeration.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String email;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "actif")
    private Boolean actif = true;
    @Enumerated(EnumType.STRING)
    private Role Role;

    public DtoUtilisateur dtoUtilisateur(Utilisateur user){
        return new DtoUtilisateur(user.email, user.Role, user.actif);
    }
}