package com.example.demo.repository;

import com.example.demo.model.Rapport_scolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportScolaireRepository extends JpaRepository<Rapport_scolaire, Integer> {
    List<Rapport_scolaire> findByEnfantId(int enfantId);
    
    @Query("SELECT r FROM Rapport_scolaire r WHERE r.enfant.id = :enfantId AND r.annee_scolaire = :anneeScolaire")
    List<Rapport_scolaire> findByEnfantIdAndAnneeScolaire(@Param("enfantId") int enfantId, @Param("anneeScolaire") String anneeScolaire);
    
    @Query("SELECT r FROM Rapport_scolaire r WHERE r.annee_scolaire = :anneeScolaire")
    List<Rapport_scolaire> findByAnneeScolaire(@Param("anneeScolaire") String anneeScolaire);
}
