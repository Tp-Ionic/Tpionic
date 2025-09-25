package com.example.demo.service;

import com.example.demo.DTO.dto_enfants;
import com.example.demo.DTO.dto_parents;
import com.example.demo.model.Enfant;
import com.example.demo.model.Rapport_scolaire;
import com.example.demo.model.Frais_scolaire;
import com.example.demo.model.Paiement;
import com.example.demo.model.Parent;
import com.example.demo.repository.EnfantRepository;
import com.example.demo.repository.RapportScolaireRepository;
import com.example.demo.repository.FraisScolaireRepository;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_parents {

    private final ParentRepository parentRepository;
    private final EnfantRepository enfantRepository;
    private final RapportScolaireRepository rapportScolaireRepository;
    private final FraisScolaireRepository fraisScolaireRepository;
    private final PaiementRepository paiementRepository;

    private boolean verifierParentEnfant(int enfantId, int parentId) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        return enfantOpt.isPresent() &&
                enfantOpt.get().getParent() != null &&
                enfantOpt.get().getParent().getId() == parentId;
    }

    public List<dto_enfants> getMesEnfants(int parentId) {
        List<Enfant> enfants = enfantRepository.findByParentId(parentId);

        return enfants.stream()
                .map(enfant -> new dto_enfants(
                        enfant.getId(),
                        enfant.getNom(),
                        enfant.getPrenom(),
                        enfant.getDateNaissance(),
                        enfant.getAdresse(),
                        enfant.getAge(),
                        enfant.getAprpos_de_enfants(),
                        enfant.getParent() != null ? enfant.getParent().getId() : null,
                        enfant.getAssociation() != null ? enfant.getAssociation().getId() : null
                ))
                .collect(Collectors.toList());
    }

    public Enfant getMonEnfant(int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            return enfantRepository.findById(enfantId).orElse(null);
        }
        return null;
    }

    public List<Rapport_scolaire> getRapportsScolairesEnfant(int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            return rapportScolaireRepository.findByEnfantId(enfantId);
        }
        return List.of();
    }

    public List<Frais_scolaire> getFraisScolairesEnfant(int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            return fraisScolaireRepository.findByEnfantId(enfantId);
        }
        return List.of();
    }

    public boolean confirmerPaiementParrain(int fraisId, int enfantId, int parentId) {
        if (verifierParentEnfant(enfantId, parentId)) {
            Optional<Paiement> paiementOpt = paiementRepository.findById(fraisId);
            if (paiementOpt.isPresent()) {
                Paiement paiement = paiementOpt.get();
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

    public boolean informerChangementSituation(int enfantId, int parentId, String nouvelleSituation) {
        if (verifierParentEnfant(enfantId, parentId)) {
            Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
            if (enfantOpt.isPresent()) {
                Enfant enfant = enfantOpt.get();
                enfant.setAprpos_de_enfants(nouvelleSituation);
                enfantRepository.save(enfant);
                return true;
            }
        }
        return false;
    }

    public Parent updateProfilParent(int parentId, dto_parents parentDTO) {
        Optional<Parent> parentOpt = parentRepository.findById(parentId);
        if (parentOpt.isPresent()) {
            Parent parent = parentOpt.get();
            parent.setNom(parentDTO.getNom());
            parent.setPrenom(parentDTO.getPrenom());
            parent.setTelephone(parentDTO.getTelephone());
            parent.setAdresse(parentDTO.getAdresse());
            parent.setEmail(parentDTO.getEmail());
            if (parentDTO.getPassword() != null && !parentDTO.getPassword().isEmpty()) {
                parent.setPassword(parentDTO.getPassword());
            }
            return parentRepository.save(parent);
        }
        return null;
    }

    public Optional<Parent> getParentById(int parentId) {
        return parentRepository.findById(parentId);
    }
}