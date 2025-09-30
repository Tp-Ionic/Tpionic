package com.example.demo.service;

import com.example.demo.DTO.dto_paiement;
import com.example.demo.model.paiement;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.ParrainRepository;
import com.example.demo.repository.EnfantRepository;
import com.example.demo.service.service_parrainage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class service_paiement {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private ParrainRepository parrainRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private service_parrainage parrainageService;

    @Transactional
    public dto_paiement.Response createPaiement(dto_paiement.CreateRequest request) {
        // Vérifier que le parrain existe
        Parrain parrain = parrainRepository.findById(request.parrainId)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé avec l'ID: " + request.parrainId));

        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        paiement paiement = new paiement();
        paiement.setMontant(request.montant);
        paiement.setTypePaiement(request.typePaiement);
        paiement.setDatePaiement(request.datePaiement);
        paiement.setReference(request.reference);
        paiement.setNotes(request.notes);
        paiement.setParrain(parrain);
        paiement.setEnfant(enfant);

        paiement savedPaiement = paiementRepository.save(paiement);
        
        // Vérifier s'il y a un parrainage en attente de paiement pour ce parrain et cet enfant
        try {
            parrainageService.validerParrainageApresPaiement(request.parrainId, request.enfantId);
        } catch (RuntimeException e) {
            // Pas de parrainage en attente, ce n'est pas une erreur
            // Le paiement est créé normalement
        }
        
        return dto_paiement.of(savedPaiement);
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getAllPaiements() {
        return paiementRepository.findAll()
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<dto_paiement.Response> getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .map(dto_paiement::of);
    }

    @Transactional
    public dto_paiement.Response updatePaiement(Long id, dto_paiement.UpdateRequest request) {
        paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé avec l'ID: " + id));

        dto_paiement.applyUpdate(paiement, request);
        paiement updatedPaiement = paiementRepository.save(paiement);
        return dto_paiement.of(updatedPaiement);
    }

    @Transactional
    public void deletePaiement(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new RuntimeException("Paiement non trouvé avec l'ID: " + id);
        }
        paiementRepository.deleteById(id);
    }

    // Méthodes de filtrage
    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByParrain(Integer parrainId) {
        return paiementRepository.findByParrainId(parrainId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByEnfant(Long enfantId) {
        return paiementRepository.findByEnfantId(enfantId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByType(String typePaiement) {
        return paiementRepository.findByTypePaiement(com.example.demo.Enumeration.type_paiement.valueOf(typePaiement))
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByDate(String date) {
        return paiementRepository.findByDatePaiement(java.time.LocalDate.parse(date))
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByPeriode(String dateDebut, String dateFin) {
        return paiementRepository.findByDatePaiementBetween(
                java.time.LocalDate.parse(dateDebut), 
                java.time.LocalDate.parse(dateFin))
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByAssociation(Long associationId) {
        return paiementRepository.findByEnfantAssociationId(associationId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByAssociationAndParrain(Long associationId, Integer parrainId) {
        return paiementRepository.findByEnfantAssociationIdAndParrainId(associationId, parrainId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByAssociationAndEnfant(Long associationId, Long enfantId) {
        return paiementRepository.findByEnfantAssociationIdAndEnfantId(associationId, enfantId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByAssociationAndType(Long associationId, String typePaiement) {
        return paiementRepository.findByEnfantAssociationIdAndTypePaiement(associationId, com.example.demo.Enumeration.type_paiement.valueOf(typePaiement))
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByAssociationAndPeriode(Long associationId, String dateDebut, String dateFin) {
        return paiementRepository.findByEnfantAssociationIdAndDatePaiementBetween(
                associationId,
                java.time.LocalDate.parse(dateDebut), 
                java.time.LocalDate.parse(dateFin))
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsByAssociationAndStatut(Long associationId, String statut) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner tous les paiements de l'association
        return paiementRepository.findByEnfantAssociationId(associationId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsValidesByAssociation(Long associationId) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner tous les paiements de l'association
        return paiementRepository.findByEnfantAssociationId(associationId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsNonValidesByAssociation(Long associationId) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner une liste vide
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsEnAttenteByAssociation(Long associationId) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner tous les paiements de l'association
        return paiementRepository.findByEnfantAssociationId(associationId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsRejetesByAssociation(Long associationId) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner une liste vide
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsConfirmesByAssociation(Long associationId) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner tous les paiements de l'association
        return paiementRepository.findByEnfantAssociationId(associationId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_paiement.Response> getPaiementsRecusByAssociation(Long associationId) {
        // Cette méthode n'est plus disponible car paiement n'a pas de statut direct
        // Retourner tous les paiements de l'association
        return paiementRepository.findByEnfantAssociationId(associationId)
                .stream()
                .map(dto_paiement::of)
                .toList();
    }
}
