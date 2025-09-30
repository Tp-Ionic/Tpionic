package com.example.demo.DTO;

import com.example.demo.model.Absence;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class dto_absence {

    public static class CreateRequest {
        public Long parentId;
        public Long enfantId;
        public LocalDate dateDebut;
        public LocalDate dateFin;
        public String raison;
        public String details;
        public String typeAbsence = "TEMPORAIRE"; // TEMPORAIRE, DEMENAGEMENT_DEFINITIF
    }

    public static class UpdateRequest {
        public LocalDate dateDebut;
        public LocalDate dateFin;
        public String raison;
        public String details;
    }

    public static class Response {
        public Long id;
        public Long parentId;
        public String parentNom;
        public String parentPrenom;
        public Long enfantId;
        public String enfantNom;
        public String enfantPrenom;
        public Long associationId;
        public String associationNom;
        public LocalDate dateDebut;
        public LocalDate dateFin;
        public String raison;
        public String details;
        public String typeAbsence;
        public String statut;
        public LocalDateTime dateDeclaration;
        public String reponseAssociation;
        public LocalDateTime dateReponse;
    }

    public static class ValidationRequest {
        public String statut; // ACCEPTE, REFUSE
        public String reponseAssociation;
    }

    public static Response of(Absence a) {
        Response r = new Response();
        r.id = a.getId();
        r.parentId = (long) a.getParent().getId();
        r.parentNom = a.getParent().getNom();
        r.parentPrenom = a.getParent().getPrenom();
        r.enfantId = a.getEnfant().getId();
        r.enfantNom = a.getEnfant().getNom();
        r.enfantPrenom = a.getEnfant().getPrenom();
        r.associationId = a.getAssociation().getId();
        r.associationNom = a.getAssociation().getNom();
        r.dateDebut = a.getDateDebut();
        r.dateFin = a.getDateFin();
        r.raison = a.getRaison();
        r.details = a.getDetails();
        r.typeAbsence = a.getTypeAbsence();
        r.statut = a.getStatut();
        r.dateDeclaration = a.getDateDeclaration();
        r.reponseAssociation = a.getReponseAssociation();
        r.dateReponse = a.getDateReponse();
        return r;
    }

    public static void applyUpdate(Absence a, UpdateRequest u) {
        if (u == null) return;
        if (u.dateDebut != null) a.setDateDebut(u.dateDebut);
        if (u.dateFin != null) a.setDateFin(u.dateFin);
        if (u.raison != null) a.setRaison(u.raison);
        if (u.details != null) a.setDetails(u.details);
    }
}
