package com.example.demo.service;

import com.example.demo.model.Frais_scolaire;
import com.example.demo.model.Enfant;
import com.example.demo.DTO.dto_frais_scolaire;
import com.example.demo.repository.FraisScolaireRepository;
import com.example.demo.repository.EnfantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_frais_scolaire {

    private final FraisScolaireRepository fraisScolaireRepository;
    private final EnfantRepository enfantRepository;

    public dto_frais_scolaire creerFraisScolaire(dto_frais_scolaire fraisDTO) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(fraisDTO.getEnfantId());
        if (enfantOpt.isPresent()) {
            Frais_scolaire frais = new Frais_scolaire();
            frais.setClasse(fraisDTO.getClasse());
            frais.setAnnee_scolaire(fraisDTO.getAnneeScolaire());
            frais.setMontant(fraisDTO.getMontant());
            frais.setEnfant(enfantOpt.get());

            Frais_scolaire saved = fraisScolaireRepository.save(frais);
            return convertirEnDTO(saved);
        }
        throw new RuntimeException("Enfant non trouvé");
    }

    public List<dto_frais_scolaire> listerFraisScolaires() {
        return fraisScolaireRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public List<dto_frais_scolaire> listerFraisScolairesParEnfant(int enfantId) {
        return fraisScolaireRepository.findByEnfantId(enfantId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public dto_frais_scolaire getFraisScolaireById(int id) {
        Optional<Frais_scolaire> fraisOpt = fraisScolaireRepository.findById(id);
        if (fraisOpt.isPresent()) {
            return convertirEnDTO(fraisOpt.get());
        }
        throw new RuntimeException("Frais scolaire non trouvé");
    }

    public dto_frais_scolaire updateFraisScolaire(int id, dto_frais_scolaire fraisDTO) {
        Optional<Frais_scolaire> fraisOpt = fraisScolaireRepository.findById(id);
        if (fraisOpt.isPresent()) {
            Frais_scolaire frais = fraisOpt.get();
            frais.setClasse(fraisDTO.getClasse());
            frais.setAnnee_scolaire(fraisDTO.getAnneeScolaire());
            frais.setMontant(fraisDTO.getMontant());

            Frais_scolaire updated = fraisScolaireRepository.save(frais);
            return convertirEnDTO(updated);
        }
        throw new RuntimeException("Frais scolaire non trouvé");
    }

    public void deleteFraisScolaire(int id) {
        fraisScolaireRepository.deleteById(id);
    }

    private dto_frais_scolaire convertirEnDTO(Frais_scolaire frais) {
        dto_frais_scolaire dto = new dto_frais_scolaire();
        dto.setId(frais.getId());
        dto.setClasse(frais.getClasse());
        dto.setAnneeScolaire(frais.getAnnee_scolaire());
        dto.setMontant(frais.getMontant());
        dto.setEnfantId(frais.getEnfant().getId());
        return dto;
    }
}