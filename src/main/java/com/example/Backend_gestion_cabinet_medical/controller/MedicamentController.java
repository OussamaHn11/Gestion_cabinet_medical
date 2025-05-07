package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.dto.MedicamentDTO;
import com.example.Backend_gestion_cabinet_medical.entity.Medicament;
import com.example.Backend_gestion_cabinet_medical.entity.Ordonnance;
import com.example.Backend_gestion_cabinet_medical.repository.MedicamentRepository;
import com.example.Backend_gestion_cabinet_medical.repository.OrdonnanceRepository;
import com.example.Backend_gestion_cabinet_medical.service.MedicamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicaments")
@RequiredArgsConstructor
public class MedicamentController {
@Autowired
    private final MedicamentService medicamentService;

@Autowired
private  final OrdonnanceRepository ordonnanceRepository;

@Autowired
private  final MedicamentRepository medicamentRepository;
    @PostMapping("/{ordonnanceId}")
    public ResponseEntity<Medicament> ajouter(@PathVariable Long ordonnanceId, @RequestBody MedicamentDTO dto) {
        Ordonnance ordonnance = ordonnanceRepository.findById(ordonnanceId)
                .orElseThrow(() -> new RuntimeException("Ordonnance introuvable"));

        Medicament medicament = new Medicament();
        medicament.setNom(dto.getNom());
        medicament.setFrequence(dto.getFrequence());
        medicament.setDose(dto.getDose());
        medicament.setOrdonnance(ordonnance);

        Medicament saved = medicamentRepository.save(medicament);
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public List<Medicament> getAll() {
        return medicamentService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        medicamentService.supprimerMedicament(id);
        return ResponseEntity.noContent().build();
    }
}
