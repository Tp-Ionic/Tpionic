package com.example.demo.service;

import com.example.demo.dto.dto_parrain;
import com.example.demo.dto.dto_parrain.CreateRequest;
import com.example.demo.dto.dto_parrain.UpdateRequest;
import com.example.demo.model.Parrain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class service_parrain {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public dto_parrain.Response creer(CreateRequest req){
        Parrain p = new Parrain();
        p.setNom(req.nom);
        p.setPrenom(req.prenom);
        p.setEmail(req.email);             // hérité de Utilisateur
        p.setMotDePasse(req.motDePasse);   // hérité de Utilisateur
        p.setTelephone(req.telephone);
        p.setPays(req.pays);
        p.setVille(req.ville);
        p.setAvatarUrl(req.avatarUrl);
        p.setActif(true);                  // hérité
        em.persist(p);
        return dto_parrain.of(p);
    }

    public dto_parrain.Response get(int id){
        Parrain p = em.find(Parrain.class, (long) id);
        if (p == null) throw new IllegalArgumentException("Parrain introuvable");
        return dto_parrain.of(p);
    }

    public List<dto_parrain.Response> list(){
        return em.createQuery("select p from Parrain p order by p.id desc", Parrain.class)
                .getResultList()
                .stream().map(dto_parrain::of).toList();
    }

    @Transactional
    public dto_parrain.Response update(int id, UpdateRequest req){
        Parrain p = em.find(Parrain.class, (long) id);
        if (p == null) throw new IllegalArgumentException("Parrain introuvable");
        dto_parrain.applyUpdate(p, req);
        em.merge(p);
        return dto_parrain.of(p);
    }

    @Transactional
    public void delete(int id){
        Parrain p = em.find(Parrain.class, (long) id);
        if (p != null) em.remove(p);
    }
}