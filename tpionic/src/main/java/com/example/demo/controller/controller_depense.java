package com.example.demo.controller;

import com.example.demo.DTO.dto_depense;
import com.example.demo.service.ServiceDepense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/depenses")
@CrossOrigin(origins = "*")
public class controller_depense {

    @Autowired
    private ServiceDepense depenseService;

    @PostMapping
    public ResponseEntity<?> createDepense(@RequestBody dto_depense.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.description == null || request.description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La description est obligatoire");
            }
            if (request.montant == null || request.montant <= 0) {
                return ResponseEntity.badRequest().body("Le montant est obligatoire et doit être positif");
            }
            if (request.typeDepense == null) {
                return ResponseEntity.badRequest().body("Le type de dépense est obligatoire");
            }
            if (request.dateDepense == null) {
                return ResponseEntity.badRequest().body("La date de dépense est obligatoire");
            }
            if (request.enfantId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }
            if (request.associationId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'association est obligatoire");
            }

            dto_depense.Response response = depenseService.createDepense(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<dto_depense.Response>> getAllDepenses() {
        List<dto_depense.Response> depenses = depenseService.getAllDepenses();
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepenseById(@PathVariable int id) {
        Optional<dto_depense.Response> depense = depenseService.getDepenseById(id);
        return depense.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepense(@PathVariable int id, @RequestBody dto_depense.UpdateRequest request) {
        try {
            dto_depense.Response response = depenseService.updateDepense(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable int id) {
        try {
            depenseService.deleteDepense(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Nouveaux endpoints pour la traçabilité
    @GetMapping("/Paiement/{paiementId}")
    public ResponseEntity<List<dto_depense.Response>> getDepensesByPaiement(@PathVariable int paiementId) {
        List<dto_depense.Response> depenses = depenseService.getDepensesByPaiement(paiementId);
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_depense.Response>> getDepensesByEnfant(@PathVariable int enfantId) {
        List<dto_depense.Response> depenses = depenseService.getDepensesByEnfant(enfantId);
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_depense.Response>> getDepensesByAssociation(@PathVariable int associationId) {
        List<dto_depense.Response> depenses = depenseService.getDepensesByAssociation(associationId);
        return ResponseEntity.ok(depenses);
    }
}
