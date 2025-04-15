package com.example.Backend_gestion_cabinet_medical.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name="consultation")
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnostique;
    @Column(nullable = false)
    private LocalDateTime date ;


    // Relation ManyToOne avec Utilisateur (Médecin)
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur medecin;

    // Relation OneToOne avec Ordonnance
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultation")
    private Ordonnance ordonnance;

    // Relation OneToOne avec Certificat médical
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultation")
    private CertificatMedical certificatMedical;
}
