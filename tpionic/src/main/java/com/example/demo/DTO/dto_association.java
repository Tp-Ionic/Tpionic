package com.example.demo.DTO;

import com.example.demo.model.Association;

public class dto_association {

    public static class CreateRequest {
        public String nom;
        public String email;
        public String motDePasse;
        public String telephone;
        public String adresse;
        public String pays;
        public String ville;
        public String description;
        public String logoUrl;
    }

    public static class UpdateRequest {
        public String nom;
        public String email;
        public String motDePasse;
        public String telephone;
        public String adresse;
        public String pays;
        public String ville;
        public String description;
        public String logoUrl;
        public Boolean actif;
        public String statut; // Pour l'admin : ACCEPTE, REFUSE
    }

    public static class Response {
        public Long id;
        public String nom;
        public String email;
        public String telephone;
        public String adresse;
        public String pays;
        public String ville;
        public String description;
        public String logoUrl;
        public Boolean actif;
        public String statut;
        public int nombreEnfants;
    }

    public static Response of(Association a) {
        Response r = new Response();
        r.id = a.getId();
        r.nom = a.getNom();
        r.email = a.getEmail();
        r.telephone = a.getTelephone();
        r.adresse = a.getAdresse();
        r.pays = a.getPays();
        r.ville = a.getVille();
        r.description = a.getDescription();
        r.logoUrl = a.getLogoUrl();
        r.actif = a.getActif();
        r.statut = a.getStatut();
        r.nombreEnfants = a.getEnfants() != null ? a.getEnfants().size() : 0;
        return r;
    }

    public static void applyUpdate(Association a, UpdateRequest u) {
        if (u == null) return;
        if (u.nom != null) a.setNom(u.nom);
        if (u.email != null) a.setEmail(u.email);
        if (u.motDePasse != null) a.setMotDePasse(u.motDePasse);
        if (u.telephone != null) a.setTelephone(u.telephone);
        if (u.adresse != null) a.setAdresse(u.adresse);
        if (u.pays != null) a.setPays(u.pays);
        if (u.ville != null) a.setVille(u.ville);
        if (u.description != null) a.setDescription(u.description);
        if (u.logoUrl != null) a.setLogoUrl(u.logoUrl);
        if (u.actif != null) a.setActif(u.actif);
        if (u.statut != null) a.setStatut(u.statut);
    }
}
