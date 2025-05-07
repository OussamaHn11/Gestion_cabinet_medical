package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.dto.PatientRequest;
import com.example.Backend_gestion_cabinet_medical.entity.Patient;
import com.example.Backend_gestion_cabinet_medical.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    // Ajouter un patient (accessible au rôle SECRETAIRE)
    @PostMapping
    public ResponseEntity<?> ajouterPatient(@RequestBody PatientRequest request, Authentication authentication) {
        Patient patient = patientService.ajouterPatient(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Patient ajouté avec succès", "patient", patient));
    }

    // Lister tous les patients (MEDECIN ou SECRETAIRE)
    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'SECRETAIRE')")
    public ResponseEntity<List<Patient>> listerPatients(Authentication authentication) {
        List<Patient> patients = patientService.listerPatients(authentication);
        return ResponseEntity.ok(patients);
    }

    // Récupérer un patient par ID (MEDECIN ou SECRETAIRE)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'SECRETAIRE')")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.obtenirPatientParId(id);
        return ResponseEntity.ok(patient);
    }

    // Modifier un patient (SECRETAIRE uniquement)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<?> modifierPatient(@PathVariable Long id,
                                             @RequestBody PatientRequest request,
                                             Authentication authentication) {
        Patient patient = patientService.modifierPatient(id, request, authentication);
        return ResponseEntity.ok(Map.of("message", "Patient modifié avec succès", "patient", patient));
    }

    // Supprimer un patient (SECRETAIRE uniquement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<?> supprimerPatient(@PathVariable Long id) {
        patientService.supprimerPatient(id);
        return ResponseEntity.noContent().build();
    }
}
