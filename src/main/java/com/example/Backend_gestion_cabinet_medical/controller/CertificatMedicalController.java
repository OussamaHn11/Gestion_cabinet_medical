package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.dto.CertificatMedicalRequest;
import com.example.Backend_gestion_cabinet_medical.entity.CertificatMedical;
import com.example.Backend_gestion_cabinet_medical.service.CertificatMedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/certificats")
@RequiredArgsConstructor
public class CertificatMedicalController {

    private final CertificatMedicalService certificatMedicalService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public ResponseEntity<CertificatMedical> ajouterCertificat(@RequestBody CertificatMedicalRequest request) {
        CertificatMedical savedCertificat = certificatMedicalService.ajouterCertificatMedical(request);
        return ResponseEntity.ok(savedCertificat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificatMedical> getCertificatById(@PathVariable Long id) {
        return certificatMedicalService.getCertificatMedicalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CertificatMedical> getAllCertificats() {
        return certificatMedicalService.getAllCertificats();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public ResponseEntity<Void> supprimerCertificat(@PathVariable Long id) {
        certificatMedicalService.supprimerCertificatMedical(id);
        return ResponseEntity.noContent().build();
    }
}
