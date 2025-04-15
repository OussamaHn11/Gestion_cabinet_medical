package com.example.Backend_gestion_cabinet_medical.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "rappel")
public class Rappel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime sendDate;
    private boolean sent;

    // Relation avec Rendez-vous
    @ManyToOne
    @JoinColumn(name = "rendez_vous_id", nullable = false)
    private RendezVous rendezVous;
}