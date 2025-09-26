package com.example.demo.security;

import com.example.demo.Enumeration.Role;
import com.example.demo.Enumeration.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceGenerationtoken {
    private static final int dateExpirationAT= 24*60*60*1000;
    private static final int dateExpirationRT=30*24*60*60*1000;
    @Value("${keysecret}")
    private String keysecret;
    private SecretKey secretKey;
    @PostConstruct
    void init(){
        this.secretKey= Keys.hmacShaKeyFor(Base64.getDecoder().decode(keysecret.getBytes(StandardCharsets.UTF_8)));
    }
    private String generationtoken(int id , TypeToken typeToken, Role role, int expat){
        Date now=Date.from(Instant.now());
        Date ExpirationDate= new Date(now.getTime()+expat);

        return Jwts
                .builder()
                .subject(String.valueOf(id))
                .claim("type",typeToken.name())
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(ExpirationDate)
                .signWith(secretKey,Jwts.SIG.HS256)
                .compact();
    }

    private Claims parseAllClaims(String token) {
        String rawToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .getPayload();
    }
    public String generationAccesToken(int id,Role roles){
        return generationtoken(id, TypeToken.ACCESS_TOKEN, roles,dateExpirationAT);
    }


    public String generationRefreshToken(int id,Role role){
        return generationtoken(id,TypeToken.REFRESH_TOKEN, role, dateExpirationRT);
    }

    public boolean isValidAccessToken(String accessToken) {
        Claims claims = parseAllClaims(accessToken);
        if (claims == null) return false;
        String type = (String) claims.get("type");
        return TypeToken.ACCESS_TOKEN.name().equals(type);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        Claims claims = parseAllClaims(refreshToken);
        if (claims == null) return false;
        String type = (String) claims.get("type");
        return TypeToken.REFRESH_TOKEN.name().equals(type);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = parseAllClaims(token);
        if (claims == null) throw new IllegalArgumentException("Token invalide.");
        return Long.parseLong(claims.getSubject());
    }

    public Set<Role> getUserRolesFromToken(String token) {
        Claims claims = parseAllClaims(token);
        if (claims == null) throw new IllegalArgumentException("Token invalide");
        List<?> roles = (List<?>) claims.get("roles");
        return roles.stream()
                .map(Object::toString)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }



}
