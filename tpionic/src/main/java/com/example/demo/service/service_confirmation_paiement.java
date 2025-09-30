package com.example.demo.service;

import com.example.demo.DTO.dto_confirmation_paiement;
import com.example.demo.model.ConfirmationPaiement;
import com.example.demo.model.paiement;
import com.example.demo.model.Association;
import com.example.demo.repository.ConfirmationPaiementRepository;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class service_confirmation_paiement {

    @Autowired
    private ConfirmationPaiementRepository confirmationRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private AssociationRepository associationRepository;

    // Confirmer la réception d'un paiement
    @Transactional
    public dto_confirmation_paiement.Response confirmerPaiement(dto_confirmation_paiement.CreateRequest request) {
        // Vérifier que le paiement existe
        paiement paiement = paiementRepository.findById(request.paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé avec l'ID: " + request.paiementId));

        // Vérifier que l'association existe
        Association association = associationRepository.findById(request.associationId)
                .orElseThrow(() -> new RuntimeException("Association non trouvée avec l'ID: " + request.associationId));

        ConfirmationPaiement confirmation = new ConfirmationPaiement();
        confirmation.setStatut(request.statut != null ? request.statut : "RECU");
        confirmation.setDateConfirmation(LocalDate.now());
        confirmation.setMessageConfirmation(request.messageConfirmation);
        confirmation.setNotes(request.notes);
        confirmation.setJustificatifUrl(request.justificatifUrl);
        confirmation.setPaiement(paiement);
        confirmation.setAssociation(association);

        ConfirmationPaiement savedConfirmation = confirmationRepository.save(confirmation);
        return dto_confirmation_paiement.of(savedConfirmation);
    }

    // Lister toutes les confirmations
    @Transactional(readOnly = true)
    public List<dto_confirmation_paiement.Response> getAllConfirmations() {
        return confirmationRepository.findAll()
                .stream()
                .map(dto_confirmation_paiement::of)
                .toList();
    }

    // Obtenir une confirmation par ID
    @Transactional(readOnly = true)
    public Optional<dto_confirmation_paiement.Response> getConfirmationById(Long id) {
        return confirmationRepository.findById(id)
                .map(dto_confirmation_paiement::of);
    }

    // Obtenir les confirmations d'un paiement
    @Transactional(readOnly = true)
    public List<dto_confirmation_paiement.Response> getConfirmationsByPaiement(Long paiementId) {
        return confirmationRepository.findByPaiementId(paiementId)
                .stream()
                .map(dto_confirmation_paiement::of)
                .toList();
    }

    // Obtenir les confirmations d'une association
    @Transactional(readOnly = true)
    public List<dto_confirmation_paiement.Response> getConfirmationsByAssociation(Long associationId) {
        return confirmationRepository.findByAssociationId(associationId)
                .stream()
                .map(dto_confirmation_paiement::of)
                .toList();
    }

    // Obtenir les confirmations par statut
    @Transactional(readOnly = true)
    public List<dto_confirmation_paiement.Response> getConfirmationsByStatut(String statut) {
        return confirmationRepository.findByStatut(statut)
                .stream()
                .map(dto_confirmation_paiement::of)
                .toList();
    }

    // Modifier une confirmation
    @Transactional
    public dto_confirmation_paiement.Response updateConfirmation(Long id, dto_confirmation_paiement.UpdateRequest request) {
        ConfirmationPaiement confirmation = confirmationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Confirmation non trouvée avec l'ID: " + id));

        dto_confirmation_paiement.applyUpdate(confirmation, request);
        ConfirmationPaiement updatedConfirmation = confirmationRepository.save(confirmation);
        return dto_confirmation_paiement.of(updatedConfirmation);
    }

    // Supprimer une confirmation
    @Transactional
    public void deleteConfirmation(Long id) {
        if (!confirmationRepository.existsById(id)) {
            throw new RuntimeException("Confirmation non trouvée avec l'ID: " + id);
        }
        confirmationRepository.deleteById(id);
    }
}
