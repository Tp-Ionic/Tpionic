package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class service_parent {

    private final ParentsRepository parentsRepository;
    private final EnfantRepository enfantRepository;
    private final RapportScolaireRepository rapportScolaireRepository;
    private final FraisScolaireRepository fraisScolaireRepository;
    private final PaiementRepository paiementRepository;

    // Vérifier que l'enfant appartient bien au parent
    private boolean verifierParentEnfant(int enfantId, int parentId) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);

        if (enfantOpt.isPresent()) {
            Enfant enfant = enfantOpt.get();
            return enfant.getParent() != null && enfant.getParent().getId() == parentId;
        }
        return false;
    }

    // Voir la liste de mes enfants
    public List<Enfant> getMesEnfants(int parentId) {
        return enfantRepository.findByParentId(parentId);
    }

    // Voir les informations d'un de mes enfants
    public Enfant getMonEnfant(int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            return enfantRepository.findById(enfantId).orElse(null);
        }
        return null;
    }

    // Voir les rapports scolaires de mon enfant
    public List<Rapport_scolaire> getRapportsScolairesEnfant(int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            return rapportScolaireRepository.findByEnfantId(enfantId);
        }
        return List.of();
    }

    // Voir les frais scolaires de mon enfant
    public List<Frais_scolaire> getFraisScolairesEnfant(int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            return fraisScolaireRepository.findByEnfantId(enfantId);
        }
        return List.of();
    }

    // Confirmer un paiement aux parrains
    public boolean confirmerPaiementParrain(int fraisId, int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            Optional<paiement> paiementOpt = paiementRepository.findById(fraisId);
            if (paiementOpt.isPresent()) {
                paiement paiement = paiementOpt.get();
                if (paiement.getEnfant().getId() == enfantId) {
                    paiement.setConfirme(true);
                    paiement.setDateConfirmation(java.time.LocalDate.now());
                    paiementRepository.save(paiement);
                    return true;
                }
            }
        }
        return false;
    }

    // Informer d'un changement de situation
    public boolean informerChangementSituation(int enfantId, int parentId, String nouvelleSituation) {
        if (verifierParentEnfant(enfantId, parentId)) {
            Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
            if (enfantOpt.isPresent()) {
                Enfant enfant = enfantOpt.get();
                enfant.setAprpos_de_enfants(nouvelleSituation);
                enfantRepository.save(enfant);

                notifierEcoleChangementSituation(enfant, nouvelleSituation);
                return true;
            }
        }
        return false;
    }

    private void notifierEcoleChangementSituation(Enfant enfant, String nouvelleSituation) {
        // Implémenter la logique de notification à l'école
        System.out.println("Notification à l'école: Changement de situation pour " +
                enfant.getPrenom() + " " + enfant.getNom() +
                " : " + nouvelleSituation);
    }

    // Méthode pour que le parent puisse voir son propre profil (lecture seule)
    public Optional<Parent> getParentById(int parentId) {
        return parentsRepository.findById(parentId);
    }
}