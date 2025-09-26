package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DtoRefreshToken {
    private Instant dateCreation;
    private Instant dateExpiration;
    private String token;


}
