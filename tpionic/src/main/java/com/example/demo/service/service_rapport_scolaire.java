package com.example.demo.service;

import com.example.demo.DTO.dto_rapport_scolaire;
import com.example.demo.model.Rapport_scolaire;
import com.example.demo.model.Enfant;
import com.example.demo.repository.RapportScolaireRepository;
import com.example.demo.repository.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class service_rapport_scolaire {

    @Autowired
    private RapportScolaireRepository rapportRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    // Créer un rapport scolaire
    public dto_rapport_scolaire.Response createRapport(dto_rapport_scolaire.CreateRequest request) {
        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        Rapport_scolaire rapport = new Rapport_scolaire();
        rapport.setTrimestre(request.trimestre);
        rapport.setAnnee_scolaire(request.annee_scolaire);
        rapport.setMoyenne(request.moyenne);
        rapport.setAppreciation(request.appreciation);
        rapport.setDateRapport(request.dateRapport);
        rapport.setEnfant(enfant);

        Rapport_scolaire savedRapport = rapportRepository.save(rapport);
        return dto_rapport_scolaire.of(savedRapport);
    }

    // Lister tous les rapports
    public List<dto_rapport_scolaire.Response> getAllRapports() {
        return rapportRepository.findAll()
                .stream()
                .map(dto_rapport_scolaire::of)
                .toList();
    }

    // Obtenir un rapport par ID
    public Optional<dto_rapport_scolaire.Response> getRapportById(int id) {
        return rapportRepository.findById(id)
                .map(dto_rapport_scolaire::of);
    }

    // Obtenir les rapports d'un enfant
    public List<dto_rapport_scolaire.Response> getRapportsByEnfant(int enfantId, String anneeScolaire) {
        if (anneeScolaire != null && !anneeScolaire.trim().isEmpty()) {
            return rapportRepository.findByEnfantIdAndAnneeScolaire(enfantId, anneeScolaire)
                    .stream()
                    .map(dto_rapport_scolaire::of)
                    .toList();
        } else {
            return rapportRepository.findByEnfantId(enfantId)
                    .stream()
                    .map(dto_rapport_scolaire::of)
                    .toList();
        }
    }

    // Modifier un rapport
    public dto_rapport_scolaire.Response updateRapport(int id, dto_rapport_scolaire.UpdateRequest request) {
        Rapport_scolaire rapport = rapportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapport scolaire non trouvé avec l'ID: " + id));

        dto_rapport_scolaire.applyUpdate(rapport, request);
        Rapport_scolaire updatedRapport = rapportRepository.save(rapport);
        return dto_rapport_scolaire.of(updatedRapport);
    }

    // Supprimer un rapport
    public void deleteRapport(int id) {
        if (!rapportRepository.existsById(id)) {
            throw new RuntimeException("Rapport scolaire non trouvé avec l'ID: " + id);
        }
        rapportRepository.deleteById(id);
    }
}