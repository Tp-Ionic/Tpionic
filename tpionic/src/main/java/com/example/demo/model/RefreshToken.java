package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
@OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Utilisateur user;
@Column(nullable = false)
private Instant dateCreation;
@Column(nullable = false)
   private Instant dateExpiration;
@Column(nullable = false)
private String token;


}

