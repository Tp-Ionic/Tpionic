package com.example.demo.service;

import com.example.demo.DTO.dto_depense;
import com.example.demo.model.depense;
import com.example.demo.model.paiement;
import com.example.demo.model.Enfant;
import com.example.demo.model.Association;
import com.example.demo.repository.DepenseRepository;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.EnfantRepository;
import com.example.demo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class service_depense {

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private AssociationRepository associationRepository;

    public dto_depense.Response createDepense(dto_depense.CreateRequest request) {
        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        // Vérifier que l'association existe
        Association association = associationRepository.findById(request.associationId)
                .orElseThrow(() -> new RuntimeException("Association non trouvée avec l'ID: " + request.associationId));

        depense depense = new depense();
        depense.setDescription(request.description);
        depense.setMontant(request.montant);
        depense.setTypeDepense(request.typeDepense);
        depense.setDateDepense(request.dateDepense);
        depense.setJustificatifUrl(request.justificatifUrl);
        depense.setNotes(request.notes);
        depense.setEnfant(enfant);
        depense.setAssociation(association);

        // Lier au paiement si spécifié
        if (request.paiementId != null) {
            paiement paiement = paiementRepository.findById(request.paiementId)
                    .orElseThrow(() -> new RuntimeException("Paiement non trouvé avec l'ID: " + request.paiementId));
            depense.setPaiement(paiement);
        }

        depense savedDepense = depenseRepository.save(depense);
        return dto_depense.of(savedDepense);
    }

    public List<dto_depense.Response> getAllDepenses() {
        return depenseRepository.findAll()
                .stream()
                .map(dto_depense::of)
                .toList();
    }

    public Optional<dto_depense.Response> getDepenseById(Long id) {
        return depenseRepository.findById(id)
                .map(dto_depense::of);
    }

    public dto_depense.Response updateDepense(Long id, dto_depense.UpdateRequest request) {
        depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée avec l'ID: " + id));

        dto_depense.applyUpdate(depense, request);
        depense updatedDepense = depenseRepository.save(depense);
        return dto_depense.of(updatedDepense);
    }

    public void deleteDepense(Long id) {
        if (!depenseRepository.existsById(id)) {
            throw new RuntimeException("Dépense non trouvée avec l'ID: " + id);
        }
        depenseRepository.deleteById(id);
    }

    // Nouveaux endpoints pour la traçabilité
    public List<dto_depense.Response> getDepensesByPaiement(Long paiementId) {
        return depenseRepository.findByPaiementId(paiementId)
                .stream()
                .map(dto_depense::of)
                .toList();
    }

    public List<dto_depense.Response> getDepensesByEnfant(Long enfantId) {
        return depenseRepository.findByEnfantId(enfantId)
                .stream()
                .map(dto_depense::of)
                .toList();
    }

    public List<dto_depense.Response> getDepensesByAssociation(Long associationId) {
        return depenseRepository.findByAssociationId(associationId)
                .stream()
                .map(dto_depense::of)
                .toList();
    }
}
