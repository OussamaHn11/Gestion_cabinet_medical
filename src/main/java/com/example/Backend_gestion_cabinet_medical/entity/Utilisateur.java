package com.example.Backend_gestion_cabinet_medical.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString

@Entity
@AllArgsConstructor
@Table(name="utilisateur")
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String téléphone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String prénom ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;




    // Relation OneToMany avec Patient
    @OneToMany(mappedBy = "utilisateur")  // Une secrétaire peut gerer plusieurs patients
    @JsonManagedReference
    private List<Patient> patients;




}
