package com.example.demo.DTO;

import com.example.demo.model.Deplacement;
import com.example.demo.model.NotificationParrain;
import com.example.demo.model.Parent;

import java.time.LocalDate;
import java.util.List;

public class dto_parent {

    // DTO pour la création d'un parent
    public static class CreateRequest {
        public String nom;
        public String prenom;
        public String email;
        public String motDePasse;
        public String telephone;
        public String adresse;
        public String pays;
        public String ville;
        public String profession;
        public String relationAvecEnfant;
    }

    // DTO pour l'authentification
    public static class AuthenticateRequest {
        public String email;
        public String motDePasse;
    }

    // DTO pour la connexion
    public static class Response {
        public Integer id;
        public String nom;
        public String prenom;
        public String email;
        public String telephone;
        public String adresse;
        public String pays;
        public String ville;
        public String profession;
        public String relationAvecEnfant;
        public Boolean actif;
    }

    // DTO pour les enfants du parent
    public static class EnfantResponse {
        public int id;
        public String nom;
        public String prenom;
        public String dateNaissance;
        public int age;
        public String classe;
        public String anneeScolaire;
        public String associationNom;
        public List<String> bulletinsPdfUrls;
        public List<String> photosActivitesUrls;
        public List<String> listesPresenceUrls;
    }

    // DTO pour les bulletins (via URLs)
    public static class BulletinResponse {
        public int enfantId;
        public String enfantNom;
        public List<String> bulletinsPdfUrls;
        public String anneeScolaire;
    }

    // DTO pour les présences (via URLs)
    public static class PresenceResponse {
        public int enfantId;
        public String enfantNom;
        public List<String> listesPresenceUrls;
        public String anneeScolaire;
    }

    // DTO pour les déplacements
    public static class DeplacementRequest {
        public String nom;
        public String prenom;
        public String motif;
        public String nouvelleAdresse;
        public String nouveauTelephone;
        public LocalDate dateDeplacement;
        public String notes;
        public Long enfantId;
    }

    public static class DeplacementResponse {
        public Long id;
        public String nom;
        public String prenom;
        public String motif;
        public String nouvelleAdresse;
        public String nouveauTelephone;
        public LocalDate dateDeplacement;
        public String statut;
        public String notes;
        public String enfantNom;
        public String parentNom;
    }

    // DTO pour la mise à jour du profil
    public static class UpdateProfilRequest {
        public String nom;
        public String prenom;
        public String email;
        public String motDePasse;
        public String telephone;
        public String adresse;
        public String pays;
        public String ville;
        public String profession;
        public String relationAvecEnfant;
    }

    // DTO pour les reçus de dépenses
    public static class RecuDepenseResponse {
        public Long id;
        public String description;
        public Double montant;
        public String typeDepense;
        public LocalDate dateDepense;
        public String justificatifUrl;
        public String notes;
        public String enfantNom;
        public String parrainNom;
        public String paiementReference;
    }

    // DTO pour les notifications aux parrains
    public static class NotificationParrainRequest {
        public String description;
        public String message;
        public String typeNotification;
        public Long enfantId;
        public Integer parrainId;  // Changed from Long to Integer
    }

    public static class NotificationParrainResponse {
        public Long id;
        public String description;
        public String message;
        public String statut;
        public LocalDate dateEnvoi;
        public String typeNotification;
        public String enfantNom;
        public String parrainNom;
        public String parentNom;
    }

    // Méthodes de conversion
    public static Response of(Parent p) {
        Response r = new Response();
        r.id = p.getId();
        r.nom = p.getNom();
        r.prenom = p.getPrenom();
        r.email = p.getEmail();
        r.telephone = p.getTelephone();
        r.adresse = p.getAdresse();
        r.pays = p.getPays();
        r.ville = p.getVille();
        r.profession = p.getProfession();
        r.relationAvecEnfant = p.getRelationAvecEnfant();
        r.actif = p.getActif();
        return r;
    }

