package com.example.demo.repository;

import com.example.demo.model.paiement;
import com.example.demo.Enumeration.type_paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<paiement, Long> {
    // Méthodes de base
    List<paiement> findByParrainId(Integer parrainId);
    List<paiement> findByEnfantId(Long enfantId);
    List<paiement> findByTypePaiement(type_paiement typePaiement);
    List<paiement> findByDatePaiement(LocalDate datePaiement);
    List<paiement> findByDatePaiementBetween(LocalDate dateDebut, LocalDate dateFin);
    
    // Méthodes par association (via relation enfant.association)
    List<paiement> findByEnfantAssociationId(Long associationId);
    List<paiement> findByEnfantAssociationIdAndParrainId(Long associationId, Integer parrainId);
    List<paiement> findByEnfantAssociationIdAndEnfantId(Long associationId, Long enfantId);
    List<paiement> findByEnfantAssociationIdAndTypePaiement(Long associationId, type_paiement typePaiement);
    List<paiement> findByEnfantAssociationIdAndDatePaiementBetween(Long associationId, LocalDate dateDebut, LocalDate dateFin);
}
