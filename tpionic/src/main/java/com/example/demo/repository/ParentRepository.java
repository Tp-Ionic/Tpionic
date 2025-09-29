package com.example.demo.repository;

import com.example.demo.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    
    // Trouver un parent par email
    Optional<Parent> findByEmail(String email);
    
    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);
    
    // Trouver les parents actifs
    List<Parent> findByActifTrue();
    
    // Trouver les parents par nom
    List<Parent> findByNomContainingIgnoreCase(String nom);
    
    // Trouver les parents par prénom
    List<Parent> findByPrenomContainingIgnoreCase(String prenom);
    
    // Trouver les parents par pays
    List<Parent> findByPays(String pays);
    
    // Trouver les parents par ville
    List<Parent> findByVille(String ville);
    
    // Trouver les parents par profession
    List<Parent> findByProfession(String profession);
    
    // Trouver les parents par relation avec l'enfant
    List<Parent> findByRelationAvecEnfant(String relationAvecEnfant);
}