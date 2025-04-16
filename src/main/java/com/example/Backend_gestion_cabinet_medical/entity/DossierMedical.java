package com.example.Backend_gestion_cabinet_medical.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name="dossierMedical")
@NoArgsConstructor
public class DossierMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String antecedents;
    private String allergies;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference // Pour inclure cette propriété dans la réponse JSON
    private Patient patient;

    @OneToMany(mappedBy = "dossierMedical", cascade = CascadeType.ALL)
    private List<Consultation> consultations=new ArrayList<>();




}