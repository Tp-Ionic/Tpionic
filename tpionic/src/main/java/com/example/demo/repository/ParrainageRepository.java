package com.example.demo.repository;

import com.example.demo.model.Parrainage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParrainageRepository extends JpaRepository<Parrainage, Long> {
    List<Parrainage> findByParrainId(Integer parrainId);
    List<Parrainage> findByEnfantId(Long enfantId);
    List<Parrainage> findByAssociationId(Long associationId);
    List<Parrainage> findByStatut(String statut);
    List<Parrainage> findByParrainIdAndStatut(Integer parrainId, String statut);
    List<Parrainage> findByEnfantIdAndStatut(Long enfantId, String statut);
    Optional<Parrainage> findByParrainIdAndEnfantIdAndStatut(Integer parrainId, Long enfantId, String statut);
}
