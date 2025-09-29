package com.example.demo.repository;

import com.example.demo.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
    List<Paiement> findByParrainId(Integer parrainId);
    List<Paiement> findByEnfantId(int enfantId);
}
