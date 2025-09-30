package com.example.demo.controller;

import com.example.demo.DTO.dto_association;
import com.example.demo.DTO.dto_enfant;
import com.example.demo.model.Association;
import com.example.demo.model.Enfant;
import com.example.demo.service.FileUploadService;
import com.example.demo.service.service_association;
import com.example.demo.service.service_enfant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/associations")
@CrossOrigin(origins = "*")
public class controller_association {

    @Autowired
    private service_association associationService;

    @Autowired
    private service_enfant enfantService;

    @Autowired
    private FileUploadService fileUploadService;

    // CRUD pour les associations
    @PostMapping
    public ResponseEntity<?> createAssociation(@RequestBody dto_association.CreateRequest request) {
        try {
            // Validation des champs obligatoires
            if (request.nom == null || request.nom.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le nom de l'association est obligatoire");
            }
            if (request.email == null || request.email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("L'email de l'association est obligatoire");
            }
            if (request.motDePasse == null || request.motDePasse.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le mot de passe est obligatoire");
            }
            if (request.telephone == null || request.telephone.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le téléphone est obligatoire");
            }
            if (request.adresse == null || request.adresse.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("L'adresse est obligatoire");
            }
            if (request.pays == null || request.pays.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le pays est obligatoire");
            }
            if (request.ville == null || request.ville.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La ville est obligatoire");
            }

            dto_association.Response response = associationService.createAssociation(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<dto_association.Response>> getAllAssociations() {
        List<dto_association.Response> associations = associationService.getAllAssociations();
        return ResponseEntity.ok(associations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dto_association.Response> getAssociationById(@PathVariable Long id) {
        Optional<dto_association.Response> association = associationService.getAssociationById(id);
        return association.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto_association.Response> updateAssociation(@PathVariable Long id, @RequestBody dto_association.UpdateRequest request) {
        try {
            dto_association.Response response = associationService.updateAssociation(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable Long id) {
        try {
            associationService.deleteAssociation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Authentification supprimée - sera gérée par Spring Security
    
    // Endpoints pour l'admin
    @GetMapping("/en-attente")
    public ResponseEntity<List<dto_association.Response>> getAssociationsEnAttente() {
        List<dto_association.Response> associations = associationService.getAssociationsEnAttente();
        return ResponseEntity.ok(associations);
    }
    
    @PutMapping("/{id}/valider")
    public ResponseEntity<?> validerAssociation(@PathVariable Long id, @RequestParam String statut) {
        try {
            dto_association.Response response = associationService.validerAssociation(id, statut);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation: " + e.getMessage());
        }
    }

    // Gestion des enfants par association
    @PostMapping("/{associationId}/enfants")
    public ResponseEntity<dto_enfant.Response> addEnfant(@PathVariable Long associationId, @RequestBody dto_enfant.CreateRequest request) {
        try {
            request.associationId = associationId;
            dto_enfant.Response response = enfantService.createEnfant(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{associationId}/enfants")
    public ResponseEntity<List<dto_enfant.Response>> getEnfantsByAssociation(@PathVariable Long associationId) {
        List<dto_enfant.Response> enfants = enfantService.getEnfantsByAssociation(associationId);
        return ResponseEntity.ok(enfants);
    }

    // Upload de fichiers pour les enfants
    @PostMapping("/{associationId}/enfants/{enfantId}/upload-bulletin")
    public ResponseEntity<String> uploadBulletin(@PathVariable Long associationId, 
                                                @PathVariable Long enfantId, 
                                                @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileUploadService.uploadFile(file, enfantId, "bulletin");
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{associationId}/enfants/{enfantId}/upload-photo")
    public ResponseEntity<String> uploadPhotoActivite(@PathVariable Long associationId, 
                                                      @PathVariable Long enfantId, 
                                                      @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileUploadService.uploadFile(file, enfantId, "photo");
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{associationId}/enfants/{enfantId}/upload-presence")
    public ResponseEntity<String> uploadListePresence(@PathVariable Long associationId, 
                                                     @PathVariable Long enfantId, 
                                                     @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileUploadService.uploadFile(file, enfantId, "presence");
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Téléchargement de fichiers
    @GetMapping("/files/**")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filePath) {
        try {
            byte[] fileContent = fileUploadService.downloadFile(filePath);
            return ResponseEntity.ok(fileContent);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
