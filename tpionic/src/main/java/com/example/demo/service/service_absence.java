package com.example.demo.service;

import com.example.demo.DTO.dto_absence;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class service_absence {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Transactional
    public dto_absence.Response createAbsence(dto_absence.CreateRequest request) {
        // Vérifier que le parent existe
        Parent parent = parentRepository.findById(request.parentId)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé avec l'ID: " + request.parentId));

        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(request.enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + request.enfantId));

        // Vérifier que l'association existe
        Association association = associationRepository.findById(enfant.getAssociation().getId())
                .orElseThrow(() -> new RuntimeException("Association non trouvée"));

        // Vérifier que le parent est bien le parent de cet enfant
        if (enfant.getParent().getId() != request.parentId.intValue()) {
            throw new RuntimeException("Ce parent n'est pas le parent de cet enfant");
        }

        // Vérifier que les dates sont valides
        if (request.dateDebut.isAfter(request.dateFin)) {
            throw new RuntimeException("La date de début ne peut pas être après la date de fin");
        }

        if (request.dateDebut.isBefore(LocalDate.now())) {
            throw new RuntimeException("La date de début ne peut pas être dans le passé");
        }

        // Créer l'absence
        Absence absence = new Absence();
        absence.setParent(parent);
        absence.setEnfant(enfant);
        absence.setAssociation(association);
        absence.setDateDebut(request.dateDebut);
        absence.setDateFin(request.dateFin);
        absence.setRaison(request.raison);
        absence.setDetails(request.details);
        absence.setTypeAbsence(request.typeAbsence != null ? request.typeAbsence : "TEMPORAIRE");
        absence.setStatut("EN_ATTENTE");
        absence.setDateDeclaration(LocalDateTime.now());

        Absence savedAbsence = absenceRepository.save(absence);
        return dto_absence.of(savedAbsence);
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAllAbsences() {
        return absenceRepository.findAll()
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<dto_absence.Response> getAbsenceById(Long id) {
        return absenceRepository.findById(id)
                .map(dto_absence::of);
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesByParent(Long parentId) {
        return absenceRepository.findByParentId(parentId)
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesByEnfant(Long enfantId) {
        return absenceRepository.findByEnfantId(enfantId)
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesByAssociation(Long associationId) {
        return absenceRepository.findByAssociationId(associationId)
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesEnAttenteByAssociation(Long associationId) {
        return absenceRepository.findByAssociationIdAndStatut(associationId, "EN_ATTENTE")
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    @Transactional
    public dto_absence.Response updateAbsence(Long id, dto_absence.UpdateRequest request) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée avec l'ID: " + id));

        // Vérifier que l'absence n'est pas déjà traitée
        if (!"EN_ATTENTE".equals(absence.getStatut())) {
            throw new RuntimeException("Impossible de modifier une absence déjà traitée");
        }

        dto_absence.applyUpdate(absence, request);
        Absence updatedAbsence = absenceRepository.save(absence);
        return dto_absence.of(updatedAbsence);
    }

    @Transactional
    public dto_absence.Response validerAbsence(Long id, dto_absence.ValidationRequest request) {
        if (!"ACCEPTE".equals(request.statut) && !"REFUSE".equals(request.statut)) {
            throw new RuntimeException("Statut invalide. Utilisez 'ACCEPTE' ou 'REFUSE'");
        }

        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée avec l'ID: " + id));

        absence.setStatut(request.statut);
        absence.setReponseAssociation(request.reponseAssociation);
        absence.setDateReponse(LocalDateTime.now());

        Absence updatedAbsence = absenceRepository.save(absence);
        return dto_absence.of(updatedAbsence);
    }

    @Transactional
    public void deleteAbsence(Long id) {
        if (!absenceRepository.existsById(id)) {
            throw new RuntimeException("Absence non trouvée avec l'ID: " + id);
        }
        absenceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesActives() {
        return absenceRepository.findAbsencesActives(LocalDate.now())
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesActivesByEnfant(Long enfantId) {
        return absenceRepository.findAbsencesActivesByEnfant(enfantId, LocalDate.now())
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    // Obtenir les absences de type déménagement par association
    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesDemenagementByAssociation(Long associationId) {
        return absenceRepository.findByAssociationIdAndTypeAbsence(associationId, "DEMENAGEMENT_DEFINITIF")
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    // Obtenir les absences de type déménagement en attente par association
    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesDemenagementEnAttenteByAssociation(Long associationId) {
        return absenceRepository.findByAssociationIdAndTypeAbsenceAndStatut(associationId, "DEMENAGEMENT_DEFINITIF", "EN_ATTENTE")
                .stream()
                .map(dto_absence::of)
                .toList();
    }

    // Obtenir les absences de type déménagement acceptées par association
    @Transactional(readOnly = true)
    public List<dto_absence.Response> getAbsencesDemenagementAccepteesByAssociation(Long associationId) {
        return absenceRepository.findByAssociationIdAndTypeAbsenceAndStatut(associationId, "DEMENAGEMENT_DEFINITIF", "ACCEPTE")
                .stream()
                .map(dto_absence::of)
                .toList();
    }
}
