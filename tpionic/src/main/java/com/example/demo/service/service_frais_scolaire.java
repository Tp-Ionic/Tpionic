package com.example.demo.service;

import com.example.demo.DTO.dto_frais_scolaire;
import com.example.demo.model.Frais_scolaire;
import com.example.demo.model.Enfant;
import com.example.demo.repository.FraisScolaireRepository;
import com.example.demo.repository.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class service_frais_scolaire {

    @Autowired
    private FraisScolaireRepository fraisScolaireRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    public dto_frais_scolaire.Response createFraisScolaire(dto_frais_scolaire.CreateRequest request) {
        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        Frais_scolaire frais = new Frais_scolaire();
        frais.setClasse(request.classe);
        frais.setAnnee_scolaire(request.annee_scolaire);
        frais.setMontant(request.montant);
        frais.setDescription(request.description);
        frais.setStatut(request.statut != null ? request.statut : "EN_ATTENTE");
        frais.setDateEcheance(request.dateEcheance);
        frais.setEnfant(enfant);

        Frais_scolaire savedFrais = fraisScolaireRepository.save(frais);
        return dto_frais_scolaire.of(savedFrais);
    }

    public List<dto_frais_scolaire.Response> getAllFraisScolaires() {
        return fraisScolaireRepository.findAll()
                .stream()
                .map(dto_frais_scolaire::of)
                .toList();
    }

    public Optional<dto_frais_scolaire.Response> getFraisScolaireById(Long id) {
        return fraisScolaireRepository.findById(id)
                .map(dto_frais_scolaire::of);
    }

    public List<dto_frais_scolaire.Response> getFraisScolairesByEnfant(Long enfantId) {
        return fraisScolaireRepository.findByEnfantId(enfantId)
                .stream()
                .map(dto_frais_scolaire::of)
                .toList();
    }

    public dto_frais_scolaire.Response updateFraisScolaire(Long id, dto_frais_scolaire.UpdateRequest request) {
        Frais_scolaire frais = fraisScolaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frais scolaire non trouvé avec l'ID: " + id));

        dto_frais_scolaire.applyUpdate(frais, request);
        Frais_scolaire updatedFrais = fraisScolaireRepository.save(frais);
        return dto_frais_scolaire.of(updatedFrais);
    }

    public void deleteFraisScolaire(Long id) {
        if (!fraisScolaireRepository.existsById(id)) {
            throw new RuntimeException("Frais scolaire non trouvé avec l'ID: " + id);
        }
        fraisScolaireRepository.deleteById(id);
    }
}
