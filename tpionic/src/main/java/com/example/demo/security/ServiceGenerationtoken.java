package com.example.demo.security;

import com.example.demo.Enumeration.Role;
import com.example.demo.Enumeration.TypeToken;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceGenerationtoken {
    private static final int dateExpiration= 24*60*60*1000;
    @Value("${keysecret}")
    private String keysecret;
    private SecretKey secretKey;
    @PostConstruct
    void init(){
        this.secretKey= Keys.hmacShaKeyFor(Base64.getDecoder().decode(keysecret.getBytes(StandardCharsets.UTF_8)));
    }
    private String generationtoken(int id , TypeToken typeToken, Set<Role>role, int expat){
        Date now=Date.from(Instant.now());
        Date ExpirationDate= new Date(now.getTime()+expat);

        return Jwts
                .builder()
                .subject(String.valueOf(id))
                .claim("type",typeToken.name())
                .claim("roles",role.stream().map(Role::name).collect(Collectors.toList()))
                .issuedAt(now)
                .expiration(ExpirationDate)
                .signWith(secretKey,Jwts.SIG.HS256)
                .compact();
    }

}
