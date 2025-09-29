package com.example.demo.repository;

import com.example.demo.model.Frais_scolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FraisScolaireRepository extends JpaRepository<Frais_scolaire, Integer> {
    List<Frais_scolaire> findByEnfantId(int enfantId);
    List<Frais_scolaire> findByStatut(String statut);
}
