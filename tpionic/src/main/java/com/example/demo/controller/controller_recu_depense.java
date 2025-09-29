package com.example.demo.controller;

import com.example.demo.DTO.dto_recu_depense;
import com.example.demo.service.service_recu_depense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recus-depense")
@CrossOrigin(origins = "*")
public class controller_recu_depense {

    @Autowired
    private service_recu_depense recuService;

    // Créer un reçu de dépense
    @PostMapping
    public ResponseEntity<?> creerRecuDepense(@RequestBody dto_recu_depense.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.depenseId == 0) {
                return ResponseEntity.badRequest().body("L'ID de la dépense est obligatoire");
            }
            if (request.parrainId == 0) {
                return ResponseEntity.badRequest().body("L'ID du parrain est obligatoire");
            }
            if (request.associationId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'association est obligatoire");
            }
            if (request.description == null || request.description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La description est obligatoire");
            }
            if (request.montant == null || request.montant <= 0) {
                return ResponseEntity.badRequest().body("Le montant doit être positif");
            }

            dto_recu_depense.Response response = recuService.creerRecuDepense(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création du reçu: " + e.getMessage());
        }
    }

    // Lister tous les reçus
    @GetMapping
    public ResponseEntity<List<dto_recu_depense.Response>> getAllRecus() {
        List<dto_recu_depense.Response> recus = recuService.getAllRecus();
        return ResponseEntity.ok(recus);
    }

    // Obtenir un reçu par ID
    @GetMapping("/{id}")
    public ResponseEntity<dto_recu_depense.Response> getRecuById(@PathVariable int id) {
        Optional<dto_recu_depense.Response> recu = recuService.getRecuById(id);
        return recu.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtenir les reçus d'un parrain
    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<List<dto_recu_depense.Response>> getRecusByParrain(@PathVariable Integer parrainId) {
        List<dto_recu_depense.Response> recus = recuService.getRecusByParrain(parrainId);
        return ResponseEntity.ok(recus);
    }

    // Obtenir les reçus d'une association
    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_recu_depense.Response>> getRecusByAssociation(@PathVariable int associationId) {
        List<dto_recu_depense.Response> recus = recuService.getRecusByAssociation(associationId);
        return ResponseEntity.ok(recus);
    }

    // Obtenir les reçus d'une dépense
    @GetMapping("/depense/{depenseId}")
    public ResponseEntity<List<dto_recu_depense.Response>> getRecusByDepense(@PathVariable int depenseId) {
        List<dto_recu_depense.Response> recus = recuService.getRecusByDepense(depenseId);
        return ResponseEntity.ok(recus);
    }

    // Obtenir les reçus par statut
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<dto_recu_depense.Response>> getRecusByStatut(@PathVariable String statut) {
        List<dto_recu_depense.Response> recus = recuService.getRecusByStatut(statut);
        return ResponseEntity.ok(recus);
    }

    // Modifier un reçu
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecu(@PathVariable int id, @RequestBody dto_recu_depense.UpdateRequest request) {
        try {
            dto_recu_depense.Response response = recuService.updateRecu(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la modification: " + e.getMessage());
        }
    }

    // Supprimer un reçu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecu(@PathVariable int id) {
        try {
            recuService.deleteRecu(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
