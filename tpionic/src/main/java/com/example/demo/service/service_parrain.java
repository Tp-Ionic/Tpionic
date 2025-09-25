package com.example.demo.service;

import com.example.demo.model.Parrain;
import com.example.demo.DTO.dto_parrain;
import com.example.demo.repository.ParrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_parrain {

    private final ParrainRepository parrainRepository;

    public dto_parrain creerParrain(dto_parrain parrainDTO) {
        Parrain parrain = new Parrain();
        parrain.setNom(parrainDTO.getNom());
        parrain.setPrenom(parrainDTO.getPrenom());
        parrain.setEmail(parrainDTO.getEmail());
        parrain.setPassword(parrainDTO.getMotDePasse());
        parrain.setTelephone(parrainDTO.getTelephone());
        parrain.setPays(parrainDTO.getPays());
        parrain.setVille(parrainDTO.getVille());
        parrain.setAvatarUrl(parrainDTO.getAvatarUrl());
        parrain.setActif(true);

        Parrain saved = parrainRepository.save(parrain);
        return convertirEnDTO(saved);
    }

    public List<dto_parrain> listerParrains() {
        return parrainRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public dto_parrain getParrainById(int id) {
        Optional<Parrain> parrainOpt = parrainRepository.findById(id);
        if (parrainOpt.isPresent()) {
            return convertirEnDTO(parrainOpt.get());
        }
        throw new RuntimeException("Parrain non trouvé");
    }

    public dto_parrain updateParrain(int id, dto_parrain parrainDTO) {
        Optional<Parrain> parrainOpt = parrainRepository.findById(id);
        if (parrainOpt.isPresent()) {
            Parrain parrain = parrainOpt.get();
            parrain.setNom(parrainDTO.getNom());
            parrain.setPrenom(parrainDTO.getPrenom());
            parrain.setEmail(parrainDTO.getEmail());
            parrain.setPassword(parrainDTO.getMotDePasse());
            parrain.setTelephone(parrainDTO.getTelephone());
            parrain.setPays(parrainDTO.getPays());
            parrain.setVille(parrainDTO.getVille());
            parrain.setAvatarUrl(parrainDTO.getAvatarUrl());
            parrain.setActif(parrainDTO.getActif());

            Parrain updated = parrainRepository.save(parrain);
            return convertirEnDTO(updated);
        }
        throw new RuntimeException("Parrain non trouvé");
    }

    public void deleteParrain(int id) {
        parrainRepository.deleteById(id);
    }

    private dto_parrain convertirEnDTO(Parrain parrain) {
        dto_parrain dto = new dto_parrain();
        dto.setId(parrain.getId());
        dto.setNom(parrain.getNom());
        dto.setPrenom(parrain.getPrenom());
        dto.setEmail(parrain.getEmail());
        dto.setMotDePasse(parrain.getPassword());
        dto.setTelephone(parrain.getTelephone());
        dto.setPays(parrain.getPays());
        dto.setVille(parrain.getVille());
        dto.setAvatarUrl(parrain.getAvatarUrl());
        dto.setActif(parrain.getActif());
        return dto;
    }
}