package com.example.demo.controller;

import com.example.demo.DTO.dto_parrain;
import com.example.demo.service.service_parrain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrains")
@RequiredArgsConstructor
public class controller_parrain {

    private final service_parrain parrainService;

    @PostMapping
    public ResponseEntity<dto_parrain> creerParrain(@RequestBody dto_parrain parrainDTO) {
        dto_parrain created = parrainService.creerParrain(parrainDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<dto_parrain>> listerParrains() {
        List<dto_parrain> parrains = parrainService.listerParrains();
        return ResponseEntity.ok(parrains);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_parrain> getParrain(@PathVariable int id) {
        dto_parrain parrain = parrainService.getParrainById(id);
        return ResponseEntity.ok(parrain);
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto_parrain> updateParrain(@PathVariable int id, @RequestBody dto_parrain parrainDTO) {
        dto_parrain updated = parrainService.updateParrain(id, parrainDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParrain(@PathVariable int id) {
        parrainService.deleteParrain(id);
        return ResponseEntity.ok().build();
    }
}