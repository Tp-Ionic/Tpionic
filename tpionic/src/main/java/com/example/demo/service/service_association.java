package com.example.demo.service;

import com.example.demo.DTO.dto_association;
import com.example.demo.model.Association;
import com.example.demo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class service_association {

    @Autowired
    private AssociationRepository associationRepository;

    public dto_association.Response createAssociation(dto_association.CreateRequest request) {
        // Vérifier si l'email existe déjà
        if (associationRepository.existsByEmail(request.email)) {
            throw new RuntimeException("Une association avec cet email existe déjà");
        }

        Association association = new Association();
        association.setNom(request.nom);
        association.setEmail(request.email);
        association.setMotDePasse(request.motDePasse);
        association.setTelephone(request.telephone);
        association.setAdresse(request.adresse);
        association.setPays(request.pays);
        association.setVille(request.ville);
        association.setDescription(request.description);
        association.setLogoUrl(request.logoUrl);
        association.setActif(true);

        Association savedAssociation = associationRepository.save(association);
        return dto_association.of(savedAssociation);
    }

    public List<dto_association.Response> getAllAssociations() {
        return associationRepository.findAll()
                .stream()
                .map(dto_association::of)
                .toList();
    }

    public Optional<dto_association.Response> getAssociationById(Long id) {
        return associationRepository.findById(id)
                .map(dto_association::of);
    }

    public Optional<dto_association.Response> getAssociationByEmail(String email) {
        return associationRepository.findByEmail(email)
                .map(dto_association::of);
    }

    public dto_association.Response updateAssociation(Long id, dto_association.UpdateRequest request) {
        Association association = associationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Association non trouvée avec l'ID: " + id));

        dto_association.applyUpdate(association, request);
        Association updatedAssociation = associationRepository.save(association);
        return dto_association.of(updatedAssociation);
    }

    public void deleteAssociation(Long id) {
        if (!associationRepository.existsById(id)) {
            throw new RuntimeException("Association non trouvée avec l'ID: " + id);
        }
        associationRepository.deleteById(id);
    }

    // Authentification supprimée - sera gérée par Spring Security
}
