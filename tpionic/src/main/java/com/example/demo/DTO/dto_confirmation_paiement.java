package com.example.demo.DTO;

import com.example.demo.model.ConfirmationPaiement;
import com.example.demo.model.RecuDepense;

import java.time.LocalDate;

public class dto_confirmation_paiement {

    public static class CreateRequest {
        public String statut;
        public String messageConfirmation;
        public String notes;
        public String justificatifUrl;
        public Long paiementId;
        public Long associationId;
    }

    public static class UpdateRequest {
        public String statut;
        public String messageConfirmation;
        public String notes;
        public String justificatifUrl;
    }

    public static class Response {
        public Long id;
        public String statut;
        public LocalDate dateConfirmation;
        public String messageConfirmation;
        public String notes;
        public String justificatifUrl;
        public Long paiementId;
        public String paiementReference;
        public Double paiementMontant;
        public String parrainNom;
        public String enfantNom;
        public Long associationId;
        public String associationNom;
    }

    public static Response of(ConfirmationPaiement c) {
        Response r = new Response();
        r.id = c.getId();
        r.statut = c.getStatut();
        r.dateConfirmation = c.getDateConfirmation();
        r.messageConfirmation = c.getMessageConfirmation();
        r.notes = c.getNotes();
        r.justificatifUrl = c.getJustificatifUrl();
        
        // Relations
        r.paiementId = c.getPaiement() != null ? c.getPaiement().getId() : null;
        r.paiementReference = c.getPaiement() != null ? c.getPaiement().getReference() : null;
        r.paiementMontant = c.getPaiement() != null ? c.getPaiement().getMontant() : null;
        r.parrainNom = c.getPaiement() != null && c.getPaiement().getParrain() != null ?
            c.getPaiement().getParrain().getNom() + " " + c.getPaiement().getParrain().getPrenom() : null;
        r.enfantNom = c.getPaiement() != null && c.getPaiement().getEnfant() != null ?
            c.getPaiement().getEnfant().getNom() + " " + c.getPaiement().getEnfant().getPrenom() : null;
        r.associationId = c.getAssociation() != null ? c.getAssociation().getId() : null;
        r.associationNom = c.getAssociation() != null ? c.getAssociation().getNom() : null;
        
        return r;
    }

    public static void applyUpdate(ConfirmationPaiement c, UpdateRequest u) {
        if (u == null) return;
        if (u.statut != null) c.setStatut(u.statut);
        if (u.messageConfirmation != null) c.setMessageConfirmation(u.messageConfirmation);
        if (u.notes != null) c.setNotes(u.notes);
        if (u.justificatifUrl != null) c.setJustificatifUrl(u.justificatifUrl);
    }
}
