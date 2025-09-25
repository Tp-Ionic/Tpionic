package com.example.demo.model;

import com.example.demo.DTO.DtoAdmin;
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
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends Utilisateur{
    private String nom;
    @OneToMany(mappedBy = "administrateur")
    private List<Association> Associations=new ArrayList<>();

    public DtoAdmin toDto() {
        return new DtoAdmin(
                this.nom,
                this.getEmail()
        );
    }
}
