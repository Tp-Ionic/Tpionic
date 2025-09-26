package com.example.demo.DTO;

import com.example.demo.model.depense;
import com.example.demo.model.paiement;
import com.example.demo.Enumeration.type_paiement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class dto_paiement {

    public static class CreateRequest {
        public Double montant;
        public type_paiement typePaiement;
        public LocalDate datePaiement;
        public String reference;
        public String notes;
        public Integer parrainId;  // Changé de Long à Integer
        public Long enfantId;
    }

    public static class UpdateRequest {
        public Double montant;
        public type_paiement typePaiement;
        public LocalDate datePaiement;
        public String reference;
        public String notes;
    }

    public static class Response {
        public Long id;
        public Double montant;
        public type_paiement typePaiement;
        public LocalDate datePaiement;
        public String reference;
        public String notes;
        public Integer parrainId;  // Changé de Long à Integer
        public String parrainNom;
        public Long enfantId;
        public String enfantNom;
        public List<dto_depense.Response> depenses;  // Liste des dépenses financées
        public Double montantDepenseTotal;           // Total des dépenses
        public Double montantRestant;                // Montant restant non dépensé
    }

    public static Response of(paiement p) {
        Response r = new Response();
        r.id = p.getId();
        r.montant = p.getMontant();
        r.typePaiement = p.getTypePaiement();
        r.datePaiement = p.getDatePaiement();
        r.reference = p.getReference();
        r.notes = p.getNotes();
        r.parrainId = p.getParrain() != null ? p.getParrain().getId() : null;
        r.parrainNom = p.getParrain() != null ? p.getParrain().getNom() + " " + p.getParrain().getPrenom() : null;
        r.enfantId = p.getEnfant() != null ? p.getEnfant().getId() : null;
        r.enfantNom = p.getEnfant() != null ? p.getEnfant().getNom() + " " + p.getEnfant().getPrenom() : null;
        
        // Calcul des dépenses
        if (p.getDepenses() != null) {
            r.depenses = p.getDepenses().stream()
                    .map(dto_depense::of)
                    .collect(Collectors.toList());
            r.montantDepenseTotal = p.getDepenses().stream()
                    .mapToDouble(depense::getMontant)
                    .sum();
        } else {
            r.depenses = List.of();
            r.montantDepenseTotal = 0.0;
        }
        
        r.montantRestant = r.montant - r.montantDepenseTotal;
        
        return r;
    }

    public static void applyUpdate(paiement p, UpdateRequest u) {
        if (u == null) return;
        if (u.montant != null) p.setMontant(u.montant);
        if (u.typePaiement != null) p.setTypePaiement(u.typePaiement);
        if (u.datePaiement != null) p.setDatePaiement(u.datePaiement);
        if (u.reference != null) p.setReference(u.reference);
        if (u.notes != null) p.setNotes(u.notes);
    }
}