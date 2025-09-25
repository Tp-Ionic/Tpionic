
package com.example.demo.model;

import jakarta.persistence.*;
        import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "actif")
    private Boolean actif = true;
}