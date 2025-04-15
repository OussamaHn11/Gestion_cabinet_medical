package com.example.Backend_gestion_cabinet_medical.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="ordonnance")
public class Ordonnance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String médicament;
    @Column(nullable = false)
    private String fréquence;
    @Column(nullable = false)
    private String duré;
}
