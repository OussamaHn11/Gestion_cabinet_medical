package com.example.Backend_gestion_cabinet_medical.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousRequest {
    private Long patientId;
    private LocalDateTime date;
    private String motif;
    // Ajoute d'autres champs si besoin
}
