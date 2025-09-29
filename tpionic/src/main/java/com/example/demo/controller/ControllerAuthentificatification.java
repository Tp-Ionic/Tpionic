package com.example.demo.controller;

import com.example.demo.DTO.DtoAuthentihication;
import com.example.demo.service.AuthentificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentification")
public class ControllerAuthentificatification {
    private final AuthentificationService authentificationService;

    public ControllerAuthentificatification(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthentificationService.TokenResponse> login(
            @RequestBody DtoAuthentihication dtoAuthentihication) {
        return ResponseEntity.ok(
                authentificationService.authentification(dtoAuthentihication)
        );
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthentificationService.TokenResponse> refreshToken(@RequestBody String refresh) {
        return ResponseEntity.ok(authentificationService.refresh(refresh));
    }
}
