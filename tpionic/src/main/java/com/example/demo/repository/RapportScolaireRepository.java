package com.example.demo.repository;

import com.example.demo.model.Rapport_scolaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RapportScolaireRepository extends JpaRepository<Rapport_scolaire, Integer> {
    List<Rapport_scolaire> findByEnfantId(int enfantId);
}