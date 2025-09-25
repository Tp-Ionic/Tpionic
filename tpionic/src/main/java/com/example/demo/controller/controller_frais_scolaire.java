package com.example.demo.controller;

import com.example.demo.DTO.dto_frais_scolaire;
import com.example.demo.service.service_frais_scolaire;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frais-scolaires")
@RequiredArgsConstructor
public class controller_frais_scolaire {

    private final service_frais_scolaire fraisScolaireService;

    @PostMapping
    public ResponseEntity<dto_frais_scolaire> creerFraisScolaire(@RequestBody dto_frais_scolaire fraisDTO) {
        dto_frais_scolaire created = fraisScolaireService.creerFraisScolaire(fraisDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<dto_frais_scolaire>> listerFraisScolaires() {
        List<dto_frais_scolaire> frais = fraisScolaireService.listerFraisScolaires();
        return ResponseEntity.ok(frais);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_frais_scolaire>> listerFraisScolairesParEnfant(@PathVariable int enfantId) {
        List<dto_frais_scolaire> frais = fraisScolaireService.listerFraisScolairesParEnfant(enfantId);
        return ResponseEntity.ok(frais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_frais_scolaire> getFraisScolaire(@PathVariable int id) {
        dto_frais_scolaire frais = fraisScolaireService.getFraisScolaireById(id);
        return ResponseEntity.ok(frais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto_frais_scolaire> updateFraisScolaire(@PathVariable int id, @RequestBody dto_frais_scolaire fraisDTO) {
        dto_frais_scolaire updated = fraisScolaireService.updateFraisScolaire(id, fraisDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFraisScolaire(@PathVariable int id) {
        fraisScolaireService.deleteFraisScolaire(id);
        return ResponseEntity.ok().build();
    }
}