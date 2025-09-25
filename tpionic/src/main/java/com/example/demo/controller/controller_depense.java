package com.example.demo.controller;

import com.example.demo.DTO.dto_depense;
import com.example.demo.service.service_depense;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depenses")
@RequiredArgsConstructor
public class controller_depense {

    private final service_depense depenseService;

    @PostMapping
    public ResponseEntity<dto_depense> creerDepense(@RequestBody dto_depense depenseDTO) {
        dto_depense created = depenseService.creerDepense(depenseDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<dto_depense>> listerDepenses() {
        List<dto_depense> depenses = depenseService.listerDepenses();
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<dto_depense>> listerDepensesParCategorie(@PathVariable String categorie) {
        List<dto_depense> depenses = depenseService.listerDepensesParCategorie(categorie);
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_depense> getDepense(@PathVariable int id) {
        dto_depense depense = depenseService.getDepenseById(id);
        return ResponseEntity.ok(depense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto_depense> updateDepense(@PathVariable int id, @RequestBody dto_depense depenseDTO) {
        dto_depense updated = depenseService.updateDepense(id, depenseDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable int id) {
        depenseService.deleteDepense(id);
        return ResponseEntity.ok().build();
    }
}