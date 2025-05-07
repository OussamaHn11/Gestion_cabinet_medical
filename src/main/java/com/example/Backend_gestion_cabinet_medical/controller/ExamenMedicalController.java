package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.entity.ExamenMedical;
import com.example.Backend_gestion_cabinet_medical.service.ExamenMedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examens")
@RequiredArgsConstructor
public class ExamenMedicalController {

    private final ExamenMedicalService examenMedicalService;

    // ✅ Ajouter un examen médical à une consultation
    @PostMapping("/{consultationId}")
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public ResponseEntity<ExamenMedical> ajouterExamen(
            @PathVariable Long consultationId,
            @RequestBody ExamenMedical examen) {
        ExamenMedical saved = examenMedicalService.ajouterExamen(consultationId, examen);
        return ResponseEntity.ok(saved);
    }

    // ✅ Récupérer la liste de tous les examens médicaux
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public ResponseEntity<List<ExamenMedical>> getAllExamens() {
        List<ExamenMedical> examens = examenMedicalService.getAll();
        return ResponseEntity.ok(examens);
    }

    // ✅ Supprimer un examen médical par ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public ResponseEntity<Void> supprimerExamen(@PathVariable Long id) {
        examenMedicalService.supprimerExamen(id);
        return ResponseEntity.noContent().build();
    }
}
