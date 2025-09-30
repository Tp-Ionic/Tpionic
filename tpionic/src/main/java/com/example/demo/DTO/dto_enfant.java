package com.example.demo.DTO;

import com.example.demo.model.Enfant;

import java.util.Arrays;
import java.util.List;

public class dto_enfant {

    public static class CreateRequest {
        public String nom;
        public String prenom;
        public String dateNaissance;
        public String adresse;
        public int age;
        public String apropos_de_enfant;
        public Long associationId;
        public Long parentId; // Pour utiliser un parent existant
        
        // Informations du parent (si création d'un nouveau parent)
        public String parentNom;
        public String parentPrenom;
        public String parentEmail;
        public String parentMotDePasse;
        public String parentTelephone;
        public String parentAdresse;
        public String parentPays;
        public String parentVille;
        public String parentProfession;
        public String parentRelationAvecEnfant;
    }

    public static class UpdateRequest {
        public String nom;
        public String prenom;
        public String dateNaissance;
        public String adresse;
        public int age;
        public String apropos_de_enfant;
    }

    public static class Response {
        public Long id;
        public String nom;
        public String prenom;
        public String dateNaissance;
        public String adresse;
        public int age;
        public String apropos_de_enfant;
        public List<String> bulletinsPdfUrls;
        public List<String> photosActivitesUrls;
        public List<String> listesPresenceUrls;
        public Long associationId;
        public String associationNom;
        
        // Informations du parent
        public Integer parentId;
        public String parentNom;
        public String parentPrenom;
        public String parentEmail;
        public String parentTelephone;
        public String parentAdresse;
        public String parentPays;
        public String parentVille;
        public String parentProfession;
        public String parentRelationAvecEnfant;
    }

    public static class FileUploadRequest {
        public Long enfantId;
        public String typeFichier; // "bulletin", "photo", "presence"
    }

    public static Response of(Enfant e) {
        Response r = new Response();
        r.id = e.getId();
        r.nom = e.getNom();
        r.prenom = e.getPrenom();
        r.dateNaissance = e.getDateNaissance();
        r.adresse = e.getAdresse();
        r.age = e.getAge();
        r.apropos_de_enfant = e.getApropos_de_enfant();
        r.associationId = e.getAssociation() != null ? e.getAssociation().getId() : null;
        r.associationNom = e.getAssociation() != null ? e.getAssociation().getNom() : null;
        
        // Informations du parent
        r.parentId = e.getParent() != null ? e.getParent().getId() : null;
        r.parentNom = e.getParent() != null ? e.getParent().getNom() : null;
        r.parentPrenom = e.getParent() != null ? e.getParent().getPrenom() : null;
        r.parentEmail = e.getParent() != null ? e.getParent().getEmail() : null;
        r.parentTelephone = e.getParent() != null ? e.getParent().getTelephone() : null;
        r.parentAdresse = e.getParent() != null ? e.getParent().getAdresse() : null;
        r.parentPays = e.getParent() != null ? e.getParent().getPays() : null;
        r.parentVille = e.getParent() != null ? e.getParent().getVille() : null;
        r.parentProfession = e.getParent() != null ? e.getParent().getProfession() : null;
        r.parentRelationAvecEnfant = e.getParent() != null ? e.getParent().getRelationAvecEnfant() : null;
        
        // Conversion des URLs séparés par virgule en listes
        r.bulletinsPdfUrls = e.getBulletinsPdfUrls() != null ? 
            Arrays.asList(e.getBulletinsPdfUrls().split(",")) : List.of();
        r.photosActivitesUrls = e.getPhotosActivitesUrls() != null ? 
            Arrays.asList(e.getPhotosActivitesUrls().split(",")) : List.of();
        r.listesPresenceUrls = e.getListesPresenceUrls() != null ? 
            Arrays.asList(e.getListesPresenceUrls().split(",")) : List.of();
        
        return r;
    }

    public static void applyUpdate(Enfant e, UpdateRequest u) {
        if (u == null) return;
        if (u.nom != null) e.setNom(u.nom);
        if (u.prenom != null) e.setPrenom(u.prenom);
        if (u.dateNaissance != null) e.setDateNaissance(u.dateNaissance);
        if (u.adresse != null) e.setAdresse(u.adresse);
        if (u.age > 0) e.setAge(u.age);
        if (u.apropos_de_enfant != null) e.setApropos_de_enfant(u.apropos_de_enfant);
    }
}
