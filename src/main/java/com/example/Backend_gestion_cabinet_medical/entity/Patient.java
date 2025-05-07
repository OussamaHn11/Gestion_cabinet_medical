package com.example.Backend_gestion_cabinet_medical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")

    private String nom;
    @Column(name = "prenom")

    private String prenom;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "email")

    private String email;

    @Column(name = "datenaissance")
    private Date dateNaissance;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnore
    private Utilisateur utilisateur;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("patient-dossier")
    private DossierMedical dossierMedical;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("patient-rdv")
    private List<RendezVous> rendezVousList = new ArrayList<>();

    public void initializeEmptyMedicalRecord() {
        this.dossierMedical = new DossierMedical();
        this.dossierMedical.setPatient(this);
    }
}
