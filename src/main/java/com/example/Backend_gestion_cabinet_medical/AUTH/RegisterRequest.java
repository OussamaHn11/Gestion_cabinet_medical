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

    private String nom;
    private String username;
    private String téléphone;
    private String email;
    private String password;
    private String prénom ;
    private String type; }
