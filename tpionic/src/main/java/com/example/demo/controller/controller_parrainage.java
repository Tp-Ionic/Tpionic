package com.example.demo.controller;

import com.example.demo.DTO.dto_parrainage;
import com.example.demo.service.service_parrainage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parrainages")
@CrossOrigin(origins = "*")
public class controller_parrainage {

    @Autowired
    private service_parrainage parrainageService;

    @PostMapping
    public ResponseEntity<?> createDemandeParrainage(@RequestBody dto_parrainage.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.enfantId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }
            if (request.associationId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'association est obligatoire");
            }

            dto_parrainage.Response response = parrainageService.createDemandeParrainage(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @PostMapping("/direct")
    public ResponseEntity<?> createParrainageDirect(@RequestBody dto_parrainage.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.parrainId == 0) {
                return ResponseEntity.badRequest().body("L'ID du parrain est obligatoire");
            }
            if (request.enfantId == 0) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }
            if (request.montantMensuel == null || request.montantMensuel <= 0) {
                return ResponseEntity.badRequest().body("Le montant mensuel est obligatoire et doit être positif");
            }

            dto_parrainage.Response response = parrainageService.createParrainageDirect(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<dto_parrainage.Response>> getAllParrainages() {
        List<dto_parrainage.Response> parrainages = parrainageService.getAllParrainages();
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParrainageById(@PathVariable int id) {
        Optional<dto_parrainage.Response> parrainage = parrainageService.getParrainageById(id);
        return parrainage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesByParrain(@PathVariable Integer parrainId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesByParrain(parrainId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesByEnfant(@PathVariable int enfantId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesByEnfant(enfantId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesByAssociation(@PathVariable int associationId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesByAssociation(associationId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/parrain/{parrainId}/en-attente")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesEnAttente(@PathVariable Integer parrainId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesEnAttente(parrainId);
        return ResponseEntity.ok(parrainages);
    }

    @PostMapping("/{id}/accepter")
    public ResponseEntity<?> accepterParrainage(@PathVariable int id, @RequestParam(required = false) String messageReponse) {
        try {
            dto_parrainage.Response response = parrainageService.accepterParrainage(id, messageReponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'acceptation: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/refuser")
    public ResponseEntity<?> refuserParrainage(@PathVariable int id, @RequestParam(required = false) String messageReponse) {
        try {
            dto_parrainage.Response response = parrainageService.refuserParrainage(id, messageReponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors du refus: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParrainage(@PathVariable int id, @RequestBody dto_parrainage.UpdateRequest request) {
        try {
            dto_parrainage.Response response = parrainageService.updateParrainage(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParrainage(@PathVariable int id) {
        try {
            parrainageService.deleteParrainage(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
