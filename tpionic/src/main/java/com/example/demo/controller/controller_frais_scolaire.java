package com.example.demo.controller;

import com.example.demo.DTO.dto_frais_scolaire;
import com.example.demo.service.ServiceFraisScolaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/frais-scolaires")
@CrossOrigin(origins = "*")
public class controller_frais_scolaire {

    @Autowired
    private ServiceFraisScolaire fraisScolaireService;

    @PostMapping
    public ResponseEntity<?> createFraisScolaire(@RequestBody dto_frais_scolaire.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.montant == null || request.montant <= 0) {
                return ResponseEntity.badRequest().body("Le montant est obligatoire et doit être positif");
            }
            if (request.enfantId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }

            dto_frais_scolaire.Response response = fraisScolaireService.createFraisScolaire(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<dto_frais_scolaire.Response>> getAllFraisScolaires() {
        List<dto_frais_scolaire.Response> frais = fraisScolaireService.getAllFraisScolaires();
        return ResponseEntity.ok(frais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFraisScolaireById(@PathVariable int id) {
        Optional<dto_frais_scolaire.Response> frais = fraisScolaireService.getFraisScolaireById(id);
        return frais.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_frais_scolaire.Response>> getFraisScolairesByEnfant(@PathVariable int enfantId) {
        List<dto_frais_scolaire.Response> frais = fraisScolaireService.getFraisScolairesByEnfant(enfantId);
        return ResponseEntity.ok(frais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFraisScolaire(@PathVariable int id, @RequestBody dto_frais_scolaire.UpdateRequest request) {
        try {
            dto_frais_scolaire.Response response = fraisScolaireService.updateFraisScolaire(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFraisScolaire(@PathVariable int id) {
        try {
            fraisScolaireService.deleteFraisScolaire(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
