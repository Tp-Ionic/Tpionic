package com.example.demo.controller;

import com.example.demo.DTO.dto_enfant;
import com.example.demo.service.ServiceEnfant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enfants")
@CrossOrigin(origins = "*")
public class controller_enfant {

    @Autowired
    private ServiceEnfant enfantService;

    @GetMapping
    public ResponseEntity<List<dto_enfant.Response>> getAllEnfants() {
        List<dto_enfant.Response> enfants = enfantService.getAllEnfants();
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnfantById(@PathVariable int id) {
        Optional<dto_enfant.Response> enfant = enfantService.getEnfantById(id);
        return enfant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnfant(@PathVariable int id, @RequestBody dto_enfant.UpdateRequest request) {
        try {
            dto_enfant.Response response = enfantService.updateEnfant(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnfant(@PathVariable int id) {
        try {
            enfantService.deleteEnfant(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_enfant.Response>> getEnfantsByAssociation(@PathVariable int associationId) {
        List<dto_enfant.Response> enfants = enfantService.getEnfantsByAssociation(associationId);
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/association/{associationId}/non-parraines")
    public ResponseEntity<List<dto_enfant.Response>> getEnfantsNonParraines(@PathVariable Long associationId) {
        List<dto_enfant.Response> enfants = enfantService.getEnfantsNonParraines(associationId);
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/search/nom/{nom}")
    public ResponseEntity<List<dto_enfant.Response>> searchEnfantsByName(@PathVariable String nom) {
        List<dto_enfant.Response> enfants = enfantService.searchEnfantsByName(nom);
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/search/prenom/{prenom}")
    public ResponseEntity<List<dto_enfant.Response>> searchEnfantsByPrenom(@PathVariable String prenom) {
        List<dto_enfant.Response> enfants = enfantService.searchEnfantsByPrenom(prenom);
        return ResponseEntity.ok(enfants);
    }
}
