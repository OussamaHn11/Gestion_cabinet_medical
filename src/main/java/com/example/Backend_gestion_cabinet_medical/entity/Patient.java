package com.example.Backend_gestion_cabinet_medical.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@Table(name="patient")

@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;


    @Column(nullable = false)
    private String prénom;
    @Column(nullable = false)
    private String telephone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDateTime date_nessance;




    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = true)
    @JsonIgnore
    private Utilisateur utilisateur;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference// Pour éviter les boucles infinies
    private DossierMedical dossierMedical;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RendezVous> rendezVousList = new ArrayList<>();

    // Méthode pour initialiser un dossier médical vide
    public void initializeEmptyMedicalRecord() {
        this.dossierMedical = new DossierMedical();
        this.dossierMedical.setPatient(this); // Liaison bidirectionnelle
    }




}