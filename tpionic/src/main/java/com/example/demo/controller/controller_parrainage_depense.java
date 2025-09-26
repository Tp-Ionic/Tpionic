package com.example.demo.controller;

import com.example.demo.DTO.dto_parrainage_depense;
import com.example.demo.service.service_parrainage_depense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrainage-depenses")
public class controller_parrainage_depense {

    @Autowired
    private service_parrainage_depense parrainageDepenseService;

    // Créer une prise en charge de dépense par un parrainage
    @PostMapping
    public ResponseEntity<dto_parrainage_depense.Response> creerPriseEnCharge(@RequestBody dto_parrainage_depense.CreateRequest request) {
        try {
            dto_parrainage_depense.Response response = parrainageDepenseService.creerPriseEnCharge(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtenir toutes les prises en charge d'un parrainage
    @GetMapping("/parrainage/{parrainageId}")
    public ResponseEntity<List<dto_parrainage_depense.Response>> getPrisesEnChargeByParrainage(@PathVariable Long parrainageId) {
        List<dto_parrainage_depense.Response> responses = parrainageDepenseService.getPrisesEnChargeByParrainage(parrainageId);
        return ResponseEntity.ok(responses);
    }

    // Obtenir toutes les prises en charge d'un parrain
    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<List<dto_parrainage_depense.Response>> getPrisesEnChargeByParrain(@PathVariable Integer parrainId) {
        List<dto_parrainage_depense.Response> responses = parrainageDepenseService.getPrisesEnChargeByParrain(parrainId);
        return ResponseEntity.ok(responses);
    }

    // Obtenir toutes les prises en charge pour un enfant
    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_parrainage_depense.Response>> getPrisesEnChargeByEnfant(@PathVariable Long enfantId) {
        List<dto_parrainage_depense.Response> responses = parrainageDepenseService.getPrisesEnChargeByEnfant(enfantId);
        return ResponseEntity.ok(responses);
    }

    // Obtenir toutes les prises en charge pour une dépense
    @GetMapping("/depense/{depenseId}")
    public ResponseEntity<List<dto_parrainage_depense.Response>> getPrisesEnChargeByDepense(@PathVariable Long depenseId) {
        List<dto_parrainage_depense.Response> responses = parrainageDepenseService.getPrisesEnChargeByDepense(depenseId);
        return ResponseEntity.ok(responses);
    }

    // Obtenir le résumé des prises en charge pour une dépense
    @GetMapping("/depense/{depenseId}/resume")
    public ResponseEntity<dto_parrainage_depense.ResumeDepenseResponse> getResumeDepense(@PathVariable Long depenseId) {
        try {
            dto_parrainage_depense.ResumeDepenseResponse response = parrainageDepenseService.getResumeDepense(depenseId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Modifier une prise en charge
    @PutMapping("/{id}")
    public ResponseEntity<dto_parrainage_depense.Response> modifierPriseEnCharge(@PathVariable Long id, @RequestBody dto_parrainage_depense.UpdateRequest request) {
        try {
            dto_parrainage_depense.Response response = parrainageDepenseService.modifierPriseEnCharge(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Confirmer une prise en charge
    @PutMapping("/{id}/confirmer")
    public ResponseEntity<dto_parrainage_depense.Response> confirmerPriseEnCharge(@PathVariable Long id) {
        try {
            dto_parrainage_depense.Response response = parrainageDepenseService.confirmerPriseEnCharge(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Refuser une prise en charge
    @PutMapping("/{id}/refuser")
    public ResponseEntity<dto_parrainage_depense.Response> refuserPriseEnCharge(@PathVariable Long id) {
        try {
            dto_parrainage_depense.Response response = parrainageDepenseService.refuserPriseEnCharge(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer une prise en charge
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPriseEnCharge(@PathVariable Long id) {
        try {
            parrainageDepenseService.supprimerPriseEnCharge(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
