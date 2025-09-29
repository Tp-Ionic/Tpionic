package com.example.demo.DTO;

import com.example.demo.model.depense;
import com.example.demo.Enumeration.type_depense;

import java.time.LocalDate;

public class dto_depense {

    public static class CreateRequest {
        public String description;
        public Double montant;
        public type_depense typeDepense;
        public LocalDate dateDepense;
        public String justificatifUrl;
        public String notes;
        public int paiementId;        // Optionnel - pour lier à un Paiement spécifique
        public int enfantId;          // Obligatoire - enfant concerné
        public int associationId;      // Obligatoire - association qui fait la dépense
    }

    public static class UpdateRequest {
        public String description;
        public Double montant;
        public type_depense typeDepense;
        public LocalDate dateDepense;
        public String justificatifUrl;
        public String notes;
    }

    public static class Response {
        public int id;
        public String description;
        public Double montant;
        public type_depense typeDepense;
        public LocalDate dateDepense;
        public String justificatifUrl;
        public String notes;
        public int paiementId;
        public String paiementReference;
        public int enfantId;
        public String enfantNom;
        public int associationId;
        public String associationNom;
    }

    public static Response of(depense d) {
        Response r = new Response();
        r.id = d.getId();
        r.description = d.getDescription();
        r.montant = d.getMontant();
        r.typeDepense = d.getTypeDepense();
        r.dateDepense = d.getDateDepense();
        r.justificatifUrl = d.getJustificatifUrl();
        r.notes = d.getNotes();
        
        // Relations
        r.paiementId = d.getPaiement() != null ? d.getPaiement().getId() : null;
        r.paiementReference = d.getPaiement() != null ? d.getPaiement().getReference() : null;
        r.enfantId = d.getEnfant() != null ? d.getEnfant().getId() : null;
        r.enfantNom = d.getEnfant() != null ? d.getEnfant().getNom() + " " + d.getEnfant().getPrenom() : null;
        r.associationId = d.getAssociation() != null ? d.getAssociation().getId() : null;
        r.associationNom = d.getAssociation() != null ? d.getAssociation().getNom() : null;
        
        return r;
    }

    public static void applyUpdate(depense d, UpdateRequest u) {
        if (u == null) return;
        if (u.description != null) d.setDescription(u.description);
        if (u.montant != null) d.setMontant(u.montant);
        if (u.typeDepense != null) d.setTypeDepense(u.typeDepense);
        if (u.dateDepense != null) d.setDateDepense(u.dateDepense);
        if (u.justificatifUrl != null) d.setJustificatifUrl(u.justificatifUrl);
        if (u.notes != null) d.setNotes(u.notes);
    }
}