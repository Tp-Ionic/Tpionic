package com.example.demo.service;

import com.example.demo.DTO.dto_recu_depense;
import com.example.demo.model.RecuDepense;
import com.example.demo.model.depense;
import com.example.demo.model.Parrain;
import com.example.demo.model.Association;
import com.example.demo.repository.RecuDepenseRepository;
import com.example.demo.repository.DepenseRepository;
import com.example.demo.repository.ParrainRepository;
import com.example.demo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class service_recu_depense {

    @Autowired
    private RecuDepenseRepository recuRepository;

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private ParrainRepository parrainRepository;

    @Autowired
    private AssociationRepository associationRepository;

    // Créer un reçu de dépense
    public dto_recu_depense.Response creerRecuDepense(dto_recu_depense.CreateRequest request) {
        // Vérifier que la dépense existe
        depense depense = depenseRepository.findById(request.depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée avec l'ID: " + request.depenseId));

        // Vérifier que le parrain existe
        Parrain parrain = parrainRepository.findById(request.parrainId)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé avec l'ID: " + request.parrainId));

        // Vérifier que l'association existe
        Association association = associationRepository.findById(request.associationId)
                .orElseThrow(() -> new RuntimeException("Association non trouvée avec l'ID: " + request.associationId));

        RecuDepense recu = new RecuDepense();
        recu.setDescription(request.description);
        recu.setMontant(request.montant);
        recu.setDateDepense(request.dateDepense);
        recu.setJustificatifUrl(request.justificatifUrl);
        recu.setNotes(request.notes);
        recu.setStatut("ENVOYE");
        recu.setDepense(depense);
        recu.setParrain(parrain);
        recu.setAssociation(association);

        RecuDepense savedRecu = recuRepository.save(recu);
        return dto_recu_depense.of(savedRecu);
    }

    // Lister tous les reçus
    public List<dto_recu_depense.Response> getAllRecus() {
        return recuRepository.findAll()
                .stream()
                .map(dto_recu_depense::of)
                .toList();
    }

    // Obtenir un reçu par ID
    public Optional<dto_recu_depense.Response> getRecuById(int id) {
        return recuRepository.findById(id)
                .map(dto_recu_depense::of);
    }

    // Obtenir les reçus d'un parrain
    public List<dto_recu_depense.Response> getRecusByParrain(Integer parrainId) {
        return recuRepository.findByParrainId(parrainId)
                .stream()
                .map(dto_recu_depense::of)
                .toList();
    }

    // Obtenir les reçus d'une association
    public List<dto_recu_depense.Response> getRecusByAssociation(int associationId) {
        return recuRepository.findByAssociationId(associationId)
                .stream()
                .map(dto_recu_depense::of)
                .toList();
    }

    // Obtenir les reçus d'une dépense
    public List<dto_recu_depense.Response> getRecusByDepense(int depenseId) {
        return recuRepository.findByDepenseId(depenseId)
                .stream()
                .map(dto_recu_depense::of)
                .toList();
    }

    // Obtenir les reçus par statut
    public List<dto_recu_depense.Response> getRecusByStatut(String statut) {
        return recuRepository.findByStatut(statut)
                .stream()
                .map(dto_recu_depense::of)
                .toList();
    }

    // Modifier un reçu
    public dto_recu_depense.Response updateRecu(int id, dto_recu_depense.UpdateRequest request) {
        RecuDepense recu = recuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reçu non trouvé avec l'ID: " + id));

        dto_recu_depense.applyUpdate(recu, request);
        RecuDepense updatedRecu = recuRepository.save(recu);
        return dto_recu_depense.of(updatedRecu);
    }

    // Supprimer un reçu
    public void deleteRecu(int id) {
        if (!recuRepository.existsById(id)) {
            throw new RuntimeException("Reçu non trouvé avec l'ID: " + id);
        }
        recuRepository.deleteById(id);
    }
}
