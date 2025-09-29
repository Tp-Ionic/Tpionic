package com.example.demo.controller;

import com.example.demo.DTO.dto_parent;
import com.example.demo.service.service_parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parents")
public class controller_parent {

    @Autowired
    private service_parent parentService;

    // Créer un compte parent
    @PostMapping
    public ResponseEntity<dto_parent.Response> createParent(@RequestBody dto_parent.CreateRequest request) {
        try {
            dto_parent.Response response = parentService.createParent(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtenir tous les parents
    @GetMapping
    public ResponseEntity<List<dto_parent.Response>> getAllParents() {
        List<dto_parent.Response> responses = parentService.getAllParents();
        return ResponseEntity.ok(responses);
    }

    // Obtenir un parent par ID
    @GetMapping("/{id}")
    public ResponseEntity<dto_parent.Response> getParentById(@PathVariable int id) {
        Optional<dto_parent.Response> response = parentService.getParentById(id);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Obtenir un parent par email
    @GetMapping("/email/{email}")
    public ResponseEntity<dto_parent.Response> getParentByEmail(@PathVariable String email) {
        Optional<dto_parent.Response> response = parentService.getParentByEmail(email);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Authentification supprimée - sera gérée par Spring Security

    // Modifier un parent
    @PutMapping("/{id}")
    public ResponseEntity<dto_parent.Response> updateParent(@PathVariable int id, @RequestBody dto_parent.UpdateProfilRequest request) {
        try {
            dto_parent.Response response = parentService.updateParent(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer un parent
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable int id) {
        try {
            parentService.deleteParent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Rechercher des parents par nom
    @GetMapping("/search/nom/{nom}")
    public ResponseEntity<List<dto_parent.Response>> searchParentsByName(@PathVariable String nom) {
        List<dto_parent.Response> responses = parentService.searchParentsByName(nom);
        return ResponseEntity.ok(responses);
    }

    // Rechercher des parents par prénom
    @GetMapping("/search/prenom/{prenom}")
    public ResponseEntity<List<dto_parent.Response>> searchParentsByPrenom(@PathVariable String prenom) {
        List<dto_parent.Response> responses = parentService.searchParentsByPrenom(prenom);
        return ResponseEntity.ok(responses);
    }

    // Rechercher des parents par pays
    @GetMapping("/search/pays/{pays}")
    public ResponseEntity<List<dto_parent.Response>> searchParentsByPays(@PathVariable String pays) {
        List<dto_parent.Response> responses = parentService.searchParentsByPays(pays);
        return ResponseEntity.ok(responses);
    }

    // Rechercher des parents par ville
    @GetMapping("/search/ville/{ville}")
    public ResponseEntity<List<dto_parent.Response>> searchParentsByVille(@PathVariable String ville) {
        List<dto_parent.Response> responses = parentService.searchParentsByVille(ville);
        return ResponseEntity.ok(responses);
    }

    // Rechercher des parents par profession
    @GetMapping("/search/profession/{profession}")
    public ResponseEntity<List<dto_parent.Response>> searchParentsByProfession(@PathVariable String profession) {
        List<dto_parent.Response> responses = parentService.searchParentsByProfession(profession);
        return ResponseEntity.ok(responses);
    }

    // Rechercher des parents par relation avec l'enfant
    @GetMapping("/search/relation/{relationAvecEnfant}")
    public ResponseEntity<List<dto_parent.Response>> searchParentsByRelation(@PathVariable String relationAvecEnfant) {
        List<dto_parent.Response> responses = parentService.searchParentsByRelation(relationAvecEnfant);
        return ResponseEntity.ok(responses);
    }
}