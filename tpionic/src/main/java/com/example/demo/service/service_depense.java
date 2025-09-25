package com.example.demo.service;

import com.example.demo.model.Depense;
import com.example.demo.DTO.dto_depense;
import com.example.demo.repository.DepenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_depense {

    private final DepenseRepository depenseRepository;

    public dto_depense creerDepense(dto_depense depenseDTO) {
        Depense depense = new Depense();
        depense.setLibelle(depenseDTO.getLibelle());
        depense.setMontant(depenseDTO.getMontant());
        depense.setCategorie(depenseDTO.getCategorie());
        depense.setDateDepense(depenseDTO.getDateDepense());

        Depense saved = depenseRepository.save(depense);
        return convertirEnDTO(saved);
    }

    public List<dto_depense> listerDepenses() {
        return depenseRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public List<dto_depense> listerDepensesParCategorie(String categorie) {
        return depenseRepository.findByCategorie(categorie).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public dto_depense getDepenseById(int id) {
        return depenseRepository.findById(id)
                .map(this::convertirEnDTO)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));
    }

    public dto_depense updateDepense(int id, dto_depense depenseDTO) {
        return depenseRepository.findById(id)
                .map(depense -> {
                    depense.setLibelle(depenseDTO.getLibelle());
                    depense.setMontant(depenseDTO.getMontant());
                    depense.setCategorie(depenseDTO.getCategorie());
                    depense.setDateDepense(depenseDTO.getDateDepense());
                    return convertirEnDTO(depenseRepository.save(depense));
                })
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));
    }

    public void deleteDepense(int id) {
        depenseRepository.deleteById(id);
    }

    private dto_depense convertirEnDTO(Depense depense) {
        dto_depense dto = new dto_depense();
        dto.setId(depense.getId());
        dto.setLibelle(depense.getLibelle());
        dto.setMontant(depense.getMontant());
        dto.setCategorie(depense.getCategorie());
        dto.setDateDepense(depense.getDateDepense());
        return dto;
    }
}