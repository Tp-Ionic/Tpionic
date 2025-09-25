package com.example.demo.controller;

import com.example.demo.DTO.dto_enfants;
import com.example.demo.model.*;
import com.example.demo.model.Association.AssociationStatus;
import com.example.demo.DTO.dto_association;
import com.example.demo.DTO.dto_parents;
import com.example.demo.service.service_association;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/association")
@RequiredArgsConstructor
public class controller_association {

    private final service_association associationService;

    // Créer une association (statut: EN_ATTENTE)
    @PostMapping
    public ResponseEntity<Association> creerAssociation(@RequestBody dto_association associationDTO) {
        Association association = associationService.creerAssociation(associationDTO);
        return ResponseEntity.ok(association);
    }

    // Valider une association (par l'admin)
    @PutMapping("/{associationId}/valider")
    public ResponseEntity<Association> validerAssociation(@PathVariable int associationId) {
        Association association = associationService.validerAssociation(associationId);
        return ResponseEntity.ok(association);
    }

    // Rejeter une association (par l'admin)
    @PutMapping("/{associationId}/rejeter")
    public ResponseEntity<Association> rejeterAssociation(@PathVariable int associationId) {
        Association association = associationService.rejeterAssociation(associationId);
        return ResponseEntity.ok(association);
    }

    // Lister les associations en attente
    @GetMapping("/en-attente")
    public ResponseEntity<List<Association>> listerAssociationsEnAttente() {
        List<Association> associations = associationService.listerAssociationsEnAttente();
        return ResponseEntity.ok(associations);
    }

    // Lister les associations validées
    @GetMapping("/validees")
    public ResponseEntity<List<Association>> listerAssociationsValidees() {
        List<Association> associations = associationService.listerAssociationsValidees();
        return ResponseEntity.ok(associations);
    }

    // Lister les associations rejetées
    @GetMapping("/rejetees")
    public ResponseEntity<List<Association>> listerAssociationsRejetees() {
        List<Association> associations = associationService.listerAssociationsRejetees();
        return ResponseEntity.ok(associations);
    }

    // Méthodes existantes (inchangées)
    @PostMapping("/parents")
    public ResponseEntity<Parent> creerParent(@RequestBody dto_parents parentDTO) {
        Parent parent = associationService.creerParent(parentDTO);
        return ResponseEntity.ok(parent);
    }

    @GetMapping("/enfants")
    public ResponseEntity<List<Enfant>> listerTousLesEnfants() {
        List<Enfant> enfants = associationService.listerTousLesEnfants();
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/parents")
    public ResponseEntity<List<Parent>> listerTousLesParents() {
        List<Parent> parents = associationService.listerTousLesParents();
        return ResponseEntity.ok(parents);
    }

    @GetMapping("/enfants/parraines")
    public ResponseEntity<List<Enfant>> listerEnfantsParraines() {
        List<Enfant> enfants = associationService.listerEnfantsParraines();
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/enfants/non-parraines")
    public ResponseEntity<List<Enfant>> listerEnfantsNonParraines() {
        List<Enfant> enfants = associationService.listerEnfantsNonParraines();
        return ResponseEntity.ok(enfants);
    }

    @GetMapping("/parrains")
    public ResponseEntity<List<Parrain>> listerParrains() {
        List<Parrain> parrains = associationService.listerTousLesParrains();
        return ResponseEntity.ok(parrains);
    }

    @PostMapping("/enfants/{enfantId}/demander-transfer")
    public ResponseEntity<String> demanderTransfertFonds(
            @PathVariable int enfantId, @RequestParam int montant, @RequestParam String motif) {
        boolean success = associationService.demanderTransfertFonds(enfantId, montant, motif);
        if (success) return ResponseEntity.ok("Demande de transfert envoyée");
        return ResponseEntity.badRequest().body("Enfant non trouvé");
    }

    @PostMapping("/enfants/{enfantId}/paiements")
    public ResponseEntity<Paiement> enregistrerPaiement(
            @PathVariable int enfantId, @RequestParam int montant, @RequestParam String typePaiement) {
        Paiement paiement = associationService.enregistrerPaiement(enfantId, montant, typePaiement);
        if (paiement != null) return ResponseEntity.ok(paiement);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/depenses")
    public ResponseEntity<Depense> enregistrerDepense(
            @RequestParam String libelle, @RequestParam int montant, @RequestParam String categorie) {
        Depense depense = associationService.enregistrerDepense(libelle, montant, categorie);
        return ResponseEntity.ok(depense);
    }

    @PostMapping("/enfants/{enfantId}/rapports-scolaires")
    public ResponseEntity<Rapport_scolaire> genererRapportScolaire(
            @PathVariable int enfantId,
            @RequestParam String anneeScolaire,
            @RequestParam String bulletinUrl,
            @RequestParam(required = false) String urlPhotoactivite,
            @RequestParam(required = false) String urlPresence) {

        Rapport_scolaire rapport = associationService.genererRapportScolaire(
                enfantId, anneeScolaire, bulletinUrl, urlPhotoactivite, urlPresence);
        if (rapport != null) return ResponseEntity.ok(rapport);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/rapports-financiers")
    public ResponseEntity<String> genererRapportFinancier(@RequestParam String periode) {
        String rapport = associationService.genererRapportFinancier(periode);
        return ResponseEntity.ok(rapport);
    }

    @PostMapping("/enfants/{enfantId}/demande-parrainage")
    public ResponseEntity<String> faireDemandeParrainage(
            @PathVariable int enfantId, @RequestParam String description) {
        boolean success = associationService.faireDemandeParrainage(enfantId, description);
        if (success) return ResponseEntity.ok("Demande de parrainage envoyée");
        return ResponseEntity.badRequest().body("Enfant non trouvé");
    }

    @PutMapping("/{associationId}/profil")
    public ResponseEntity<Association> updateProfil(
            @PathVariable int associationId, @RequestBody dto_association associationDTO) {
        Association association = associationService.updateProfil(associationId, associationDTO);
        if (association != null) return ResponseEntity.ok(association);
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/parents/{parentId}/profil")
    public ResponseEntity<Parent> updateProfilParent(
            @PathVariable int parentId, @RequestBody dto_parents parentDTO) {
        Parent parent = associationService.updateProfilParent(parentId, parentDTO);
        if (parent != null) return ResponseEntity.ok(parent);
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/enfants")
    public ResponseEntity<Enfant> creerEnfant(@RequestBody dto_enfants enfantDTO) {
        try {
            Enfant enfant = associationService.creerEnfant(enfantDTO);
            return ResponseEntity.ok(enfant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}