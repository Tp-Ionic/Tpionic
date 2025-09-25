package com.example.demo.controller;

import com.example.demo.DTO.dto_parrainage;
import com.example.demo.service.service_parrainage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrainages")
@RequiredArgsConstructor
public class controller_parrainage {

    private final service_parrainage parrainageService;

    @PostMapping
    public ResponseEntity<dto_parrainage> creerParrainage(@RequestBody dto_parrainage parrainageDTO) {
        dto_parrainage created = parrainageService.creerParrainage(parrainageDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<dto_parrainage>> listerParrainages() {
        List<dto_parrainage> parrainages = parrainageService.listerParrainages();
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/enfant/{enfantId}")
    public ResponseEntity<List<dto_parrainage>> listerParrainagesParEnfant(@PathVariable int enfantId) {
        List<dto_parrainage> parrainages = parrainageService.listerParrainagesParEnfant(enfantId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/parrain/{parrainId}")
    public ResponseEntity<List<dto_parrainage>> listerParrainagesParParrain(@PathVariable int parrainId) {
        List<dto_parrainage> parrainages = parrainageService.listerParrainagesParParrain(parrainId);
        return ResponseEntity.ok(parrainages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_parrainage> getParrainage(@PathVariable int id) {
        dto_parrainage parrainage = parrainageService.getParrainageById(id);
        return ResponseEntity.ok(parrainage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto_parrainage> updateParrainage(@PathVariable int id, @RequestBody dto_parrainage parrainageDTO) {
        dto_parrainage updated = parrainageService.updateParrainage(id, parrainageDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParrainage(@PathVariable int id) {
        parrainageService.deleteParrainage(id);
        return ResponseEntity.ok().build();
    }
}