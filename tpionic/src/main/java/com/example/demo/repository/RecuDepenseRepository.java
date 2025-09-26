package com.example.demo.repository;

import com.example.demo.model.RecuDepense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecuDepenseRepository extends JpaRepository<RecuDepense, Long> {
    List<RecuDepense> findByParrainId(Integer parrainId);
    List<RecuDepense> findByAssociationId(Long associationId);
    List<RecuDepense> findByDepenseId(Long depenseId);
    List<RecuDepense> findByStatut(String statut);
}
