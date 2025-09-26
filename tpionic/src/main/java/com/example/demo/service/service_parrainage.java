package com.example.demo.service;

import com.example.demo.DTO.dto_parrainage;
import com.example.demo.model.Parrainage;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
import com.example.demo.model.Association;
import com.example.demo.repository.ParrainageRepository;
import com.example.demo.repository.ParrainRepository;
import com.example.demo.repository.EnfantRepository;
import com.example.demo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class service_parrainage {

    @Autowired
    private ParrainageRepository parrainageRepository;

    @Autowired
    private ParrainRepository parrainRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private AssociationRepository associationRepository;

    public dto_parrainage.Response createDemandeParrainage(dto_parrainage.CreateRequest request) {
        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        // Vérifier que l'association existe
        Association association = associationRepository.findById(request.associationId)
                .orElseThrow(() -> new RuntimeException("Association non trouvée avec l'ID: " + request.associationId));

        Parrainage parrainage = new Parrainage();
        parrainage.setEnfant(enfant);
        parrainage.setAssociation(association);
        parrainage.setStatut("EN_ATTENTE");
        parrainage.setDateDemande(LocalDate.now());
        parrainage.setMontantMensuel(request.montantMensuel);
        parrainage.setMessageDemande(request.messageDemande);
        parrainage.setNotes(request.notes);

        // Si un parrain est spécifié, l'associer
        if (request.parrainId != null) {
            Parrain parrain = parrainRepository.findById(request.parrainId)
                    .orElseThrow(() -> new RuntimeException("Parrain non trouvé avec l'ID: " + request.parrainId));
            parrainage.setParrain(parrain);
        }

        Parrainage savedParrainage = parrainageRepository.save(parrainage);
        return dto_parrainage.of(savedParrainage);
    }

    public List<dto_parrainage.Response> getAllParrainages() {
        return parrainageRepository.findAll()
                .stream()
                .map(dto_parrainage::of)
                .toList();
    }

    public Optional<dto_parrainage.Response> getParrainageById(Long id) {
        return parrainageRepository.findById(id)
                .map(dto_parrainage::of);
    }

    public List<dto_parrainage.Response> getParrainagesByParrain(Integer parrainId) {
        return parrainageRepository.findByParrainId(parrainId)
                .stream()
                .map(dto_parrainage::of)
                .toList();
    }

    public List<dto_parrainage.Response> getParrainagesByEnfant(Long enfantId) {
        return parrainageRepository.findByEnfantId(enfantId)
                .stream()
                .map(dto_parrainage::of)
                .toList();
    }

    public List<dto_parrainage.Response> getParrainagesByAssociation(Long associationId) {
        return parrainageRepository.findByAssociationId(associationId)
                .stream()
                .map(dto_parrainage::of)
                .toList();
    }

    public List<dto_parrainage.Response> getParrainagesEnAttente(Integer parrainId) {
        return parrainageRepository.findByParrainIdAndStatut(parrainId, "EN_ATTENTE")
                .stream()
                .map(dto_parrainage::of)
                .toList();
    }

    public dto_parrainage.Response accepterParrainage(Long id, String messageReponse) {
        Parrainage parrainage = parrainageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrainage non trouvé avec l'ID: " + id));

        parrainage.setStatut("ACCEPTE");
        parrainage.setMessageReponse(messageReponse);
        parrainage.setDateDebut(LocalDate.now());

        Parrainage updatedParrainage = parrainageRepository.save(parrainage);
        return dto_parrainage.of(updatedParrainage);
    }

    public dto_parrainage.Response refuserParrainage(Long id, String messageReponse) {
        Parrainage parrainage = parrainageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrainage non trouvé avec l'ID: " + id));

        parrainage.setStatut("REFUSE");
        parrainage.setMessageReponse(messageReponse);

        Parrainage updatedParrainage = parrainageRepository.save(parrainage);
        return dto_parrainage.of(updatedParrainage);
    }

    public dto_parrainage.Response updateParrainage(Long id, dto_parrainage.UpdateRequest request) {
        Parrainage parrainage = parrainageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrainage non trouvé avec l'ID: " + id));

        dto_parrainage.applyUpdate(parrainage, request);
        Parrainage updatedParrainage = parrainageRepository.save(parrainage);
        return dto_parrainage.of(updatedParrainage);
    }

    public void deleteParrainage(Long id) {
        if (!parrainageRepository.existsById(id)) {
            throw new RuntimeException("Parrainage non trouvé avec l'ID: " + id);
        }
        parrainageRepository.deleteById(id);
    }

    public dto_parrainage.Response createParrainageDirect(dto_parrainage.CreateRequest request) {
        // Vérifier que le parrain existe
        Parrain parrain = parrainRepository.findById(request.parrainId)
                .orElseThrow(() -> new RuntimeException("Parrain non trouvé avec l'ID: " + request.parrainId));

        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        // Vérifier que l'enfant n'est pas déjà parrainé
        List<Parrainage> existingParrainages = parrainageRepository.findByEnfantIdAndStatut(request.enfantId, "ACCEPTE");
        if (!existingParrainages.isEmpty()) {
            throw new RuntimeException("Cet enfant est déjà parrainé");
        }

        // Récupérer l'association de l'enfant
        Association association = enfant.getAssociation();
        if (association == null) {
            throw new RuntimeException("L'enfant n'est associé à aucune association");
        }

        Parrainage parrainage = new Parrainage();
        parrainage.setParrain(parrain);
        parrainage.setEnfant(enfant);
        parrainage.setAssociation(association);
        parrainage.setStatut("ACCEPTE"); // Parrainage direct accepté automatiquement
        parrainage.setDateDemande(LocalDate.now());
        parrainage.setDateDebut(LocalDate.now());
        parrainage.setMontantMensuel(request.montantMensuel);
        parrainage.setMessageDemande(request.messageDemande);
        parrainage.setMessageReponse("Parrainage direct accepté");
        parrainage.setNotes(request.notes);

        Parrainage savedParrainage = parrainageRepository.save(parrainage);
        return dto_parrainage.of(savedParrainage);
    }
}
