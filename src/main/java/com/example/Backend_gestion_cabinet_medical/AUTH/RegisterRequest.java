package com.example.Backend_gestion_cabinet_medical.AUTH;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String type; // "MEDECIN" ou "SECRETAIRE"
    private String specialite; // facultatif, requis uniquement si MEDECIN
}
