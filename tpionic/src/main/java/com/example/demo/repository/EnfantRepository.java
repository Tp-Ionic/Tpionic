package com.example.demo.repository;

import com.example.demo.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnfantRepository extends JpaRepository<Enfant, Integer> {
}