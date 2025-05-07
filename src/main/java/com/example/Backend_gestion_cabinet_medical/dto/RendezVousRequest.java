package com.example.Backend_gestion_cabinet_medical.dto;

import com.example.Backend_gestion_cabinet_medical.entity.StatutRendezVous;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousRequest {
    private Long patientId;
    private LocalDateTime date;
    private String motif;
    private StatutRendezVous statut;

}
