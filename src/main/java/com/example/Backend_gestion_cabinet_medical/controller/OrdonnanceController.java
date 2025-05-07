package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.dto.OrdonnanceRequest;
import com.example.Backend_gestion_cabinet_medical.entity.Ordonnance;
import com.example.Backend_gestion_cabinet_medical.service.OrdonnanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordonnances")
@RequiredArgsConstructor
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    // Endpoint pour ajouter une ordonnance à une consultation
    @PostMapping("/{consultationId}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Ordonnance> ajouterOrdonnance(@RequestBody OrdonnanceRequest request,
                                                        @PathVariable Long consultationId) {
        Ordonnance ordonnance = ordonnanceService.ajouterOrdonnance(request, consultationId);
        return ResponseEntity.ok(ordonnance);
    }

    // Endpoint pour récupérer une ordonnance par son ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Ordonnance> getOrdonnanceById(@PathVariable Long id) {
        Optional<Ordonnance> ordonnance = ordonnanceService.getOrdonnanceById(id);
        return ordonnance.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour récupérer toutes les ordonnances
    @GetMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceService.getAllOrdonnances();
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Void> supprimerOrdonnance(@PathVariable Long id) {
        ordonnanceService.supprimerOrdonnance(id);
        return ResponseEntity.noContent().build();
    }
}
