package com.example.demo.DTO;

import com.example.demo.model.Frais_scolaire;

import java.time.LocalDate;

public class dto_frais_scolaire {

    public static class CreateRequest {
        public String classe;
        public String annee_scolaire;
        public Double montant;
        public String description;
        public String statut;
        public LocalDate dateEcheance;
        public int enfantId;
    }

    public static class UpdateRequest {
        public String classe;
        public String annee_scolaire;
        public Double montant;
        public String description;
        public String statut;
        public LocalDate dateEcheance;
    }

    public static class Response {
        public Long id;
        public String classe;
        public String annee_scolaire;
        public Double montant;
        public String description;
        public String statut;
        public LocalDate dateEcheance;
        public int enfantId;
        public String enfantNom;
    }

    public static Response of(Frais_scolaire f) {
        Response r = new Response();
        r.id = f.getId();
        r.classe = f.getClasse();
        r.annee_scolaire = f.getAnnee_scolaire();
        r.montant = f.getMontant();
        r.description = f.getDescription();
        r.statut = f.getStatut();
        r.dateEcheance = f.getDateEcheance();
        r.enfantId = f.getEnfant() != null ? f.getEnfant().getId() : null;
        r.enfantNom = f.getEnfant() != null ? f.getEnfant().getNom() + " " + f.getEnfant().getPrenom() : null;
        return r;
    }

    public static void applyUpdate(Frais_scolaire f, UpdateRequest u) {
        if (u == null) return;
        if (u.classe != null) f.setClasse(u.classe);
        if (u.annee_scolaire != null) f.setAnnee_scolaire(u.annee_scolaire);
        if (u.montant != null) f.setMontant(u.montant);
        if (u.description != null) f.setDescription(u.description);
        if (u.statut != null) f.setStatut(u.statut);
        if (u.dateEcheance != null) f.setDateEcheance(u.dateEcheance);
    }
}
