package com.example.Backend_gestion_cabinet_medical.dto;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class PatientRequest {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private Date dateNaissance;  // ✅ corriger ici
}
