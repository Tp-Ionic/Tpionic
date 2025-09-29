package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "notifications_parrains")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationParrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String message;

    private String statut; // ENVOYE, LU, REPONDU
    private LocalDate dateEnvoi;
    private String typeNotification; // CONFIRMATION_DEPENSE, REMERCIEMENT, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parrain_id", nullable = false)
    private Parrain parrain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id", nullable = false)
    private Enfant enfant;
}
