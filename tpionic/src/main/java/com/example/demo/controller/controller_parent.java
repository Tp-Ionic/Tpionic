package com.example.demo.controller;

import com.example.demo.model.Enfant;
import com.example.demo.model.Rapport_scolaire;
import com.example.demo.model.Frais_scolaire;
import com.example.demo.model.Parent;
import com.example.demo.service.service_parent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class controller_parent {

    private final service_parent parentsService;

    // Voir mon profil (lecture seule)
    @GetMapping("/{parentId}/profil")
    public ResponseEntity<Parent> getMonProfil(@PathVariable int parentId) {
        Optional<Parent> parent = parentsService.getParentById(parentId);
        return parent.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Voir la liste de mes enfants
    @GetMapping("/{parentId}/enfants")
    public ResponseEntity<List<Enfant>> getMesEnfants(@PathVariable int parentId) {
        List<Enfant> enfants = parentsService.getMesEnfants(parentId);
        return ResponseEntity.ok(enfants);
    }

    // Voir les informations d'un de mes enfants
    @GetMapping("/{parentId}/enfants/{enfantId}")
    public ResponseEntity<Enfant> getMonEnfant(
            @PathVariable int parentId,
            @PathVariable int enfantId) {
        Enfant enfant = parentsService.getMonEnfant(enfantId, parentId);
        if (enfant != null) {
            return ResponseEntity.ok(enfant);
        }
        return ResponseEntity.notFound().build();
    }

    // Voir les rapports scolaires de mon enfant
    @GetMapping("/{parentId}/enfants/{enfantId}/rapports-scolaires")
    public ResponseEntity<List<Rapport_scolaire>> getRapportsScolairesEnfant(
            @PathVariable int parentId,
            @PathVariable int enfantId) {
        List<Rapport_scolaire> rapports = parentsService.getRapportsScolairesEnfant(enfantId, parentId);
        return ResponseEntity.ok(rapports);
    }

    // Voir l'état des paiements de mon enfant
    @GetMapping("/{parentId}/enfants/{enfantId}/frais-scolaires")
    public ResponseEntity<List<Frais_scolaire>> getFraisScolairesEnfant(
            @PathVariable int parentId,
            @PathVariable int enfantId) {
        List<Frais_scolaire> frais = parentsService.getFraisScolairesEnfant(enfantId, parentId);
        return ResponseEntity.ok(frais);
    }

    // Confirmer un paiement aux parrains
    @PutMapping("/{parentId}/enfants/{enfantId}/frais/{fraisId}/confirmer-paiement")
    public ResponseEntity<String> confirmerPaiementParrain(
            @PathVariable int parentId,
            @PathVariable int enfantId,
            @PathVariable int fraisId) {
        boolean confirme = parentsService.confirmerPaiementParrain(fraisId, enfantId, parentId);
        if (confirme) {
            return ResponseEntity.ok("Paiement confirmé aux parrains avec succès");
        }
        return ResponseEntity.badRequest().body("Frais non trouvé ou non autorisé");
    }

    // Informer d'un changement de situation
    @PostMapping("/{parentId}/enfants/{enfantId}/changement-situation")
    public ResponseEntity<String> informerChangementSituation(
            @PathVariable int parentId,
            @PathVariable int enfantId,
            @RequestBody String nouvelleSituation) {
        boolean informe = parentsService.informerChangementSituation(enfantId, parentId, nouvelleSituation);
        if (informe) {
            return ResponseEntity.ok("Changement de situation signalé à l'école");
        }
        return ResponseEntity.badRequest().body("Enfant non trouvé ou non autorisé");
    }
}