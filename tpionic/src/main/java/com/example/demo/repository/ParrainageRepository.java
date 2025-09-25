package com.example.demo.repository;

import com.example.demo.model.Parrainage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParrainageRepository extends JpaRepository<Parrainage, Integer> {
    List<Parrainage> findByEnfantId(int enfantId);
    List<Parrainage> findByParrainId(int parrainId);
    List<Parrainage> findByActif(boolean actif);
}