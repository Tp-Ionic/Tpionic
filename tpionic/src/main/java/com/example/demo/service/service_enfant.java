package com.example.demo.service;

import com.example.demo.model.Enfant;
import com.example.demo.model.Rapport_scolaire;
import com.example.demo.model.Frais_scolaire;
import com.example.demo.model.paiement;
import com.example.demo.DTO.dto_enfant;
import com.example.demo.repository.EnfantRepository;
import com.example.demo.repository.RapportScolaireRepository;
import com.example.demo.repository.FraisScolaireRepository;
import com.example.demo.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class service_enfant {

    private final EnfantRepository enfantRepository;
    private final RapportScolaireRepository rapportScolaireRepository;
    private final FraisScolaireRepository fraisScolaireRepository;
    private final PaiementRepository paiementRepository;

    // Voir les rapports scolaires d'un enfant
    public List<Rapport_scolaire> getRapportsScolaires(int enfantId) {
        return rapportScolaireRepository.findByEnfantId(enfantId);
    }

    // Voir les frais scolaires d'un enfant
    public List<Frais_scolaire> getFraisScolaires(int enfantId) {
        return fraisScolaireRepository.findByEnfantId(enfantId);
    }

    // Confirmer un paiement
    public boolean confirmerPaiement(int fraisId) {
        Optional<paiement> paiementOpt = paiementRepository.findById(fraisId);
        if (paiementOpt.isPresent()) {
            paiement paiement = paiementOpt.get();
            paiement.setConfirme(true);
            paiement.setDateConfirmation(java.time.LocalDate.now());
            paiementRepository.save(paiement);
            return true;
        }
        return false;
    }

    // Mettre à jour le profil de l'enfant
    public Enfant updateProfil(int enfantId, dto_enfant enfantDTO) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        if (enfantOpt.isPresent()) {
            Enfant enfant = enfantOpt.get();

            // Mettre à jour les champs
            enfant.setNom(enfantDTO.getNom());
            enfant.setPrenom(enfantDTO.getPrenom());
            enfant.setDateNaissance(enfantDTO.getDateNaissance());
            enfant.setAdresse(enfantDTO.getAdresse());
            enfant.setEmail(enfantDTO.getEmail());
            enfant.setPassword(enfantDTO.getPassword());
            enfant.setAge(enfantDTO.getAge());
            enfant.setAprpos_de_enfants(enfantDTO.getAprpos_de_engants());

            return enfantRepository.save(enfant);
        }
        return null;
    }

    // Informer d'un changement de situation
    public void informerChangementSituation(int enfantId, String nouvelleSituation) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        if (enfantOpt.isPresent()) {
            Enfant enfant = enfantOpt.get();
            // Mettre à jour la situation dans le champ "aprops_de_enfants"
            enfant.setAprpos_de_enfants(nouvelleSituation);
            enfantRepository.save(enfant);

            // Ici, vous pourriez ajouter une logique pour notifier l'école
            // par email ou via un système de notification
        }
    }

    // Obtenir un enfant par son ID
    public Optional<Enfant> getEnfantById(int enfantId) {
        return enfantRepository.findById(enfantId);
    }
}