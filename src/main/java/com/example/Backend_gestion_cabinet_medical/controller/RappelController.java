package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.entity.Rappel;
import com.example.Backend_gestion_cabinet_medical.repository.RappelRepository;
import com.example.Backend_gestion_cabinet_medical.service.Rappel.RappelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rappels")
@RequiredArgsConstructor
public class RappelController {
    private final RappelService rappelService;

    private final RappelRepository rappelRepository;

    @GetMapping
    public List<Rappel> getAllRappels() {
        return rappelRepository.findAll();
    }
    @GetMapping("/{id}")
    public Rappel getRappelById(@PathVariable Long id) {
        return rappelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rappel non trouvé"));
    }

    @PostMapping("/envoyer")
    public ResponseEntity<String> envoyerRappels() {
        rappelService.envoyerRappelsAutomatiques();
        return ResponseEntity.ok("✅ Rappels envoyés avec succès !");
    }}