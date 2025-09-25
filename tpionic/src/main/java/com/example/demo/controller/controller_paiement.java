package com.example.demo.controller;

import com.example.demo.DTO.dto_paiement;
import com.example.demo.service.service_paiement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class controller_paiement {

    private final service_paiement paiementService;

    @PostMapping
    public ResponseEntity<dto_paiement> creerPaiement(@RequestBody dto_paiement paiementDTO) {
        dto_paiement created = paiementService.creerPaiement(paiementDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<dto_paiement>> listerPaiements() {
        List<dto_paiement> paiements = paiementService.listerPaiements();
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_paiement>> listerPaiementsParEnfant(@PathVariable int enfantId) {
        List<dto_paiement> paiements = paiementService.listerPaiementsParEnfant(enfantId);
        return ResponseEntity.ok(paiements);
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<dto_paiement> confirmerPaiement(@PathVariable int id) {
        dto_paiement confirmed = paiementService.confirmerPaiement(id);
        return ResponseEntity.ok(confirmed);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_paiement> getPaiement(@PathVariable int id) {
        dto_paiement paiement = paiementService.getPaiementById(id);
        return ResponseEntity.ok(paiement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable int id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.ok().build();
    }
}