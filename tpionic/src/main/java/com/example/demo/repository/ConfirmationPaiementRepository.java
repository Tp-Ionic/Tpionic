package com.example.demo.repository;

import com.example.demo.model.ConfirmationPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationPaiementRepository extends JpaRepository<ConfirmationPaiement, Integer> {
    List<ConfirmationPaiement> findByPaiementId(int paiementId);
    List<ConfirmationPaiement> findByAssociationId(int associationId);
    List<ConfirmationPaiement> findByStatut(String statut);
}
