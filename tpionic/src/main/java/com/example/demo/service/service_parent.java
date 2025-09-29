package com.example.demo.service;

import com.example.demo.DTO.dto_parent;
import com.example.demo.model.Parent;
import com.example.demo.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class service_parent {

    @Autowired
    private ParentRepository parentRepository;

    // Créer un compte parent
    public dto_parent.Response createParent(dto_parent.CreateRequest request) {
        // Vérifier si l'email existe déjà
        if (parentRepository.existsByEmail(request.email)) {
            throw new RuntimeException("Un parent avec cet email existe déjà");
        }

        Parent parent = new Parent();
        parent.setNom(request.nom);
        parent.setPrenom(request.prenom);
        parent.setEmail(request.email);
        parent.setMotDePasse(request.motDePasse);
        parent.setTelephone(request.telephone);
        parent.setAdresse(request.adresse);
        parent.setPays(request.pays);
        parent.setVille(request.ville);
        parent.setProfession(request.profession);
        parent.setRelationAvecEnfant(request.relationAvecEnfant);
        parent.setActif(true);

        Parent savedParent = parentRepository.save(parent);
        return dto_parent.of(savedParent);
    }

    // Obtenir tous les parents
    public List<dto_parent.Response> getAllParents() {
        return parentRepository.findAll()
                .stream()
                .map(dto_parent::of)
                .toList();
    }

    // Obtenir un parent par ID
    public Optional<dto_parent.Response> getParentById(int id) {
        return parentRepository.findById(id)
                .map(dto_parent::of);
    }

    // Obtenir un parent par email
    public Optional<dto_parent.Response> getParentByEmail(String email) {
        return parentRepository.findByEmail(email)
                .map(dto_parent::of);
    }

    // Authentification supprimée - sera gérée par Spring Security

    // Modifier un parent
    public dto_parent.Response updateParent(int id, dto_parent.UpdateProfilRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé avec l'ID: " + id));

        dto_parent.applyUpdate(parent, request);
        Parent updatedParent = parentRepository.save(parent);
        return dto_parent.of(updatedParent);
    }

    // Supprimer un parent
    public void deleteParent(int id) {
        if (!parentRepository.existsById(id)) {
            throw new RuntimeException("Parent non trouvé avec l'ID: " + id);
        }
        parentRepository.deleteById(id);
    }

    // Rechercher des parents par nom
    public List<dto_parent.Response> searchParentsByName(String nom) {
        return parentRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(dto_parent::of)
                .toList();
    }

    // Rechercher des parents par prénom
    public List<dto_parent.Response> searchParentsByPrenom(String prenom) {
        return parentRepository.findByPrenomContainingIgnoreCase(prenom)
                .stream()
                .map(dto_parent::of)
                .toList();
    }

    // Rechercher des parents par pays
    public List<dto_parent.Response> searchParentsByPays(String pays) {
        return parentRepository.findByPays(pays)
                .stream()
                .map(dto_parent::of)
                .toList();
    }

    // Rechercher des parents par ville
    public List<dto_parent.Response> searchParentsByVille(String ville) {
        return parentRepository.findByVille(ville)
                .stream()
                .map(dto_parent::of)
                .toList();
    }

    // Rechercher des parents par profession
    public List<dto_parent.Response> searchParentsByProfession(String profession) {
        return parentRepository.findByProfession(profession)
                .stream()
                .map(dto_parent::of)
                .toList();
    }

    // Rechercher des parents par relation avec l'enfant
    public List<dto_parent.Response> searchParentsByRelation(String relationAvecEnfant) {
        return parentRepository.findByRelationAvecEnfant(relationAvecEnfant)
                .stream()
                .map(dto_parent::of)
                .toList();
    }
}