package com.example.Backend_gestion_cabinet_medical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "consultation")
@NoArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnostique;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnore
    private Utilisateur medecin;

    @ManyToOne
    @JoinColumn(name = "dossier_medical_id", nullable = false)
    @JsonIgnore
    private DossierMedical dossierMedical;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultation")
    @JsonIgnore
    private Ordonnance ordonnance;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultation")
    @JsonIgnore
    private CertificatMedical certificatMedical;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ExamenMedical> examens;
}