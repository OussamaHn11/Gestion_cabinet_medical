package com.example.Backend_gestion_cabinet_medical.dto;


import com.example.Backend_gestion_cabinet_medical.entity.Medicament;
import lombok.Data;

import java.util.List;

@Data
public class OrdonnanceRequest {
    private Long id;
    private String medicament;
    private String frequence;
    private String duree;
    private List<Medicament> medicaments;

}
