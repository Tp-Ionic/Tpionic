package com.example.demo.controller;

import com.example.demo.DTO.dto_paiement;
import com.example.demo.service.service_paiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "*")
public class controller_paiement {

    @Autowired
    private service_paiement paiementService;

    @PostMapping
    public ResponseEntity<?> createPaiement(@RequestBody dto_paiement.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.montant == null || request.montant <= 0) {
                return ResponseEntity.badRequest().body("Le montant est obligatoire et doit être positif");
            }
            if (request.typePaiement == null) {
                return ResponseEntity.badRequest().body("Le type de Paiement est obligatoire");
            }
            if (request.datePaiement == null) {
                return ResponseEntity.badRequest().body("La date de Paiement est obligatoire");
            }
            if (request.parrainId == 0) {
                return ResponseEntity.badRequest().body("L'ID du parrain est obligatoire");
            }
            if (request.enfantId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }

            dto_paiement.Response response = paiementService.createPaiement(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<dto_paiement.Response>> getAllPaiements() {
        List<dto_paiement.Response> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaiementById(@PathVariable int id) {
        Optional<dto_paiement.Response> paiement = paiementService.getPaiementById(id);
        return paiement.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaiement(@PathVariable int id, @RequestBody dto_paiement.UpdateRequest request) {
        try {
            dto_paiement.Response response = paiementService.updatePaiement(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable int id) {
        try {
            paiementService.deletePaiement(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
