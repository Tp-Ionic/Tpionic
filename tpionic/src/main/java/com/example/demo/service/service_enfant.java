package com.example.demo.service;

import com.example.demo.DTO.dto_enfant;
import com.example.demo.model.Association;
import com.example.demo.model.Enfant;
import com.example.demo.model.Parent;
import com.example.demo.repository.AssociationRepository;
import com.example.demo.repository.EnfantRepository;
import com.example.demo.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class service_enfant {

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private ParentRepository parentRepository;

    public dto_enfant.Response createEnfant(dto_enfant.CreateRequest request) {
        // Vérifier que l'association existe
        Association association = associationRepository.findById(request.associationId)
                .orElseThrow(() -> new RuntimeException("Association non trouvée avec l'ID: " + request.associationId));

        // Créer le compte parent
        Parent parent = new Parent();
        parent.setNom(request.parentNom);
        parent.setPrenom(request.parentPrenom);
        parent.setEmail(request.parentEmail);
        parent.setMotDePasse(request.parentMotDePasse);
        parent.setTelephone(request.parentTelephone);
        parent.setAdresse(request.parentAdresse);
        parent.setPays(request.parentPays);
        parent.setVille(request.parentVille);
        parent.setProfession(request.parentProfession);
        parent.setRelationAvecEnfant(request.parentRelationAvecEnfant);
        parent.setActif(true);

        Parent savedParent = parentRepository.save(parent);

        // Créer l'enfant
        Enfant enfant = new Enfant();
        enfant.setNom(request.nom);
        enfant.setPrenom(request.prenom);
        enfant.setDateNaissance(request.dateNaissance);
        enfant.setAdresse(request.adresse);
        enfant.setAge(request.age);
        enfant.setApropos_de_enfant(request.apropos_de_enfant);
        enfant.setAssociation(association);
        enfant.setParent(savedParent);

        Enfant savedEnfant = enfantRepository.save(enfant);
        return dto_enfant.of(savedEnfant);
    }

    public List<dto_enfant.Response> getAllEnfants() {
        return enfantRepository.findAll()
                .stream()
                .map(dto_enfant::of)
                .toList();
    }

    public List<dto_enfant.Response> getEnfantsByAssociation(Long associationId) {
        return enfantRepository.findByAssociationId(associationId)
                .stream()
                .map(dto_enfant::of)
                .toList();
    }

    public Optional<dto_enfant.Response> getEnfantById(Long id) {
        return enfantRepository.findById(id)
                .map(dto_enfant::of);
    }

    public dto_enfant.Response updateEnfant(Long id, dto_enfant.UpdateRequest request) {
        Enfant enfant = enfantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + id));

        dto_enfant.applyUpdate(enfant, request);
        Enfant updatedEnfant = enfantRepository.save(enfant);
        return dto_enfant.of(updatedEnfant);
    }

    public void deleteEnfant(Long id) {
        if (!enfantRepository.existsById(id)) {
            throw new RuntimeException("Enfant non trouvé avec l'ID: " + id);
        }
        enfantRepository.deleteById(id);
    }

    public List<dto_enfant.Response> searchEnfantsByName(String nom) {
        return enfantRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(dto_enfant::of)
                .toList();
    }

    public List<dto_enfant.Response> searchEnfantsByPrenom(String prenom) {
        return enfantRepository.findByPrenomContainingIgnoreCase(prenom)
                .stream()
                .map(dto_enfant::of)
                .toList();
    }

    public List<dto_enfant.Response> getEnfantsNonParraines(Long associationId) {
        return enfantRepository.findEnfantsNonParrainesByAssociation(associationId)
                .stream()
                .map(dto_enfant::of)
                .toList();
    }
}
