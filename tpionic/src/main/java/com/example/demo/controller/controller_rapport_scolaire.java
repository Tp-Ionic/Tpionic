package com.example.demo.controller;

import com.example.demo.DTO.dto_rapport_scolaire;
import com.example.demo.service.service_rapport_scolaire;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports-scolaires")
@RequiredArgsConstructor
public class controller_rapport_scolaire {

    private final service_rapport_scolaire rapportScolaireService;

    @PostMapping
    public ResponseEntity<dto_rapport_scolaire> creerRapport(@RequestBody dto_rapport_scolaire rapportDTO) {
        dto_rapport_scolaire created = rapportScolaireService.creerRapportScolaire(rapportDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<dto_rapport_scolaire>> listerRapports() {
        List<dto_rapport_scolaire> rapports = rapportScolaireService.listerRapportsScolaires();
        return ResponseEntity.ok(rapports);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_rapport_scolaire>> listerRapportsParEnfant(@PathVariable int enfantId) {
        List<dto_rapport_scolaire> rapports = rapportScolaireService.listerRapportsScolairesParEnfant(enfantId);
        return ResponseEntity.ok(rapports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_rapport_scolaire> getRapport(@PathVariable int id) {
        dto_rapport_scolaire rapport = rapportScolaireService.getRapportScolaireById(id);
        return ResponseEntity.ok(rapport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto_rapport_scolaire> updateRapport(@PathVariable int id, @RequestBody dto_rapport_scolaire rapportDTO) {
        dto_rapport_scolaire updated = rapportScolaireService.updateRapportScolaire(id, rapportDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRapport(@PathVariable int id) {
        rapportScolaireService.deleteRapportScolaire(id);
        return ResponseEntity.ok().build();
    }
}