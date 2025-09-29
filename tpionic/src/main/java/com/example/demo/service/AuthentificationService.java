package com.example.demo.service;

import com.example.demo.DTO.DtoAuthentihication;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.security.ServiceGenerationtoken;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

@Service


public class AuthentificationService {
    private final ServiceGenerationtoken serviceGenerationtoken;
    private final UtilisateurRepository utilisateurRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthentificationService(ServiceGenerationtoken serviceGenerationtoken, UtilisateurRepository utilisateurRepository, RefreshTokenRepository refreshTokenRepository) {
        this.serviceGenerationtoken = serviceGenerationtoken;
        this.refreshTokenRepository = refreshTokenRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public TokenResponse authentification(DtoAuthentihication dtoAuthentihication) {
        Utilisateur user = utilisateurRepository.findByEmail(dtoAuthentihication.email())
                .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe incorrecte"));
        if (BCrypt.checkpw(dtoAuthentihication.motdepasse(), user.getMotDePasse())) {
            String newAccessToken = serviceGenerationtoken.generationAccesToken(
                    user.getId(),
                    user.getRole()
            );
            String newRefreshToken = serviceGenerationtoken.generationRefreshToken(
                    user.getId(),
                    user.getRole()
            );

            return new TokenResponse(newAccessToken, newRefreshToken);
        }
        throw new BadCredentialsException("Email ou mot de passe incorrect.");
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        if (!serviceGenerationtoken.isValidRefreshToken(refreshToken)) {
            throw new JwtException("");
        }
        int userId = serviceGenerationtoken.getUserIdFromToken(refreshToken);
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new JwtException("Utilisateur introuvable."));

        String hashedToken = hashToken(refreshToken);
        refreshTokenRepository.findByUserIdAndToken(user.getId(), hashedToken)
                .orElseThrow(() -> new JwtException(""));

        String newAccessToken = serviceGenerationtoken.generationAccesToken(user.getId(), user.getRole());
        String newRefreshToken = serviceGenerationtoken.generationRefreshToken(user.getId(), user.getRole());
        storeRefreshToken(user.getId(), newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private void storeRefreshToken(int userId, String refreshToken) {
        String hashedToken = hashToken(refreshToken);
        long expiryMs = serviceGenerationtoken.getDateExpirationRT();
        Instant expiresAt = Instant.now().plusMillis(expiryMs);

        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new JwtException("Utilisateur introuvable."));

        RefreshToken tokenToStore = refreshTokenRepository.findByUserId(userId)
                .orElse(new RefreshToken(0, user, Instant.now(), null, ""));

        tokenToStore.setDateExpiration(expiresAt);
        tokenToStore.setToken(hashedToken);

        refreshTokenRepository.save(tokenToStore);
    }

    private String hashToken(String token) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hash du token.", e);
        }
    }

    public Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public record TokenResponse(String accessToken, String refreshToken) {
    }


}



