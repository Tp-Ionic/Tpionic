package com.example.demo.controller;

import com.example.demo.DTO.dto_absence;
import com.example.demo.service.service_absence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*")
public class controller_absence {

    @Autowired
    private service_absence absenceService;

    // CRUD pour les absences
    @PostMapping
    public ResponseEntity<?> createAbsence(@RequestBody dto_absence.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.parentId == null) {
                return ResponseEntity.badRequest().body("L'ID du parent est obligatoire");
            }
            if (request.enfantId == null) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }
            if (request.dateDebut == null) {
                return ResponseEntity.badRequest().body("La date de début est obligatoire");
            }
            if (request.dateFin == null) {
                return ResponseEntity.badRequest().body("La date de fin est obligatoire");
            }
            if (request.raison == null || request.raison.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La raison est obligatoire");
            }

            dto_absence.Response response = absenceService.createAbsence(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<dto_absence.Response>> getAllAbsences() {
        List<dto_absence.Response> absences = absenceService.getAllAbsences();
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_absence.Response> getAbsenceById(@PathVariable Long id) {
        Optional<dto_absence.Response> absence = absenceService.getAbsenceById(id);
        return absence.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesByParent(@PathVariable Long parentId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesByParent(parentId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesByEnfant(@PathVariable Long enfantId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesByEnfant(enfantId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesByAssociation(@PathVariable Long associationId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesByAssociation(associationId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/association/{associationId}/en-attente")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesEnAttenteByAssociation(@PathVariable Long associationId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesEnAttenteByAssociation(associationId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesActives() {
        List<dto_absence.Response> absences = absenceService.getAbsencesActives();
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/enfant/{enfantId}/actives")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesActivesByEnfant(@PathVariable Long enfantId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesActivesByEnfant(enfantId);
        return ResponseEntity.ok(absences);
    }

    // Obtenir les absences de type déménagement par association
    @GetMapping("/association/{associationId}/demenagement")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesDemenagementByAssociation(@PathVariable Long associationId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesDemenagementByAssociation(associationId);
        return ResponseEntity.ok(absences);
    }

    // Obtenir les absences de type déménagement en attente par association
    @GetMapping("/association/{associationId}/demenagement/en-attente")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesDemenagementEnAttenteByAssociation(@PathVariable Long associationId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesDemenagementEnAttenteByAssociation(associationId);
        return ResponseEntity.ok(absences);
    }

    // Obtenir les absences de type déménagement acceptées par association
    @GetMapping("/association/{associationId}/demenagement/acceptees")
    public ResponseEntity<List<dto_absence.Response>> getAbsencesDemenagementAccepteesByAssociation(@PathVariable Long associationId) {
        List<dto_absence.Response> absences = absenceService.getAbsencesDemenagementAccepteesByAssociation(associationId);
        return ResponseEntity.ok(absences);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAbsence(@PathVariable Long id, @RequestBody dto_absence.UpdateRequest request) {
        try {
            dto_absence.Response response = absenceService.updateAbsence(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<?> validerAbsence(@PathVariable Long id, @RequestBody dto_absence.ValidationRequest request) {
        try {
            dto_absence.Response response = absenceService.validerAbsence(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        try {
            absenceService.deleteAbsence(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
