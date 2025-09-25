package com.example.demo.service;

import com.example.demo.model.Rapport_scolaire;
import com.example.demo.model.Enfant;
import com.example.demo.DTO.dto_rapport_scolaire;
import com.example.demo.repository.RapportScolaireRepository;
import com.example.demo.repository.EnfantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_rapport_scolaire {

    private final RapportScolaireRepository rapportScolaireRepository;
    private final EnfantRepository enfantRepository;

    public dto_rapport_scolaire creerRapportScolaire(dto_rapport_scolaire rapportDTO) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(rapportDTO.getEnfantId());
        if (enfantOpt.isPresent()) {
            Rapport_scolaire rapport = new Rapport_scolaire();
            rapport.setAnnee_scolaire(rapportDTO.getAnneeScolaire());
            rapport.setUrlBulletin(rapportDTO.getUrlBulletin());
            rapport.setUrlPhotoactivite(rapportDTO.getUrlPhotoActivite());
            rapport.setUrlPresence(rapportDTO.getUrlPresence());
            rapport.setDate(rapportDTO.getDate());
            rapport.setEnfant(enfantOpt.get());

            Rapport_scolaire saved = rapportScolaireRepository.save(rapport);
            return convertirEnDTO(saved);
        }
        throw new RuntimeException("Enfant non trouvé");
    }

    public List<dto_rapport_scolaire> listerRapportsScolaires() {
        return rapportScolaireRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public List<dto_rapport_scolaire> listerRapportsScolairesParEnfant(int enfantId) {
        return rapportScolaireRepository.findByEnfantId(enfantId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public dto_rapport_scolaire getRapportScolaireById(int id) {
        Optional<Rapport_scolaire> rapportOpt = rapportScolaireRepository.findById(id);
        if (rapportOpt.isPresent()) {
            return convertirEnDTO(rapportOpt.get());
        }
        throw new RuntimeException("Rapport scolaire non trouvé");
    }

    public dto_rapport_scolaire updateRapportScolaire(int id, dto_rapport_scolaire rapportDTO) {
        Optional<Rapport_scolaire> rapportOpt = rapportScolaireRepository.findById(id);
        if (rapportOpt.isPresent()) {
            Rapport_scolaire rapport = rapportOpt.get();
            rapport.setAnnee_scolaire(rapportDTO.getAnneeScolaire());
            rapport.setUrlBulletin(rapportDTO.getUrlBulletin());
            rapport.setUrlPhotoactivite(rapportDTO.getUrlPhotoActivite());
            rapport.setUrlPresence(rapportDTO.getUrlPresence());
            rapport.setDate(rapportDTO.getDate());

            Rapport_scolaire updated = rapportScolaireRepository.save(rapport);
            return convertirEnDTO(updated);
        }
        throw new RuntimeException("Rapport scolaire non trouvé");
    }

    public void deleteRapportScolaire(int id) {
        rapportScolaireRepository.deleteById(id);
    }

    private dto_rapport_scolaire convertirEnDTO(Rapport_scolaire rapport) {
        dto_rapport_scolaire dto = new dto_rapport_scolaire();
        dto.setId(rapport.getId());
        dto.setAnneeScolaire(rapport.getAnnee_scolaire());
        dto.setUrlBulletin(rapport.getUrlBulletin());
        dto.setUrlPhotoActivite(rapport.getUrlPhotoactivite());
        dto.setUrlPresence(rapport.getUrlPresence());
        dto.setDate(rapport.getDate());
        dto.setEnfantId(rapport.getEnfant().getId());
        return dto;
    }
}