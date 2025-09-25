package com.example.demo.repository;

import com.example.demo.model.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepenseRepository extends JpaRepository<Depense, Integer> {
    List<Depense> findByCategorie(String categorie);
}