package com.example.demo.controller;

import com.example.demo.DTO.dto_parrain;
import com.example.demo.DTO.dto_parrain.CreateRequest;
import com.example.demo.DTO.dto_parrain.UpdateRequest;
import com.example.demo.DTO.dto_enfant;
import com.example.demo.DTO.dto_rapport_scolaire;
import com.example.demo.DTO.dto_confirmation_paiement;
import com.example.demo.DTO.dto_recu_depense;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrains")
@CrossOrigin(origins = "*")
public class controller_parrain {

    private final service_parrain service;

    @Autowired
    private ServiceEnfant enfantService;

    @Autowired
    private service_rapport_scolaire rapportService;

    @Autowired
    private ServiceConfirmationPaiement confirmationService;

    @Autowired
    private service_recu_depense recuService;

    public controller_parrain(service_parrain service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> creer(@RequestBody CreateRequest req){
        try {
            // Validation des champs obligatoires
            if (req.nom == null || req.nom.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le nom du parrain est obligatoire");
            }
            if (req.prenom == null || req.prenom.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le prénom du parrain est obligatoire");
            }
            if (req.email == null || req.email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("L'email du parrain est obligatoire");
            }
            if (req.motDePasse == null || req.motDePasse.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le mot de passe est obligatoire");
            }

            dto_parrain.Response response = service.creer(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }

    }

    // Authentification supprimée - sera gérée par Spring Security

    @GetMapping
    public List<dto_parrain.Response> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public dto_parrain.Response get(@PathVariable int id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public dto_parrain.Response update(@PathVariable int id, @RequestBody UpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    // ===== NOUVELLES FONCTIONNALITÉS POUR LE PARRAIN =====

    // Voir les enfants parrainés par ce parrain
    @GetMapping("/{parrainId}/enfants-parraines")
    public ResponseEntity<?> getEnfantsParraines(@PathVariable Integer parrainId) {
        try {
            List<dto_enfant.Response> enfants = service.getEnfantsParraines(parrainId);
            return ResponseEntity.ok(enfants);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir les rapports scolaires d'un enfant parrainé
    @GetMapping("/{parrainId}/enfants/{enfantId}/rapports-scolaires")
    public ResponseEntity<?> getRapportsScolaires(@PathVariable Integer parrainId, 
                                                 @PathVariable int enfantId,
                                                 @RequestParam(required = false) String anneeScolaire) {
        try {
            List<dto_rapport_scolaire.Response> rapports = service.getRapportsScolairesEnfant(parrainId, enfantId, anneeScolaire);
            return ResponseEntity.ok(rapports);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir les bulletins PDF d'un enfant parrainé
    @GetMapping("/{parrainId}/enfants/{enfantId}/bulletins")
    public ResponseEntity<?> getBulletinsEnfant(@PathVariable Integer parrainId, 
                                               @PathVariable Long enfantId) {
        try {
            dto_enfant.Response enfant = service.getBulletinsEnfant(parrainId, enfantId);
            return ResponseEntity.ok(enfant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir les photos d'activités d'un enfant parrainé
    @GetMapping("/{parrainId}/enfants/{enfantId}/photos-activites")
    public ResponseEntity<?> getPhotosActivites(@PathVariable Integer parrainId, 
                                              @PathVariable Long enfantId) {
        try {
            dto_enfant.Response enfant = service.getPhotosActivitesEnfant(parrainId, enfantId);
            return ResponseEntity.ok(enfant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir les listes de présence d'un enfant parrainé
    @GetMapping("/{parrainId}/enfants/{enfantId}/listes-presence")
    public ResponseEntity<?> getListesPresence(@PathVariable Integer parrainId, 
                                             @PathVariable Long enfantId) {
        try {
            dto_enfant.Response enfant = service.getListesPresenceEnfant(parrainId, enfantId);
            return ResponseEntity.ok(enfant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir les confirmations de Paiement du parrain
    @GetMapping("/{parrainId}/confirmations-Paiement")
    public ResponseEntity<?> getConfirmationsPaiement(@PathVariable Integer parrainId) {
        try {
            List<dto_confirmation_paiement.Response> confirmations = service.getConfirmationsPaiement(parrainId);
            return ResponseEntity.ok(confirmations);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir les reçus de dépense du parrain
    @GetMapping("/{parrainId}/recus-depense")
    public ResponseEntity<?> getRecusDepense(@PathVariable Integer parrainId) {
        try {
            List<dto_recu_depense.Response> recus = recuService.getRecusByParrain(parrainId);
            return ResponseEntity.ok(recus);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Voir le résumé complet d'un enfant parrainé
    @GetMapping("/{parrainId}/enfants/{enfantId}/resume-complet")
    public ResponseEntity<?> getResumeCompletEnfant(@PathVariable Integer parrainId, 
                                                   @PathVariable int enfantId) {
        try {
            dto_parrain.ResumeEnfantResponse resume = service.getResumeCompletEnfant(parrainId, enfantId);
            return ResponseEntity.ok(resume);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
}