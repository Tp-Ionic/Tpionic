package com.example.demo.repository;

import com.example.demo.model.ParrainageDepense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParrainageDepenseRepository extends JpaRepository<ParrainageDepense, Integer> {
    
    // Trouver toutes les dépenses prises en charge par un parrainage
    List<ParrainageDepense> findByParrainageId(int parrainageId);
    
    // Trouver toutes les dépenses prises en charge par un parrain
    @Query("SELECT pd FROM ParrainageDepense pd WHERE pd.parrainage.parrain.id = :parrainId")
    List<ParrainageDepense> findByParrainId(@Param("parrainId") Integer parrainId);
    
    // Trouver toutes les dépenses prises en charge pour un enfant
    @Query("SELECT pd FROM ParrainageDepense pd WHERE pd.parrainage.enfant.id = :enfantId")
    List<ParrainageDepense> findByEnfantId(@Param("enfantId") int enfantId);
    
    // Trouver toutes les dépenses prises en charge pour une dépense spécifique
    List<ParrainageDepense> findByDepenseId(int depenseId);
    
    // Trouver les dépenses prises en charge par statut
    List<ParrainageDepense> findByStatut(String statut);
}
