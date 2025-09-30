package com.example.demo.service;

import com.example.demo.DTO.DtoUtilisateur;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser {
    UtilisateurRepository utilisateurRepository;
    public ServiceUser(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository=utilisateurRepository;
    }
    public DtoUtilisateur activerUser(int id){
        Utilisateur user=utilisateurRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Utilisateur non trouvé")
        );
        user.setActif(true);
        utilisateurRepository.save(user);
        return new DtoUtilisateur(user.getEmail(),user.getRole(), user.getActif());
    }

    public DtoUtilisateur desactiverUser(int id){
        Utilisateur user=utilisateurRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Utilisateur non trouvé")
        );
        user.setActif(false);
        utilisateurRepository.save(user);
        return new DtoUtilisateur(user.getEmail(), user.getRole(), user.getActif());
    }

}
