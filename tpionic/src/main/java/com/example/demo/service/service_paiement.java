package com.example.demo.service;

import com.example.demo.DTO.dto_paiement;
import com.example.demo.model.paiement;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.ParrainRepository;
import com.example.demo.repository.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return dto_paiement.of(savedPaiement);
    }

    public List<dto_paiement.Response> getAllPaiements() {
        return paiementRepository.findAll()
                .stream()
                .map(dto_paiement::of)
                .toList();
    }

    public Optional<dto_paiement.Response> getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .map(dto_paiement::of);
    }

    public dto_paiement.Response updatePaiement(Long id, dto_paiement.UpdateRequest request) {
        paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé avec l'ID: " + id));

        dto_paiement.applyUpdate(paiement, request);
        paiement updatedPaiement = paiementRepository.save(paiement);
        return dto_paiement.of(updatedPaiement);
    }

    public void deletePaiement(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new RuntimeException("Paiement non trouvé avec l'ID: " + id);
        }
        paiementRepository.deleteById(id);
    }
}
