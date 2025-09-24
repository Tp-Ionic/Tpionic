package com.example.demo.controller;

import com.example.demo.model.Enfant;
import com.example.demo.model.Rapport_scolaire;
import com.example.demo.model.Frais_scolaire;
import com.example.demo.DTO.dto_enfant;
import com.example.demo.service.service_enfant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enfants")
@RequiredArgsConstructor
public class controller_enfant {

    private final service_enfant enfantService;

    // Voir les rapports scolaires
    @GetMapping("/{enfantId}/rapports-scolaires")
    public ResponseEntity<List<Rapport_scolaire>> getRapportsScolaires(@PathVariable int enfantId) {
        List<Rapport_scolaire> rapports = enfantService.getRapportsScolaires(enfantId);
        return ResponseEntity.ok(rapports);
    }

    // Voir l'état des paiements
    @GetMapping("/{enfantId}/frais-scolaires")
    public ResponseEntity<List<Frais_scolaire>> getFraisScolaires(@PathVariable int enfantId) {
        List<Frais_scolaire> frais = enfantService.getFraisScolaires(enfantId);
        return ResponseEntity.ok(frais);
    }

    // Confirmer un paiement
    @PutMapping("/frais/{fraisId}/confirmer-paiement")
    public ResponseEntity<String> confirmerPaiement(@PathVariable int fraisId) {
        boolean confirme = enfantService.confirmerPaiement(fraisId);
        if (confirme) {
            return ResponseEntity.ok("Paiement confirmé avec succès");
        }
        return ResponseEntity.badRequest().body("Frais non trouvé");
    }

    // Mettre à jour le profil
    @PutMapping("/{enfantId}/profil")
    public ResponseEntity<Enfant> updateProfil(@PathVariable int enfantId, @RequestBody dto_enfant enfantDTO) {
        Enfant enfant = enfantService.updateProfil(enfantId, enfantDTO);
        if (enfant != null) {
            return ResponseEntity.ok(enfant);
        }
        return ResponseEntity.notFound().build();
    }

    // Informer d'un changement de situation
    @PostMapping("/{enfantId}/changement-situation")
    public ResponseEntity<String> informerChangementSituation(
            @PathVariable int enfantId,
            @RequestBody String nouvelleSituation) {
        enfantService.informerChangementSituation(enfantId, nouvelleSituation);
        return ResponseEntity.ok("Changement de situation signalé à l'école");
    }

    // Obtenir les informations de l'enfant
    @GetMapping("/{enfantId}")
    public ResponseEntity<Enfant> getEnfant(@PathVariable int enfantId) {
        Optional<Enfant> enfant = enfantService.getEnfantById(enfantId);
        return enfant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}