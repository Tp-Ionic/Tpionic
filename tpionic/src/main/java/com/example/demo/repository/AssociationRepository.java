package com.example.demo.repository;

import com.example.demo.model.Association;
import com.example.demo.model.Association.AssociationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssociationRepository extends JpaRepository<Association, Integer> {
    List<Association> findByStatus(AssociationStatus status);
    List<Association> findByActif(boolean actif);
}