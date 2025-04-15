package com.example.Backend_gestion_cabinet_medical.controller;

import com.example.Backend_gestion_cabinet_medical.entity.RendezVous;
import com.example.Backend_gestion_cabinet_medical.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rendezvous")
@RequiredArgsConstructor
public class RendezVousController {

    private final RendezVousService rendezVousService;

    @GetMapping
    public List<RendezVous> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long id) {
        Optional<RendezVous> rdv = rendezVousService.getRendezVousById(id);
        return rdv.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RendezVous> createRendezVous(@RequestBody RendezVous rendezVous) {
        RendezVous createdRdv = rendezVousService.saveRendezVous(rendezVous);
        return ResponseEntity.ok(createdRdv);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable Long id, @RequestBody RendezVous updatedRdv) {
        try {
            RendezVous rdv = rendezVousService.updateRendezVous(id, updatedRdv);
            return ResponseEntity.ok(rdv);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }
}
