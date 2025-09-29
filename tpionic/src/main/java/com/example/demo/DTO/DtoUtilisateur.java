package com.example.demo.DTO;

import com.example.demo.Enumeration.Role;
import com.example.demo.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class DtoUtilisateur {
    private String email;
    private Role role;
    private boolean actif;

    public DtoUtilisateur dtoUtilisateur(Utilisateur user){
        return new DtoUtilisateur(user.getEmail(), user.getRole(), user.getActif());
    }
}
