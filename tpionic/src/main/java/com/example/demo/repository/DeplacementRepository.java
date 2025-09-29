package com.example.demo.repository;

import com.example.demo.model.Deplacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeplacementRepository extends JpaRepository<Deplacement, Integer> {
    List<Deplacement> findByParentId(int parentId);
    List<Deplacement> findByEnfantId(int enfantId);
}
