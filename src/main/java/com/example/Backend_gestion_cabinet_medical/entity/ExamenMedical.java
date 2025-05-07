package com.example.Backend_gestion_cabinet_medical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "examen_medical")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamenMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String lieu;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @JsonIgnore
    private Consultation consultation;
}