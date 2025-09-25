package com.example.demo.service;

import com.example.demo.DTO.dto_enfants;
import com.example.demo.model.*;
import com.example.demo.model.Association.AssociationStatus;
import com.example.demo.DTO.dto_association;
import com.example.demo.DTO.dto_parents;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_association {

    private final AssociationRepository associationRepository;
    private final ParentRepository parentRepository;
    private final EnfantRepository enfantRepository;
    private final ParrainRepository parrainRepository;
    private final ParrainageRepository parrainageRepository;
    private final PaiementRepository paiementRepository;
    private final FraisScolaireRepository fraisScolaireRepository;
    private final RapportScolaireRepository rapportScolaireRepository;
    private final DepenseRepository depenseRepository;

    // Créer une association (statut par défaut: EN_ATTENTE)
    public Association creerAssociation(dto_association associationDTO) {
        Association association = new Association();
        association.setNom(associationDTO.getNom());
        association.setAdresse(associationDTO.getAdresse());
        association.setNumero_autorisation(associationDTO.getNumero_autorisation());
        association.setEmail(associationDTO.getEmail());
        association.setPassword(associationDTO.getMotDePasse());
        association.setActif(associationDTO.getActif());
        association.setStatus(AssociationStatus.EN_ATTENTE); // Statut initial

        return associationRepository.save(association);
    }

    // Valider une association (par l'admin)
    public Association validerAssociation(int associationId) {
        Optional<Association> associationOpt = associationRepository.findById(associationId);
        if (associationOpt.isPresent()) {
            Association association = associationOpt.get();
            association.setStatus(AssociationStatus.VALIDEE);
            association.setActif(true); // Activer le compte
            return associationRepository.save(association);
        }
        throw new RuntimeException("Association non trouvée");
    }

    // Rejeter une association (par l'admin)
    public Association rejeterAssociation(int associationId) {
        Optional<Association> associationOpt = associationRepository.findById(associationId);
        if (associationOpt.isPresent()) {
            Association association = associationOpt.get();
            association.setStatus(AssociationStatus.REJETEE);
            association.setActif(false); // Désactiver le compte
            return associationRepository.save(association);
        }
        throw new RuntimeException("Association non trouvée");
    }

    // Lister les associations en attente de validation
    public List<Association> listerAssociationsEnAttente() {
        return associationRepository.findByStatus(AssociationStatus.EN_ATTENTE);
    }

    // Lister les associations validées
    public List<Association> listerAssociationsValidees() {
        return associationRepository.findByStatus(AssociationStatus.VALIDEE);
    }

    // Lister les associations rejetées
    public List<Association> listerAssociationsRejetees() {
        return associationRepository.findByStatus(AssociationStatus.REJETEE);
    }


    public Parent creerParent(dto_parents parentDTO) {
        Parent parent = new Parent();
        parent.setNom(parentDTO.getNom());
        parent.setPrenom(parentDTO.getPrenom());
        parent.setTelephone(parentDTO.getTelephone());
        parent.setAdresse(parentDTO.getAdresse());
        parent.setEmail(parentDTO.getEmail());
        parent.setPassword(parentDTO.getPassword());
        parent.setActif(parentDTO.getActif());
        return parentRepository.save(parent);
    }

    public List<Enfant> listerTousLesEnfants() {
        return enfantRepository.findAll();
    }

    public List<Parent> listerTousLesParents() {
        return parentRepository.findAll();
    }

    public List<Enfant> listerEnfantsParraines() {
        List<Parrainage> parrainagesActifs = parrainageRepository.findByActif(true);
        return parrainagesActifs.stream()
                .map(Parrainage::getEnfant)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Enfant> listerEnfantsNonParraines() {
        List<Enfant> tousLesEnfants = enfantRepository.findAll();
        List<Enfant> enfantsParraines = listerEnfantsParraines();
        return tousLesEnfants.stream()
                .filter(enfant -> !enfantsParraines.contains(enfant))
                .collect(Collectors.toList());
    }

    public List<Parrain> listerTousLesParrains() {
        return parrainRepository.findAll();
    }

    public boolean demanderTransfertFonds(int enfantId, int montant, String motif) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        if (enfantOpt.isPresent()) {
            System.out.println("Demande de transfert de " + montant + " pour l'enfant " + enfantId + " : " + motif);
            return true;
        }
        return false;
    }

    public Paiement enregistrerPaiement(int enfantId, int montant, String typePaiement) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        if (enfantOpt.isPresent()) {
            Paiement paiement = new Paiement();
            paiement.setEnfant(enfantOpt.get());
            paiement.setMontant(montant);
            paiement.setTypePaiement(typePaiement);
            paiement.setDatePaiement(java.time.LocalDate.now());
            paiement.setConfirme(false);
            return paiementRepository.save(paiement);
        }
        return null;
    }

    public Depense enregistrerDepense(String libelle, int montant, String categorie) {
        Depense depense = new Depense();
        depense.setLibelle(libelle);
        depense.setMontant(montant);
        depense.setCategorie(categorie);
        depense.setDateDepense(java.time.LocalDate.now());
        return depenseRepository.save(depense);
    }

    public Rapport_scolaire genererRapportScolaire(int enfantId, String anneeScolaire, String bulletinUrl,
                                                   String urlPhotoactivite, String urlPresence) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        if (enfantOpt.isPresent()) {
            Rapport_scolaire rapport = new Rapport_scolaire();
            rapport.setEnfant(enfantOpt.get());
            rapport.setAnnee_scolaire(anneeScolaire);
            rapport.setUrlBulletin(bulletinUrl);
            rapport.setUrlPhotoactivite(urlPhotoactivite);
            rapport.setUrlPresence(urlPresence);
            rapport.setDate(java.time.LocalDate.now().toString());

            return rapportScolaireRepository.save(rapport);
        }
        return null;
    }

    public String genererRapportFinancier(String periode) {
        List<Paiement> paiements = paiementRepository.findAll();
        List<Frais_scolaire> frais = fraisScolaireRepository.findAll();
        List<Depense> depenses = depenseRepository.findAll();

        int totalPaiements = paiements.stream().mapToInt(Paiement::getMontant).sum();
        int totalFrais = frais.stream().mapToInt(Frais_scolaire::getMontant).sum();
        int totalDepenses = depenses.stream().mapToInt(Depense::getMontant).sum();

        return "Rapport financier pour " + periode +
                "\nTotal des paiements: " + totalPaiements +
                "\nTotal des frais: " + totalFrais +
                "\nTotal des dépenses: " + totalDepenses +
                "\nSolde: " + (totalPaiements - totalFrais - totalDepenses);
    }

    public boolean faireDemandeParrainage(int enfantId, String description) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(enfantId);
        if (enfantOpt.isPresent()) {
            System.out.println("Demande de parrainage pour l'enfant " + enfantId + " : " + description);
            return true;
        }
        return false;
    }

    public Association updateProfil(int associationId, dto_association associationDTO) {
        Optional<Association> associationOpt = associationRepository.findById(associationId);
        if (associationOpt.isPresent()) {
            Association association = associationOpt.get();
            association.setNom(associationDTO.getNom());
            association.setAdresse(associationDTO.getAdresse());
            association.setNumero_autorisation(associationDTO.getNumero_autorisation());
            association.setEmail(associationDTO.getEmail());
            association.setPassword(associationDTO.getMotDePasse());
            // Ne pas modifier le statut via cette méthode
            return associationRepository.save(association);
        }
        return null;
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
            parent.setPassword(parentDTO.getPassword());
            parent.setActif(parentDTO.getActif());
            return parentRepository.save(parent);
        }
        return null;
    }
    public Enfant creerEnfant(dto_enfants enfantDTO) {
        Enfant enfant = new Enfant();
        enfant.setNom(enfantDTO.getNom());
        enfant.setPrenom(enfantDTO.getPrenom());
        enfant.setDateNaissance(enfantDTO.getDateNaissance());
        enfant.setAdresse(enfantDTO.getAdresse());
        enfant.setAge(enfantDTO.getAge());
        enfant.setAprpos_de_enfants(enfantDTO.getAprpos_de_enfants());

        // Lier l'enfant au parent
        if (enfantDTO.getParentId() != null) {
            Optional<Parent> parentOpt = parentRepository.findById(enfantDTO.getParentId());
            if (parentOpt.isPresent()) {
                enfant.setParent(parentOpt.get());
            } else {
                throw new RuntimeException("Parent non trouvé avec l'ID: " + enfantDTO.getParentId());
            }
        }

        // Lier l'enfant à l'association (optionnel)
        if (enfantDTO.getAssociationId() != null) {
            Optional<Association> associationOpt = associationRepository.findById(enfantDTO.getAssociationId());
            if (associationOpt.isPresent()) {
                enfant.setAssociation(associationOpt.get());
            }
        }

        return enfantRepository.save(enfant);
    }
}