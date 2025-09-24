package com.example.demo.repository;

import com.example.demo.model.Frais_scolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FraisScolaireRepository extends JpaRepository<Frais_scolaire, Integer> {
    List<Frais_scolaire> findByEnfantId(int enfantId);
}