package com.example.demo.repository;

import com.example.demo.model.Parrainage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParrainageRepository extends JpaRepository<Parrainage, Integer> {
    List<Parrainage> findByParrainId(int parrainId);
    List<Parrainage> findByEnfantId(int enfantId);
    List<Parrainage> findByAssociationId(int associationId);
    List<Parrainage> findByStatut(String statut);
    List<Parrainage> findByParrainIdAndStatut(Integer parrainId, String statut);
    List<Parrainage> findByEnfantIdAndStatut(int enfantId, String statut);
}
