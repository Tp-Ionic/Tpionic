package com.example.demo.controller;

import com.example.demo.DTO.dto_confirmation_paiement;
import com.example.demo.service.ServiceConfirmationPaiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/confirmations-Paiement")
@CrossOrigin(origins = "*")
public class controller_confirmation_paiement {

    @Autowired
    private ServiceConfirmationPaiement confirmationService;

    // Confirmer la réception d'un Paiement
    @PostMapping
    public ResponseEntity<?> confirmerPaiement(@RequestBody dto_confirmation_paiement.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.paiementId == 0) {
                return ResponseEntity.badRequest().body("L'ID du Paiement est obligatoire");
            }
            if (request.associationId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'association est obligatoire");
            }

            dto_confirmation_paiement.Response response = confirmationService.confirmerPaiement(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la confirmation: " + e.getMessage());
        }
    }

    // Lister toutes les confirmations
    @GetMapping
    public ResponseEntity<List<dto_confirmation_paiement.Response>> getAllConfirmations() {
        List<dto_confirmation_paiement.Response> confirmations = confirmationService.getAllConfirmations();
        return ResponseEntity.ok(confirmations);
    }

    // Obtenir une confirmation par ID
    @GetMapping("/{id}")
    public ResponseEntity<dto_confirmation_paiement.Response> getConfirmationById(@PathVariable int id) {
        Optional<dto_confirmation_paiement.Response> confirmation = confirmationService.getConfirmationById(id);
        return confirmation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtenir les confirmations d'un Paiement
    @GetMapping("/Paiement/{paiementId}")
    public ResponseEntity<List<dto_confirmation_paiement.Response>> getConfirmationsByPaiement(@PathVariable int paiementId) {
        List<dto_confirmation_paiement.Response> confirmations = confirmationService.getConfirmationsByPaiement(paiementId);
        return ResponseEntity.ok(confirmations);
    }

    // Obtenir les confirmations d'une association
    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_confirmation_paiement.Response>> getConfirmationsByAssociation(@PathVariable int associationId) {
        List<dto_confirmation_paiement.Response> confirmations = confirmationService.getConfirmationsByAssociation(associationId);
        return ResponseEntity.ok(confirmations);
    }

    // Obtenir les confirmations par statut
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<dto_confirmation_paiement.Response>> getConfirmationsByStatut(@PathVariable String statut) {
        List<dto_confirmation_paiement.Response> confirmations = confirmationService.getConfirmationsByStatut(statut);
        return ResponseEntity.ok(confirmations);
    }

    // Modifier une confirmation
    @PutMapping("/{id}")
    public ResponseEntity<?> updateConfirmation(@PathVariable int id, @RequestBody dto_confirmation_paiement.UpdateRequest request) {
        try {
            dto_confirmation_paiement.Response response = confirmationService.updateConfirmation(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la modification: " + e.getMessage());
        }
    }

    // Supprimer une confirmation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfirmation(@PathVariable int id) {
        try {
            confirmationService.deleteConfirmation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
