package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public record DtoAuthentihication (
    @NotBlank(message="Email invalide")String email,
    @NotBlank(message ="mot de passe invalide")String motdepasse){
}
