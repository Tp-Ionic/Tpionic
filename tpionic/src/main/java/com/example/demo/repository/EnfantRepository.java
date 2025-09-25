package com.example.demo.repository;

import com.example.demo.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnfantRepository extends JpaRepository<Enfant, Integer> {
    List<Enfant> findByParentId(int parentId);
    List<Enfant> findByAssociationId(int associationId);
}