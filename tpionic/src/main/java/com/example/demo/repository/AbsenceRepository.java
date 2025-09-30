package com.example.demo.repository;

import com.example.demo.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    
    // Trouver les absences d'un parent
    List<Absence> findByParentId(Long parentId);
    
    // Trouver les absences d'un enfant
    List<Absence> findByEnfantId(Long enfantId);
    
    // Trouver les absences d'une association
    List<Absence> findByAssociationId(Long associationId);
    
    // Trouver les absences par statut
    List<Absence> findByStatut(String statut);
    
    // Trouver les absences en attente pour une association
    List<Absence> findByAssociationIdAndStatut(Long associationId, String statut);
    
    // Trouver les absences d'un parent par statut
    List<Absence> findByParentIdAndStatut(Long parentId, String statut);
    
    // Trouver les absences actives (date actuelle entre dateDebut et dateFin)
    @Query("SELECT a FROM Absence a WHERE a.dateDebut <= :dateActuelle AND a.dateFin >= :dateActuelle AND a.statut = 'ACCEPTE'")
    List<Absence> findAbsencesActives(@Param("dateActuelle") LocalDate dateActuelle);
    
    // Trouver les absences d'un enfant actives
    @Query("SELECT a FROM Absence a WHERE a.enfant.id = :enfantId AND a.dateDebut <= :dateActuelle AND a.dateFin >= :dateActuelle AND a.statut = 'ACCEPTE'")
    List<Absence> findAbsencesActivesByEnfant(@Param("enfantId") Long enfantId, @Param("dateActuelle") LocalDate dateActuelle);
    
    // Trouver les absences par type d'absence et association
    List<Absence> findByAssociationIdAndTypeAbsence(Long associationId, String typeAbsence);
    
    // Trouver les absences par type d'absence, association et statut
    List<Absence> findByAssociationIdAndTypeAbsenceAndStatut(Long associationId, String typeAbsence, String statut);
}
