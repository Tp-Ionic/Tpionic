package com.example.demo.service;

import com.example.demo.DTO.dto_parrainage_depense;
import com.example.demo.model.ParrainageDepense;
import com.example.demo.model.Parrainage;
import com.example.demo.model.depense;
import com.example.demo.repository.ParrainageDepenseRepository;
import com.example.demo.repository.ParrainageRepository;
import com.example.demo.repository.DepenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class service_parrainage_depense {

    @Autowired
    private ParrainageDepenseRepository parrainageDepenseRepository;

    @Autowired
    private ParrainageRepository parrainageRepository;

    @Autowired
    private DepenseRepository depenseRepository;

    // Créer une prise en charge de dépense par un parrainage
    public dto_parrainage_depense.Response creerPriseEnCharge(dto_parrainage_depense.CreateRequest request) {
        // Vérifier que le parrainage existe et est actif
        Parrainage parrainage = parrainageRepository.findById(request.parrainageId)
                .orElseThrow(() -> new RuntimeException("Parrainage non trouvé"));
        
        if (!"ACTIF".equals(parrainage.getStatut())) {
            throw new RuntimeException("Le parrainage doit être actif pour prendre en charge des dépenses");
        }

        // Vérifier que la dépense existe
        depense depense = depenseRepository.findById(request.depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

        // Vérifier que la dépense est pour le même enfant que le parrainage
        if (depense.getEnfant().getId() != (parrainage.getEnfant().getId())) {
            throw new RuntimeException("La dépense doit être pour le même enfant que le parrainage");
        }

        // Vérifier que le montant ne dépasse pas la dépense totale
        if (request.montantPriseEnCharge > depense.getMontant()) {
            throw new RuntimeException("Le montant pris en charge ne peut pas dépasser le montant total de la dépense");
        }

        // Calculer le pourcentage si non fourni
        if (request.pourcentagePriseEnCharge == null) {
            request.pourcentagePriseEnCharge = (request.montantPriseEnCharge / depense.getMontant()) * 100;
        }

        // Créer la prise en charge
        ParrainageDepense parrainageDepense = new ParrainageDepense();
        parrainageDepense.setParrainage(parrainage);
        parrainageDepense.setDepense(depense);
        parrainageDepense.setMontantPriseEnCharge(request.montantPriseEnCharge);
        parrainageDepense.setPourcentagePriseEnCharge(request.pourcentagePriseEnCharge);
        parrainageDepense.setDatePriseEnCharge(request.datePriseEnCharge != null ? request.datePriseEnCharge : LocalDate.now());
        parrainageDepense.setStatut(request.statut != null ? request.statut : "EN_ATTENTE");
        parrainageDepense.setNotes(request.notes);

        ParrainageDepense saved = parrainageDepenseRepository.save(parrainageDepense);
        return dto_parrainage_depense.of(saved);
    }

    // Obtenir toutes les prises en charge d'un parrainage
    public List<dto_parrainage_depense.Response> getPrisesEnChargeByParrainage(int parrainageId) {
        return parrainageDepenseRepository.findByParrainageId(parrainageId)
                .stream()
                .map(dto_parrainage_depense::of)
                .toList();
    }

    // Obtenir toutes les prises en charge d'un parrain
    public List<dto_parrainage_depense.Response> getPrisesEnChargeByParrain(Integer parrainId) {
        return parrainageDepenseRepository.findByParrainId(parrainId)
                .stream()
                .map(dto_parrainage_depense::of)
                .toList();
    }

    // Obtenir toutes les prises en charge pour un enfant
    public List<dto_parrainage_depense.Response> getPrisesEnChargeByEnfant(int enfantId) {
        return parrainageDepenseRepository.findByEnfantId(enfantId)
                .stream()
                .map(dto_parrainage_depense::of)
                .toList();
    }

    // Obtenir toutes les prises en charge pour une dépense
    public List<dto_parrainage_depense.Response> getPrisesEnChargeByDepense(int depenseId) {
        return parrainageDepenseRepository.findByDepenseId(depenseId)
                .stream()
                .map(dto_parrainage_depense::of)
                .toList();
    }

    // Modifier une prise en charge
    public dto_parrainage_depense.Response modifierPriseEnCharge(int id, dto_parrainage_depense.UpdateRequest request) {
        ParrainageDepense parrainageDepense = parrainageDepenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prise en charge non trouvée"));

        dto_parrainage_depense.applyUpdate(parrainageDepense, request);
        ParrainageDepense updated = parrainageDepenseRepository.save(parrainageDepense);
        return dto_parrainage_depense.of(updated);
    }

    // Supprimer une prise en charge
    public void supprimerPriseEnCharge(int id) {
        if (!parrainageDepenseRepository.existsById(id)) {
            throw new RuntimeException("Prise en charge non trouvée");
        }
        parrainageDepenseRepository.deleteById(id);
    }

    // Confirmer une prise en charge
    public dto_parrainage_depense.Response confirmerPriseEnCharge(int id) {
        ParrainageDepense parrainageDepense = parrainageDepenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prise en charge non trouvée"));

        parrainageDepense.setStatut("CONFIRME");
        ParrainageDepense updated = parrainageDepenseRepository.save(parrainageDepense);
        return dto_parrainage_depense.of(updated);
    }

    // Refuser une prise en charge
    public dto_parrainage_depense.Response refuserPriseEnCharge(int id) {
        ParrainageDepense parrainageDepense = parrainageDepenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prise en charge non trouvée"));

        parrainageDepense.setStatut("REFUSE");
        ParrainageDepense updated = parrainageDepenseRepository.save(parrainageDepense);
        return dto_parrainage_depense.of(updated);
    }

    // Obtenir le résumé des prises en charge pour une dépense
    public dto_parrainage_depense.ResumeDepenseResponse getResumeDepense(int depenseId) {
        depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

        List<ParrainageDepense> prisesEnCharge = parrainageDepenseRepository.findByDepenseId(depenseId);
        
        double montantTotalPriseEnCharge = prisesEnCharge.stream()
                .mapToDouble(ParrainageDepense::getMontantPriseEnCharge)
                .sum();
        
        double montantRestant = depense.getMontant() - montantTotalPriseEnCharge;

        dto_parrainage_depense.ResumeDepenseResponse resume = new dto_parrainage_depense.ResumeDepenseResponse();
        resume.depenseId = depenseId;
        resume.depenseDescription = depense.getDescription();
        resume.montantTotal = depense.getMontant();
        resume.montantTotalPriseEnCharge = montantTotalPriseEnCharge;
        resume.montantRestant = montantRestant;
        resume.pourcentageCouvert = (montantTotalPriseEnCharge / depense.getMontant()) * 100;
        resume.nombreParrains = prisesEnCharge.size();
        resume.prisesEnCharge = prisesEnCharge.stream()
                .map(dto_parrainage_depense::of)
                .toList();

        return resume;
    }
}
