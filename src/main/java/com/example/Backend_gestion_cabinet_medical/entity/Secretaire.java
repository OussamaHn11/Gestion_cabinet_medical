package com.example.Backend_gestion_cabinet_medical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "secretaire")
public class Secretaire extends Utilisateur {
    // Tu peux ajouter des champs spécifiques si nécessaire
}
