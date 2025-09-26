package com.example.demo.repository;

import com.example.demo.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    List<Enfant> findByAssociationId(Long associationId);
    List<Enfant> findByNomContainingIgnoreCase(String nom);
    List<Enfant> findByPrenomContainingIgnoreCase(String prenom);
    List<Enfant> findByParentId(Long parentId);
    
    @Query("SELECT e FROM Enfant e WHERE e.association.id = :associationId AND e.id NOT IN " +
           "(SELECT p.enfant.id FROM Parrainage p WHERE p.statut = 'ACCEPTE')")
    List<Enfant> findEnfantsNonParrainesByAssociation(@Param("associationId") Long associationId);
}
