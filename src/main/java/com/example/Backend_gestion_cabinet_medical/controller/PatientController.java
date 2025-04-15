package com.example.Backend_gestion_cabinet_medical.controller;


import com.example.Backend_gestion_cabinet_medical.DTO.PatientRequest;
import com.example.Backend_gestion_cabinet_medical.entity.DossierMedical;
import com.example.Backend_gestion_cabinet_medical.entity.Patient;
import com.example.Backend_gestion_cabinet_medical.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    // Ajouter un patient
    @PostMapping
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<?> ajouterPatient(@RequestBody PatientRequest request, Authentication authentication) {
        Patient patient = patientService.ajouterPatient(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Patient ajouté avec succès"));
    }

    // Lister les patients
    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'SECRETAIRE')")
    public ResponseEntity<List<Patient>> listerPatients(Authentication authentication) {
        return ResponseEntity.ok(patientService.listerPatients(authentication));
    }
    // Lister un patient
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'SECRETAIRE')")
    public ResponseEntity<List<Patient>> getPatientById(Authentication authentication) {
        return ResponseEntity.ok(patientService.listerPatients(authentication));
    }



    // Modifier un patient
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<?> modifierPatient(@PathVariable Long id, @RequestBody PatientRequest request, Authentication authentication) {
        Patient patient = patientService.modifierPatient(id, request, authentication);
        return ResponseEntity.ok(Map.of("message", "Patient modifié avec succès"));
    }

    // Supprimer un patient
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<?> supprimerPatient(@PathVariable Long id) {
        patientService.supprimerPatient(id);
        return ResponseEntity.noContent().build();
    }


}



