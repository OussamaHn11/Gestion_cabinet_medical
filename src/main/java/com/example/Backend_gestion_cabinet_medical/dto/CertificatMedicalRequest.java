package com.example.Backend_gestion_cabinet_medical.dto;

import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificatMedicalRequest {
    private String type;
    private Integer duree;
    private LocalDateTime dateDebut;
    private Long consultationId;

}