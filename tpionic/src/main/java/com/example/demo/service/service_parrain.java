package com.example.demo.service;

import com.example.demo.DTO.dto_parrain;
import com.example.demo.DTO.dto_parrain.CreateRequest;
import com.example.demo.DTO.dto_parrain.UpdateRequest;
import com.example.demo.DTO.dto_enfant;
import com.example.demo.DTO.dto_rapport_scolaire;
import com.example.demo.DTO.dto_confirmation_paiement;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
import com.example.demo.model.Parrainage;
import com.example.demo.model.paiement;
import com.example.demo.model.ConfirmationPaiement;
import com.example.demo.repository.ParrainRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class service_parrain {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ParrainRepository parrainRepository;

    @Autowired
    private service_rapport_scolaire rapportService;

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

    @Transactional(readOnly = true)
    public dto_parrain.Response get(int id){
        Parrain p = em.find(Parrain.class, (long) id);
        if (p == null) throw new IllegalArgumentException("Parrain introuvable");
        return dto_parrain.of(p);
    }

    @Transactional(readOnly = true)
    public List<dto_parrain.Response> list(){
        return em.createQuery("select p from Parrain p order by p.id desc", Parrain.class)
                .getResultList()
                .stream().map(dto_parrain::of).toList();
    }

    @Transactional(readOnly = true)
    public List<dto_parrain.Response> getParrainsActifs(){
        return parrainRepository.findByActif(true)
                .stream()
                .map(dto_parrain::of)
                .toList();
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

    // Authentification supprimée - sera gérée par Spring Security

    // ===== NOUVELLES FONCTIONNALITÉS POUR LE PARRAIN =====

    // Obtenir les enfants parrainés par ce parrain
    @Transactional(readOnly = true)
    public List<dto_enfant.Response> getEnfantsParraines(Integer parrainId) {
        List<Parrainage> parrainages = em.createQuery(
            "select p from Parrainage p where p.parrain.id = :parrainId and p.statut = 'ACCEPTE'", 
            Parrainage.class)
            .setParameter("parrainId", parrainId)
            .getResultList();

        return parrainages.stream()
            .map(Parrainage::getEnfant)
            .map(dto_enfant::of)
            .collect(Collectors.toList());
    }

    // Obtenir les rapports scolaires d'un enfant parrainé
    @Transactional(readOnly = true)
    public List<dto_rapport_scolaire.Response> getRapportsScolairesEnfant(Integer parrainId, Long enfantId, String anneeScolaire) {
        // Vérifier que le parrain parraine bien cet enfant
        Parrainage parrainage = em.createQuery(
            "select p from Parrainage p where p.parrain.id = :parrainId and p.enfant.id = :enfantId and p.statut = 'ACCEPTE'", 
            Parrainage.class)
            .setParameter("parrainId", parrainId)
            .setParameter("enfantId", enfantId)
            .getSingleResult();

        if (parrainage == null) {
            throw new RuntimeException("Vous ne parrainez pas cet enfant");
        }

        return rapportService.getRapportsByEnfant(enfantId, anneeScolaire);
    }

    // Obtenir les bulletins PDF d'un enfant parrainé
    @Transactional(readOnly = true)
    public dto_enfant.Response getBulletinsEnfant(Integer parrainId, Long enfantId) {
        // Vérifier que le parrain parraine bien cet enfant
        Parrainage parrainage = em.createQuery(
            "select p from Parrainage p where p.parrain.id = :parrainId and p.enfant.id = :enfantId and p.statut = 'ACCEPTE'", 
            Parrainage.class)
            .setParameter("parrainId", parrainId)
            .setParameter("enfantId", enfantId)
            .getSingleResult();

        if (parrainage == null) {
            throw new RuntimeException("Vous ne parrainez pas cet enfant");
        }

        return dto_enfant.of(parrainage.getEnfant());
    }

    // Obtenir les photos d'activités d'un enfant parrainé
    @Transactional(readOnly = true)
    public dto_enfant.Response getPhotosActivitesEnfant(Integer parrainId, Long enfantId) {
        // Vérifier que le parrain parraine bien cet enfant
        Parrainage parrainage = em.createQuery(
            "select p from Parrainage p where p.parrain.id = :parrainId and p.enfant.id = :enfantId and p.statut = 'ACCEPTE'", 
            Parrainage.class)
            .setParameter("parrainId", parrainId)
            .setParameter("enfantId", enfantId)
            .getSingleResult();

        if (parrainage == null) {
            throw new RuntimeException("Vous ne parrainez pas cet enfant");
        }

        return dto_enfant.of(parrainage.getEnfant());
    }

    // Obtenir les listes de présence d'un enfant parrainé
    @Transactional(readOnly = true)
    public dto_enfant.Response getListesPresenceEnfant(Integer parrainId, Long enfantId) {
        // Vérifier que le parrain parraine bien cet enfant
        Parrainage parrainage = em.createQuery(
            "select p from Parrainage p where p.parrain.id = :parrainId and p.enfant.id = :enfantId and p.statut = 'ACCEPTE'", 
            Parrainage.class)
            .setParameter("parrainId", parrainId)
            .setParameter("enfantId", enfantId)
            .getSingleResult();

        if (parrainage == null) {
            throw new RuntimeException("Vous ne parrainez pas cet enfant");
        }

        return dto_enfant.of(parrainage.getEnfant());
    }

    // Obtenir les confirmations de paiement du parrain
    @Transactional(readOnly = true)
    public List<dto_confirmation_paiement.Response> getConfirmationsPaiement(Integer parrainId) {
        List<paiement> paiements = em.createQuery(
            "select p from paiement p where p.parrain.id = :parrainId", 
            paiement.class)
            .setParameter("parrainId", parrainId)
            .getResultList();

        return paiements.stream()
            .flatMap(p -> p.getConfirmations().stream())
            .map(dto_confirmation_paiement::of)
            .collect(Collectors.toList());
    }

    // Obtenir le résumé complet d'un enfant parrainé
    @Transactional(readOnly = true)
    public dto_parrain.ResumeEnfantResponse getResumeCompletEnfant(Integer parrainId, Long enfantId) {
        // Vérifier que le parrain parraine bien cet enfant
        Parrainage parrainage = em.createQuery(
            "select p from Parrainage p where p.parrain.id = :parrainId and p.enfant.id = :enfantId and p.statut = 'ACCEPTE'", 
            Parrainage.class)
            .setParameter("parrainId", parrainId)
            .setParameter("enfantId", enfantId)
            .getSingleResult();

        if (parrainage == null) {
            throw new RuntimeException("Vous ne parrainez pas cet enfant");
        }

        Enfant enfant = parrainage.getEnfant();
        dto_parrain.ResumeEnfantResponse resume = new dto_parrain.ResumeEnfantResponse();
        
        // Informations de base de l'enfant
        resume.enfantId = enfant.getId();
        resume.enfantNom = enfant.getNom() + " " + enfant.getPrenom();
        resume.age = enfant.getAge();
        resume.classe = enfant.getApropos_de_enfant(); // Utiliser ce champ pour la classe
        resume.associationNom = enfant.getAssociation() != null ? enfant.getAssociation().getNom() : null;
        
        // Informations du parrainage
        resume.statutParrainage = parrainage.getStatut();
        resume.montantMensuel = parrainage.getMontantMensuel();
        resume.dateDebut = parrainage.getDateDebut();
        
        // URLs des fichiers
        resume.bulletinsPdfUrls = enfant.getBulletinsPdfUrls() != null ? 
            List.of(enfant.getBulletinsPdfUrls().split(",")) : List.of();
        resume.photosActivitesUrls = enfant.getPhotosActivitesUrls() != null ? 
            List.of(enfant.getPhotosActivitesUrls().split(",")) : List.of();
        resume.listesPresenceUrls = enfant.getListesPresenceUrls() != null ? 
            List.of(enfant.getListesPresenceUrls().split(",")) : List.of();
        
        // Rapports scolaires récents
        resume.rapportsScolairesRecents = rapportService.getRapportsByEnfant(enfantId, null)
            .stream()
            .limit(5) // Les 5 plus récents
            .collect(Collectors.toList());
        
        return resume;
    }
}