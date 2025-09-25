package com.example.demo.dto;

import com.example.demo.model.Parrain;

public class dto_parrain {

    public static class CreateRequest {
        public String nom;
        public String prenom;
        public String email;       // hérité de Utilisateur
        public String motDePasse;  // hérité de Utilisateur
        public String telephone;
        public String pays;
        public String ville;
        public String avatarUrl;
    }

    public static class UpdateRequest {
        public String nom;
        public String prenom;
        public String email;       // hérité
        public String motDePasse;  // hérité
        public String telephone;
        public String pays;
        public String ville;
        public String avatarUrl;
        public Boolean actif;      // hérité
    }

    public static class Response {
        public int id;
        public String nom;
        public String prenom;
        public String email;       // hérité
        public String telephone;
        public String pays;
        public String ville;
        public String avatarUrl;
        public Boolean actif;      // hérité
    }

    public static Response of(Parrain p){
        Response r = new Response();
        r.id = (int) p.getId();
        r.nom = p.getNom();
        r.prenom = p.getPrenom();
        r.email = p.getEmail();
        r.telephone = p.getTelephone();
        r.pays = p.getPays();
        r.ville = p.getVille();
        r.avatarUrl = p.getAvatarUrl();
        r.actif = p.getActif();
        return r;
    }

    public static void applyUpdate(Parrain p, UpdateRequest u){
        if (u == null) return;
        if (u.nom != null) p.setNom(u.nom);
        if (u.prenom != null) p.setPrenom(u.prenom);
        if (u.email != null) p.setEmail(u.email);
        if (u.motDePasse != null) p.setMotDePasse(u.motDePasse);
        if (u.telephone != null) p.setTelephone(u.telephone);
        if (u.pays != null) p.setPays(u.pays);
        if (u.ville != null) p.setVille(u.ville);
        if (u.avatarUrl != null) p.setAvatarUrl(u.avatarUrl);
        if (u.actif != null) p.setActif(u.actif);
    }


}