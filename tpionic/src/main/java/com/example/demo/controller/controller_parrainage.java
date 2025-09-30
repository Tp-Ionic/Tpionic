package com.example.demo.controller;

import com.example.demo.DTO.dto_parrainage;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
import com.example.demo.service.service_parrainage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

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
            if (request.enfantId == null) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }
            if (request.associationId == null) {
                return ResponseEntity.badRequest().body("L'ID de l'association est obligatoire");
            }

            dto_parrainage.Response response = parrainageService.createDemandeParrainage(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    // Nouveau endpoint simplifié : parrainer un enfant (clic sur bouton + confirmation)
    @PostMapping("/parrainer-enfant")
    public ResponseEntity<?> parrainerEnfant(@RequestBody dto_parrainage.CreateRequest request) {
        try {
            // Validation des champs obligatoires (simplifiés)
            if (request.parrainId == null) {
                return ResponseEntity.badRequest().body("L'ID du parrain est obligatoire");
            }
            if (request.enfantId == null) {
                return ResponseEntity.badRequest().body("L'ID de l'enfant est obligatoire");
            }

            dto_parrainage.Response response = parrainageService.createDemandeParrainageSimple(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    // Endpoint pour obtenir les informations de confirmation (modal)
    @GetMapping("/confirmation/{parrainId}/{enfantId}")
    public ResponseEntity<?> getInformationsConfirmation(@PathVariable Integer parrainId, @PathVariable Long enfantId) {
        try {
            // Vérifier que le parrain existe
            Parrain parrain = parrainageService.getParrainById(parrainId);
            if (parrain == null) {
                return ResponseEntity.notFound().build();
            }

            // Vérifier que l'enfant existe
            Enfant enfant = parrainageService.getEnfantById(enfantId);
            if (enfant == null) {
                return ResponseEntity.notFound().build();
            }

            // Retourner les informations pour le modal
            Map<String, Object> confirmationInfo = new HashMap<>();
            confirmationInfo.put("parrainNom", parrain.getNom() + " " + parrain.getPrenom());
            confirmationInfo.put("enfantNom", enfant.getNom() + " " + enfant.getPrenom());
            confirmationInfo.put("enfantAge", enfant.getAge());
            confirmationInfo.put("associationNom", enfant.getAssociation().getNom());
            confirmationInfo.put("montantMensuel", 50.0); // Montant par défaut
            confirmationInfo.put("message", "Êtes-vous sûr de vouloir parrainer " + enfant.getNom() + " " + enfant.getPrenom() + " ?");

            return ResponseEntity.ok(confirmationInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des informations: " + e.getMessage());
        }
    }

    @PostMapping("/direct")
    public ResponseEntity<?> createParrainageDirect(@RequestBody dto_parrainage.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.parrainId == null) {
                return ResponseEntity.badRequest().body("L'ID du parrain est obligatoire");
            }
            if (request.enfantId == null) {
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
    public ResponseEntity<?> getParrainageById(@PathVariable Long id) {
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
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesByEnfant(@PathVariable Long enfantId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesByEnfant(enfantId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesByAssociation(@PathVariable Long associationId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesByAssociation(associationId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/parrain/{parrainId}/en-attente")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesEnAttente(@PathVariable Integer parrainId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesEnAttente(parrainId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/parrain/{parrainId}/en-attente-paiement")
    public ResponseEntity<List<dto_parrainage.Response>> getParrainagesEnAttentePaiement(@PathVariable Integer parrainId) {
        List<dto_parrainage.Response> parrainages = parrainageService.getParrainagesEnAttentePaiement(parrainId);
        return ResponseEntity.ok(parrainages);
    }

    @PostMapping("/{id}/accepter")
    public ResponseEntity<?> accepterParrainage(@PathVariable Long id, @RequestParam(required = false) String messageReponse) {
        try {
            dto_parrainage.Response response = parrainageService.accepterParrainage(id, messageReponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'acceptation: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/refuser")
    public ResponseEntity<?> refuserParrainage(@PathVariable Long id, @RequestParam(required = false) String messageReponse) {
        try {
            dto_parrainage.Response response = parrainageService.refuserParrainage(id, messageReponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors du refus: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParrainage(@PathVariable Long id, @RequestBody dto_parrainage.UpdateRequest request) {
        try {
            dto_parrainage.Response response = parrainageService.updateParrainage(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParrainage(@PathVariable Long id) {
        try {
            parrainageService.deleteParrainage(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
