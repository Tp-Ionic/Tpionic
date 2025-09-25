package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dto_rapport_scolaire {
    private int id;
    private String anneeScolaire;
    private String urlBulletin;
    private String urlPhotoActivite;
    private String urlPresence;
    private String date;
    private int enfantId;
}