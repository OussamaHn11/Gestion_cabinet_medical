package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.entity.DossierMedical;
import com.example.Backend_gestion_cabinet_medical.service.DossierMedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dossier-medical")
public class DossierMedicalController {
    private final DossierMedicalService dossierMedicalService;
    // Consulter le dossier médical d'un patient
    @GetMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'SECRETAIRE')")
    public ResponseEntity<DossierMedical> consulterDossierMedical(@PathVariable Long patientId) {
        DossierMedical dossier = dossierMedicalService.consulterDossierMedical(patientId);
        return ResponseEntity.ok(dossier);
    }

    // Modifier le dossier médical d'un patient
    @PutMapping("/{patientId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<?> modifierDossierMedical(@PathVariable Long patientId,
                                                    @RequestBody DossierMedical dossierMedicalUpdated,
                                                    Authentication authentication) {
        DossierMedical updatedDossier = dossierMedicalService.modifierDossierMedical(patientId, dossierMedicalUpdated, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDossier);
    }
}

