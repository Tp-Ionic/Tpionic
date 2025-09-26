package com.example.demo.DTO;

import com.example.demo.model.Parrainage;

import java.time.LocalDate;

public class dto_parrainage {

    public static class CreateRequest {
        public Integer parrainId;     // Changé de Long à Integer
        public Long enfantId;         // Obligatoire - enfant à parrainer
        public Long associationId;    // Obligatoire - association qui fait la demande
        public Double montantMensuel; // Montant mensuel proposé
        public String messageDemande; // Message de la demande
        public String notes;          // Notes supplémentaires
    }

    public static class UpdateRequest {
        public String statut;          // Nouveau statut
        public LocalDate dateDebut;   // Date de début du parrainage
        public LocalDate dateFin;     // Date de fin du parrainage
        public Double montantMensuel; // Montant mensuel
        public String messageReponse; // Réponse du parrain
        public String notes;          // Notes supplémentaires
    }

    public static class Response {
        public Long id;
        public Integer parrainId;     // Changé de Long à Integer
        public String parrainNom;
        public Long enfantId;
        public String enfantNom;
        public Long associationId;
        public String associationNom;
        public String statut;
        public LocalDate dateDemande;
        public LocalDate dateDebut;
        public LocalDate dateFin;
        public Double montantMensuel;
        public String messageDemande;
        public String messageReponse;
        public String notes;
    }

    public static Response of(Parrainage p) {
        Response r = new Response();
        r.id = p.getId();
        r.parrainId = p.getParrain() != null ? p.getParrain().getId() : null;
        r.parrainNom = p.getParrain() != null ? p.getParrain().getNom() + " " + p.getParrain().getPrenom() : null;
        r.enfantId = p.getEnfant() != null ? p.getEnfant().getId() : null;
        r.enfantNom = p.getEnfant() != null ? p.getEnfant().getNom() + " " + p.getEnfant().getPrenom() : null;
        r.associationId = p.getAssociation() != null ? p.getAssociation().getId() : null;
        r.associationNom = p.getAssociation() != null ? p.getAssociation().getNom() : null;
        r.statut = p.getStatut();
        r.dateDemande = p.getDateDemande();
        r.dateDebut = p.getDateDebut();
        r.dateFin = p.getDateFin();
        r.montantMensuel = p.getMontantMensuel();
        r.messageDemande = p.getMessageDemande();
        r.messageReponse = p.getMessageReponse();
        r.notes = p.getNotes();
        return r;
    }

    public static void applyUpdate(Parrainage p, UpdateRequest u) {
        if (u == null) return;
        if (u.statut != null) p.setStatut(u.statut);
        if (u.dateDebut != null) p.setDateDebut(u.dateDebut);
        if (u.dateFin != null) p.setDateFin(u.dateFin);
        if (u.montantMensuel != null) p.setMontantMensuel(u.montantMensuel);
        if (u.messageReponse != null) p.setMessageReponse(u.messageReponse);
        if (u.notes != null) p.setNotes(u.notes);
    }
}
