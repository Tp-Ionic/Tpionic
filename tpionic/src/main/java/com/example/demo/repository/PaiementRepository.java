package com.example.demo.repository;

import com.example.demo.model.paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<paiement, Long> {
    List<paiement> findByParrainId(Integer parrainId);
    List<paiement> findByEnfantId(Long enfantId);
}
