package com.example.demo.repository;

import com.example.demo.model.paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaiementRepository extends JpaRepository<paiement, Integer> {
    List<paiement> findByEnfantId(int enfantId);
}