package com.example.demo.DTO;

import com.example.demo.Enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class dtoUtilisateur {
    private String email;
    private Role role;
    private boolean actif;
}
