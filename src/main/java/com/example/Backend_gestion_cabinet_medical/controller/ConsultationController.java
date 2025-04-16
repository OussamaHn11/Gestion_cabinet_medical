package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.DTO.ConsultationRequest;
import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import com.example.Backend_gestion_cabinet_medical.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    //  Ajouter une consultation
    @PostMapping("/{dossierId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Consultation> ajouterConsultation(
            @PathVariable Long dossierId,
            @RequestBody ConsultationRequest request) {
        Consultation consultation = consultationService.ajouterConsultation(dossierId, request);
        return ResponseEntity.ok(consultation);
    }

    //  Modifier une consultation
    @PutMapping("/{consultationId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Consultation> modifierConsultation(
            @PathVariable Long consultationId,
            @RequestBody ConsultationRequest request) {
        Consultation consultation = consultationService.modifierConsultation(consultationId, request);
        return ResponseEntity.ok(consultation);
    }

    //  Supprimer une consultation
    @DeleteMapping("/{consultationId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Void> supprimerConsultation(@PathVariable Long consultationId) {
        consultationService.supprimerConsultation(consultationId);
        return ResponseEntity.noContent().build();
    }

    //  Récupérer une consultation par ID
    @GetMapping("/{consultationId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long consultationId) {
        Consultation consultation = consultationService.getConsultationById(consultationId);
        return ResponseEntity.ok(consultation);
    }

    //  Récupérer toutes les consultations d’un dossier médical
    @GetMapping("/dossier/{dossierId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<List<Consultation>> getConsultationsByDossier(@PathVariable Long dossierId) {
        List<Consultation> consultations = consultationService.getConsultationsByDossier(dossierId);
        return ResponseEntity.ok(consultations);
    }
}
