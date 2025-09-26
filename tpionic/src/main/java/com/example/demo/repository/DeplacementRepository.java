package com.example.demo.repository;

import com.example.demo.model.Deplacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeplacementRepository extends JpaRepository<Deplacement, Long> {
    List<Deplacement> findByParentId(Long parentId);
    List<Deplacement> findByEnfantId(Long enfantId);
}
