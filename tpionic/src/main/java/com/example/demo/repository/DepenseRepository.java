package com.example.demo.repository;

import com.example.demo.model.depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<depense, Integer> {
    List<depense> findByTypeDepense(com.example.demo.Enumeration.type_depense typeDepense);
    List<depense> findByPaiementId(int paiementId);
    List<depense> findByEnfantId(int enfantId);
    List<depense> findByAssociationId(int associationId);
}
