package com.example.demo.repository;

import com.example.demo.model.depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<depense, Long> {
    List<depense> findByTypeDepense(com.example.demo.Enumeration.type_depense typeDepense);
    List<depense> findByPaiementId(Long paiementId);
    List<depense> findByEnfantId(Long enfantId);
    List<depense> findByAssociationId(Long associationId);
}
