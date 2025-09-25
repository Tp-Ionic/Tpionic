package com.example.demo.service;

import com.example.demo.model.Parrainage;
import com.example.demo.model.Parrain;
import com.example.demo.model.Enfant;
import com.example.demo.DTO.dto_parrainage;
import com.example.demo.repository.ParrainageRepository;
import com.example.demo.repository.ParrainRepository;
import com.example.demo.repository.EnfantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_parrainage {

    private final ParrainageRepository parrainageRepository;
    private final ParrainRepository parrainRepository;
    private final EnfantRepository enfantRepository;

    public dto_parrainage creerParrainage(dto_parrainage parrainageDTO) {
        Optional<Parrain> parrainOpt = parrainRepository.findById(parrainageDTO.getParrainId());
        Optional<Enfant> enfantOpt = enfantRepository.findById(parrainageDTO.getEnfantId());

        if (parrainOpt.isPresent() && enfantOpt.isPresent()) {
            Parrainage parrainage = new Parrainage();
            parrainage.setParrain(parrainOpt.get());
            parrainage.setEnfant(enfantOpt.get());
            parrainage.setDateDebut(parrainageDTO.getDateDebut());
            parrainage.setMontant_contribue(parrainageDTO.getMontantContribue());
            parrainage.setActif(parrainageDTO.isActif());

            Parrainage saved = parrainageRepository.save(parrainage);
            return convertirEnDTO(saved);
        }
        throw new RuntimeException("Parrain ou enfant non trouvé");
    }

    public List<dto_parrainage> listerParrainages() {
        return parrainageRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public List<dto_parrainage> listerParrainagesParEnfant(int enfantId) {
        return parrainageRepository.findByEnfantId(enfantId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public List<dto_parrainage> listerParrainagesParParrain(int parrainId) {
        return parrainageRepository.findByParrainId(parrainId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public dto_parrainage getParrainageById(int id) {
        Optional<Parrainage> parrainageOpt = parrainageRepository.findById(id);
        if (parrainageOpt.isPresent()) {
            return convertirEnDTO(parrainageOpt.get());
        }
        throw new RuntimeException("Parrainage non trouvé");
    }

    public dto_parrainage updateParrainage(int id, dto_parrainage parrainageDTO) {
        Optional<Parrainage> parrainageOpt = parrainageRepository.findById(id);
        if (parrainageOpt.isPresent()) {
            Parrainage parrainage = parrainageOpt.get();
            parrainage.setMontant_contribue(parrainageDTO.getMontantContribue());
            parrainage.setDateDebut(parrainageDTO.getDateDebut());
            parrainage.setActif(parrainageDTO.isActif());

            Parrainage updated = parrainageRepository.save(parrainage);
            return convertirEnDTO(updated);
        }
        throw new RuntimeException("Parrainage non trouvé");
    }

    public void deleteParrainage(int id) {
        parrainageRepository.deleteById(id);
    }

    private dto_parrainage convertirEnDTO(Parrainage parrainage) {
        dto_parrainage dto = new dto_parrainage();
        dto.setId(parrainage.getId());
        dto.setParrainId(parrainage.getParrain().getId());
        dto.setEnfantId(parrainage.getEnfant().getId());
        dto.setDateDebut(parrainage.getDateDebut());
        dto.setMontantContribue(parrainage.getMontant_contribue());
        dto.setActif(parrainage.isActif());
        return dto;
    }
}