package com.example.demo.DTO;

import com.example.demo.model.RecuDepense;

import java.time.LocalDate;

public class dto_recu_depense {

    public static class CreateRequest {
        public String description;
        public Double montant;
        public LocalDate dateDepense;
        public String justificatifUrl;
        public String notes;
        public Long depenseId;
        public Integer parrainId;
        public Long associationId;
    }

    public static class UpdateRequest {
        public String statut;
        public String notes;
    }

    public static class Response {
        public Long id;
        public String description;
        public Double montant;
        public LocalDate dateDepense;
        public String justificatifUrl;
        public String notes;
        public String statut;
        public Long depenseId;
        public String depenseDescription;
        public String enfantNom;
        public Integer parrainId;
        public String parrainNom;
        public Long associationId;
        public String associationNom;
    }

    public static Response of(RecuDepense r) {
        Response response = new Response();
        response.id = r.getId();
        response.description = r.getDescription();
        response.montant = r.getMontant();
        response.dateDepense = r.getDateDepense();
        response.justificatifUrl = r.getJustificatifUrl();
        response.notes = r.getNotes();
        response.statut = r.getStatut();
        
        // Relations
        response.depenseId = r.getDepense() != null ? r.getDepense().getId() : null;
        response.depenseDescription = r.getDepense() != null ? r.getDepense().getDescription() : null;
        response.enfantNom = r.getDepense() != null && r.getDepense().getEnfant() != null ?
            r.getDepense().getEnfant().getNom() + " " + r.getDepense().getEnfant().getPrenom() : null;
        response.parrainId = r.getParrain() != null ? r.getParrain().getId() : null;
        response.parrainNom = r.getParrain() != null ?
            r.getParrain().getNom() + " " + r.getParrain().getPrenom() : null;
        response.associationId = r.getAssociation() != null ? r.getAssociation().getId() : null;
        response.associationNom = r.getAssociation() != null ? r.getAssociation().getNom() : null;
        
        return response;
    }

    public static void applyUpdate(RecuDepense r, UpdateRequest u) {
        if (u == null) return;
        if (u.statut != null) r.setStatut(u.statut);
        if (u.notes != null) r.setNotes(u.notes);
    }
}
