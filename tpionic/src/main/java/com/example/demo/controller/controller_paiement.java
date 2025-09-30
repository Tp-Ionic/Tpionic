package com.example.demo.controller;

import com.example.demo.DTO.dto_paiement;
import com.example.demo.model.paiement;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
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
                return ResponseEntity.badRequest().body("Le type de paiement est obligatoire");
            }
            if (request.datePaiement == null) {
                return ResponseEntity.badRequest().body("La date de paiement est obligatoire");
            }
            if (request.parrainId == null) {
                return ResponseEntity.badRequest().body("L'ID du parrain est obligatoire");
            }
            if (request.enfantId == null) {
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
    public ResponseEntity<?> getPaiementById(@PathVariable Long id) {
        Optional<dto_paiement.Response> paiement = paiementService.getPaiementById(id);
        return paiement.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaiement(@PathVariable Long id, @RequestBody dto_paiement.UpdateRequest request) {
        try {
            dto_paiement.Response response = paiementService.updatePaiement(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        try {
            paiementService.deletePaiement(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints de filtrage
    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByParrain(@PathVariable Integer parrainId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByParrain(parrainId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByEnfant(@PathVariable Long enfantId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByEnfant(enfantId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/type/{typePaiement}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByType(@PathVariable String typePaiement) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByType(typePaiement);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByDate(@PathVariable String date) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByDate(date);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/periode/{dateDebut}/{dateFin}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByPeriode(
            @PathVariable String dateDebut, 
            @PathVariable String dateFin) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByPeriode(dateDebut, dateFin);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/parrain/{parrainId}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByAssociationAndParrain(
            @PathVariable Long associationId, 
            @PathVariable Integer parrainId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByAssociationAndParrain(associationId, parrainId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/enfant/{enfantId}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByAssociationAndEnfant(
            @PathVariable Long associationId, 
            @PathVariable Long enfantId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByAssociationAndEnfant(associationId, enfantId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/type/{typePaiement}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByAssociationAndType(
            @PathVariable Long associationId, 
            @PathVariable String typePaiement) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByAssociationAndType(associationId, typePaiement);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/periode/{dateDebut}/{dateFin}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByAssociationAndPeriode(
            @PathVariable Long associationId, 
            @PathVariable String dateDebut, 
            @PathVariable String dateFin) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByAssociationAndPeriode(associationId, dateDebut, dateFin);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/statut/{statut}")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsByAssociationAndStatut(
            @PathVariable Long associationId, 
            @PathVariable String statut) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsByAssociationAndStatut(associationId, statut);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/valide")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsValidesByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsValidesByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/non-valide")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsNonValidesByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsNonValidesByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/en-attente")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsEnAttenteByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsEnAttenteByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/rejete")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsRejetesByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsRejetesByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/confirme")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsConfirmesByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsConfirmesByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/association/{associationId}/recu")
    public ResponseEntity<List<dto_paiement.Response>> getPaiementsRecusByAssociation(@PathVariable Long associationId) {
        List<dto_paiement.Response> paiements = paiementService.getPaiementsRecusByAssociation(associationId);
        return ResponseEntity.ok(paiements);
    }
}