    public static EnfantResponse ofEnfant(com.example.demo.model.Enfant e) {
        EnfantResponse r = new EnfantResponse();
        r.id = e.getId();
        r.nom = e.getNom();
        r.prenom = e.getPrenom();
        r.dateNaissance = e.getDateNaissance();
        r.age = e.getAge();
        r.associationNom = e.getAssociation() != null ? e.getAssociation().getNom() : null;

        // Conversion des URLs séparés par virgule en listes
        r.bulletinsPdfUrls = e.getBulletinsPdfUrls() != null ?
                List.of(e.getBulletinsPdfUrls().split(",")) : List.of();
        r.photosActivitesUrls = e.getPhotosActivitesUrls() != null ?
                List.of(e.getPhotosActivitesUrls().split(",")) : List.of();
        r.listesPresenceUrls = e.getListesPresenceUrls() != null ?
                List.of(e.getListesPresenceUrls().split(",")) : List.of();

        return r;
    }

    // Méthode pour créer un BulletinResponse à partir d'un Enfant
    public static BulletinResponse ofBulletinFromEnfant(com.example.demo.model.Enfant e, String anneeScolaire) {
        BulletinResponse r = new BulletinResponse();
        r.enfantId = e.getId();
        r.enfantNom = e.getNom() + " " + e.getPrenom();
        r.anneeScolaire = anneeScolaire;
        r.bulletinsPdfUrls = e.getBulletinsPdfUrls() != null ?
                List.of(e.getBulletinsPdfUrls().split(",")) : List.of();
        return r;
    }

    // Méthode pour créer un PresenceResponse à partir d'un Enfant
    public static PresenceResponse ofPresenceFromEnfant(com.example.demo.model.Enfant e, String anneeScolaire) {
        PresenceResponse r = new PresenceResponse();
        r.enfantId = e.getId();
        r.enfantNom = e.getNom() + " " + e.getPrenom();
        r.anneeScolaire = anneeScolaire;
        r.listesPresenceUrls = e.getListesPresenceUrls() != null ?
                List.of(e.getListesPresenceUrls().split(",")) : List.of();
        return r;
    }

    public static DeplacementResponse ofDeplacement(Deplacement d) {
        DeplacementResponse r = new DeplacementResponse();
        r.id = d.getId();
        r.nom = d.getNom();
        r.prenom = d.getPrenom();
        r.motif = d.getMotif();
        r.nouvelleAdresse = d.getNouvelleAdresse();
        r.nouveauTelephone = d.getNouveauTelephone();
        r.dateDeplacement = d.getDateDeplacement();
        r.statut = d.getStatut();
        r.notes = d.getNotes();
        r.enfantNom = d.getEnfant() != null ? d.getEnfant().getNom() + " " + d.getEnfant().getPrenom() : null;
        r.parentNom = d.getParent() != null ? d.getParent().getNom() + " " + d.getParent().getPrenom() : null;
        return r;
    }

    public static NotificationParrainResponse ofNotification(NotificationParrain n) {
        NotificationParrainResponse r = new NotificationParrainResponse();
        r.id = n.getId();
        r.description = n.getDescription();
        r.message = n.getMessage();
        r.statut = n.getStatut();
        r.dateEnvoi = n.getDateEnvoi();
        r.typeNotification = n.getTypeNotification();
        r.enfantNom = n.getEnfant() != null ? n.getEnfant().getNom() + " " + n.getEnfant().getPrenom() : null;
        r.parrainNom = n.getParrain() != null ? n.getParrain().getNom() + " " + n.getParrain().getPrenom() : null;
        r.parentNom = n.getParent() != null ? n.getParent().getNom() + " " + n.getParent().getPrenom() : null;
        return r;
    }

    // Méthode pour appliquer les modifications
    public static void applyUpdate(Parent p, UpdateProfilRequest u) {
        if (u == null) return;
        if (u.nom != null) p.setNom(u.nom);
        if (u.prenom != null) p.setPrenom(u.prenom);
        if (u.email != null) p.setEmail(u.email);
        if (u.motDePasse != null) p.setMotDePasse(u.motDePasse);
        if (u.telephone != null) p.setTelephone(u.telephone);
        if (u.adresse != null) p.setAdresse(u.adresse);
        if (u.pays != null) p.setPays(u.pays);
        if (u.ville != null) p.setVille(u.ville);
        if (u.profession != null) p.setProfession(u.profession);
        if (u.relationAvecEnfant != null) p.setRelationAvecEnfant(u.relationAvecEnfant);
    }
}
