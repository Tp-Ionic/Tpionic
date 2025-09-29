package com.example.demo.DTO;

import com.example.demo.model.Rapport_scolaire;

import java.time.LocalDate;

public class dto_rapport_scolaire {

    public static class CreateRequest {
        public String trimestre;
        public String annee_scolaire;
        public Double moyenne;
        public String appreciation;
        public LocalDate dateRapport;
        public int enfantId;
    }

    public static class UpdateRequest {
        public String trimestre;
        public String annee_scolaire;
        public Double moyenne;
        public String appreciation;
        public LocalDate dateRapport;
    }

    public static class Response {
        public int id;
        public String trimestre;
        public String annee_scolaire;
        public Double moyenne;
        public String appreciation;
        public LocalDate dateRapport;
        public int enfantId;
        public String enfantNom;
    }

    public static Response of(Rapport_scolaire r) {
        Response response = new Response();
        response.id = r.getId();
        response.trimestre = r.getTrimestre();
        response.annee_scolaire = r.getAnnee_scolaire();
        response.moyenne = r.getMoyenne();
        response.appreciation = r.getAppreciation();
        response.dateRapport = r.getDateRapport();
        response.enfantId = r.getEnfant() != null ? r.getEnfant().getId() : null;
        response.enfantNom = r.getEnfant() != null ? r.getEnfant().getNom() + " " + r.getEnfant().getPrenom() : null;
        return response;
    }

    public static void applyUpdate(Rapport_scolaire r, UpdateRequest u) {
        if (u == null) return;
        if (u.trimestre != null) r.setTrimestre(u.trimestre);
        if (u.annee_scolaire != null) r.setAnnee_scolaire(u.annee_scolaire);
        if (u.moyenne != null) r.setMoyenne(u.moyenne);
        if (u.appreciation != null) r.setAppreciation(u.appreciation);
        if (u.dateRapport != null) r.setDateRapport(u.dateRapport);
    }
}