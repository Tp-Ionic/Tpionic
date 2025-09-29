package com.example.demo.DTO;

import com.example.demo.model.ParrainageDepense;

import java.time.LocalDate;
import java.util.List;

public class dto_parrainage_depense {

    public static class CreateRequest {
        public int parrainageId;
        public int depenseId;
        public Double montantPriseEnCharge;
        public Double pourcentagePriseEnCharge;
        public LocalDate datePriseEnCharge;
        public String statut;
        public String notes;
    }

    public static class UpdateRequest {
        public Double montantPriseEnCharge;
        public Double pourcentagePriseEnCharge;
        public LocalDate datePriseEnCharge;
        public String statut;
        public String notes;
    }

    public static class Response {
        public int id;
        public int parrainageId;
        public Integer parrainId;
        public String parrainNom;
        public int enfantId;
        public String enfantNom;
        public int depenseId;
        public String depenseDescription;
        public Double montantDepenseTotal;
        public Double montantPriseEnCharge;
        public Double pourcentagePriseEnCharge;
        public LocalDate datePriseEnCharge;
        public String statut;
        public String notes;
        public int associationId;
        public String associationNom;
    }

    public static class ResumeDepenseResponse {
        public int depenseId;
        public String depenseDescription;
        public Double montantTotal;
        public Double montantTotalPriseEnCharge;
        public Double montantRestant;
        public Double pourcentageCouvert;
        public int nombreParrains;
        public List<Response> prisesEnCharge;
    }

    public static Response of(ParrainageDepense pd) {
        Response r = new Response();
        r.id = pd.getId();
        r.parrainageId = pd.getParrainage() != null ? pd.getParrainage().getId() : null;
        r.parrainId = (pd.getParrainage() != null && pd.getParrainage().getParrain() != null) ? 
                pd.getParrainage().getParrain().getId() : null;
        r.parrainNom = (pd.getParrainage() != null && pd.getParrainage().getParrain() != null) ? 
                pd.getParrainage().getParrain().getNom() + " " + pd.getParrainage().getParrain().getPrenom() : null;
        r.enfantId = (pd.getParrainage() != null && pd.getParrainage().getEnfant() != null) ? 
                pd.getParrainage().getEnfant().getId() : null;
        r.enfantNom = (pd.getParrainage() != null && pd.getParrainage().getEnfant() != null) ? 
                pd.getParrainage().getEnfant().getNom() + " " + pd.getParrainage().getEnfant().getPrenom() : null;
        r.depenseId = pd.getDepense() != null ? pd.getDepense().getId() : null;
        r.depenseDescription = pd.getDepense() != null ? pd.getDepense().getDescription() : null;
        r.montantDepenseTotal = pd.getDepense() != null ? pd.getDepense().getMontant() : null;
        r.montantPriseEnCharge = pd.getMontantPriseEnCharge();
        r.pourcentagePriseEnCharge = pd.getPourcentagePriseEnCharge();
        r.datePriseEnCharge = pd.getDatePriseEnCharge();
        r.statut = pd.getStatut();
        r.notes = pd.getNotes();
        r.associationId = (pd.getParrainage() != null && pd.getParrainage().getAssociation() != null) ? 
                pd.getParrainage().getAssociation().getId() : null;
        r.associationNom = (pd.getParrainage() != null && pd.getParrainage().getAssociation() != null) ? 
                pd.getParrainage().getAssociation().getNom() : null;
        return r;
    }

    public static void applyUpdate(ParrainageDepense pd, UpdateRequest u) {
        if (u == null) return;
        if (u.montantPriseEnCharge != null) pd.setMontantPriseEnCharge(u.montantPriseEnCharge);
        if (u.pourcentagePriseEnCharge != null) pd.setPourcentagePriseEnCharge(u.pourcentagePriseEnCharge);
        if (u.datePriseEnCharge != null) pd.setDatePriseEnCharge(u.datePriseEnCharge);
        if (u.statut != null) pd.setStatut(u.statut);
        if (u.notes != null) pd.setNotes(u.notes);
    }
}
