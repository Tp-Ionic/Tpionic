package com.example.demo.controller;

import com.example.demo.DTO.DtoUtilisateur;
import com.example.demo.DTO.dto_association;
import com.example.demo.service.ServiceAssociation;
import com.example.demo.service.ServiceUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ControllerAdmin {
    ServiceAssociation serviceAssociation;
    ServiceUser users;
    public ControllerAdmin(ServiceAssociation serviceAssociation,ServiceUser user){
        this.serviceAssociation=serviceAssociation;
        this.users=user;
    }
    //valider association
    @PostMapping("/{id}/valider")
    public ResponseEntity<?> validerAssociation(@PathVariable int id) {
        try {
            dto_association.Response response = serviceAssociation.validerAssociation(id, "ACCEPTE");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation: " + e.getMessage());
        }
    }

    //Refuser association
    @PostMapping("/{id}/refuser")
    public ResponseEntity<?> refuserAssociation(@PathVariable int id) {
        try {
            dto_association.Response response = serviceAssociation.validerAssociation(id, "REFUSE");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation: " + e.getMessage());
        }
    }
    @PostMapping("/{id}/activer")
    public ResponseEntity<DtoUtilisateur> activerUser(@PathVariable int id){
        DtoUtilisateur user= users.activerUser(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/{id}/desactiver")
    public ResponseEntity<DtoUtilisateur> desactiverUser(@PathVariable int id){
        DtoUtilisateur user= users.desactiverUser(id);
        return ResponseEntity.ok(user);
    }

}
