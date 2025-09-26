package com.example.demo.service;

import com.example.demo.DTO.DtoAuthentihication;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.security.ServiceGenerationtoken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.BufferUnderflowException;

@Service


public class AuthentificationService {
    private final ServiceGenerationtoken serviceGenerationtoken;
    private final UtilisateurRepository utilisateurRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthentificationService(ServiceGenerationtoken serviceGenerationtoken,UtilisateurRepository utilisateurRepository,RefreshTokenRepository refreshTokenRepository){
        this.serviceGenerationtoken=serviceGenerationtoken;
        this.refreshTokenRepository=refreshTokenRepository;
        this.utilisateurRepository=utilisateurRepository;
    }

    public record  TokenResponse(String accessToken,String refreshToken){}

    public TokenResponse authentification(DtoAuthentihication dtoAuthentihication){
        Utilisateur user=utilisateurRepository.findByEmail(dtoAuthentihication.email())
                .orElseThrow(()-> new BadCredentialsException("Email ou mot de passe incorrecte"));
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


}
