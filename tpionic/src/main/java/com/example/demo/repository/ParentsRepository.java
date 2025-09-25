package com.example.demo.repository;

import com.example.demo.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentsRepository extends JpaRepository<Parent, Integer> {
}