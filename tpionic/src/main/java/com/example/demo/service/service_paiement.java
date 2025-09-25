package com.example.demo.service;

import com.example.demo.model.Paiement;
import com.example.demo.model.Enfant;
import com.example.demo.DTO.dto_paiement;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.EnfantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class service_paiement {

    private final PaiementRepository paiementRepository;
    private final EnfantRepository enfantRepository;

    public dto_paiement creerPaiement(dto_paiement paiementDTO) {
        Optional<Enfant> enfantOpt = enfantRepository.findById(paiementDTO.getEnfantId());
        if (enfantOpt.isPresent()) {
            Paiement paiement = new Paiement();
            paiement.setLibelle(paiementDTO.getLibelle());
            paiement.setTypePaiement(paiementDTO.getTypePaiement());
            paiement.setDatePaiement(paiementDTO.getDatePaiement());
            paiement.setMontant(paiementDTO.getMontant());
            paiement.setDateConfirmation(paiementDTO.getDateConfirmation());
            paiement.setConfirme(paiementDTO.isConfirme());
            paiement.setEnfant(enfantOpt.get());

            Paiement saved = paiementRepository.save(paiement);
            return convertirEnDTO(saved);
        }
        throw new RuntimeException("Enfant non trouvé");
    }

    public List<dto_paiement> listerPaiements() {
        return paiementRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public List<dto_paiement> listerPaiementsParEnfant(int enfantId) {
        return paiementRepository.findByEnfantId(enfantId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    public dto_paiement confirmerPaiement(int id) {
        Optional<Paiement> paiementOpt = paiementRepository.findById(id);
        if (paiementOpt.isPresent()) {
            Paiement paiement = paiementOpt.get();
            paiement.setConfirme(true);
            paiement.setDateConfirmation(LocalDate.now());

            Paiement updated = paiementRepository.save(paiement);
            return convertirEnDTO(updated);
        }
        throw new RuntimeException("Paiement non trouvé");
    }

    public dto_paiement getPaiementById(int id) {
        Optional<Paiement> paiementOpt = paiementRepository.findById(id);
        if (paiementOpt.isPresent()) {
            return convertirEnDTO(paiementOpt.get());
        }
        throw new RuntimeException("Paiement non trouvé");
    }

    public void deletePaiement(int id) {
        paiementRepository.deleteById(id);
    }

    private dto_paiement convertirEnDTO(Paiement paiement) {
        dto_paiement dto = new dto_paiement();
        dto.setId(paiement.getId());
        dto.setLibelle(paiement.getLibelle());
        dto.setTypePaiement(paiement.getTypePaiement());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setMontant(paiement.getMontant());
        dto.setDateConfirmation(paiement.getDateConfirmation());
        dto.setConfirme(paiement.isConfirme());
        dto.setEnfantId(paiement.getEnfant().getId());
        return dto;
    }
}