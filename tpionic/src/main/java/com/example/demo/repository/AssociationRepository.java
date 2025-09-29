package com.example.demo.repository;

import com.example.demo.model.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Integer> {
    Optional<Association> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Association> findByStatut(String statut);
}
