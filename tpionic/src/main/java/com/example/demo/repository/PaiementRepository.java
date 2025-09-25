package com.example.demo.repository;

import com.example.demo.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
    List<Paiement> findByEnfantId(int enfantId);
    List<Paiement> findByConfirme(boolean confirme);
}