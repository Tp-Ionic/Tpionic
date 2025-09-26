package com.example.demo.service;

import com.example.demo.DTO.dto_enfant;
import com.example.demo.model.Association;
import com.example.demo.model.Enfant;
import com.example.demo.repository.AssociationRepository;
import com.example.demo.repository.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileUploadService {

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public String uploadFile(MultipartFile file, Long enfantId, String typeFichier) throws IOException {
        // Vérifier que l'enfant existe
        Enfant enfant = enfantRepository.findById(enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé avec l'ID: " + enfantId));

        // Créer le dossier de destination s'il n'existe pas
        Path uploadPath = Paths.get(uploadDir, "enfants", enfantId.toString(), typeFichier);
        Files.createDirectories(uploadPath);

        // Générer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;

        // Sauvegarder le fichier
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        // Construire l'URL du fichier
        String fileUrl = "/uploads/enfants/" + enfantId + "/" + typeFichier + "/" + filename;

        // Mettre à jour l'enfant avec la nouvelle URL
        updateEnfantFileUrls(enfant, fileUrl, typeFichier);

        return fileUrl;
    }

    private void updateEnfantFileUrls(Enfant enfant, String fileUrl, String typeFichier) {
        switch (typeFichier) {
            case "bulletin":
                String bulletins = enfant.getBulletinsPdfUrls();
                enfant.setBulletinsPdfUrls(bulletins == null ? fileUrl : bulletins + "," + fileUrl);
                break;
            case "photo":
                String photos = enfant.getPhotosActivitesUrls();
                enfant.setPhotosActivitesUrls(photos == null ? fileUrl : photos + "," + fileUrl);
                break;
            case "presence":
                String presences = enfant.getListesPresenceUrls();
                enfant.setListesPresenceUrls(presences == null ? fileUrl : presences + "," + fileUrl);
                break;
        }
        enfantRepository.save(enfant);
    }

    public byte[] downloadFile(String filePath) throws IOException {
        Path path = Paths.get(uploadDir, filePath);
        return Files.readAllBytes(path);
    }

    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(uploadDir, filePath);
        Files.deleteIfExists(path);
    }
}
