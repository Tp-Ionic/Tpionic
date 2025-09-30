package com.example.demo.repository;

import com.example.demo.model.Parrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParrainRepository extends JpaRepository<Parrain, Integer> {
    Optional<Parrain> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Parrain> findByActif(Boolean actif);
}
