package com.example.Backend_gestion_cabinet_medical.DTO;

import lombok.Data;


import java.time.LocalDateTime;



@Data
public class PatientRequest {
    private String nom;
    private String prénom;
    private String telephone;
    private String email;
    private LocalDateTime date_nessance;
}