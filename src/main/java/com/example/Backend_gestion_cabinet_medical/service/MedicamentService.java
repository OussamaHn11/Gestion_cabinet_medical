package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.entity.Medicament;
import com.example.Backend_gestion_cabinet_medical.entity.Ordonnance;
import com.example.Backend_gestion_cabinet_medical.repository.MedicamentRepository;
import com.example.Backend_gestion_cabinet_medical.repository.OrdonnanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentService {

    private final MedicamentRepository medicamentRepository;
    private final OrdonnanceRepository ordonnanceRepository;

    public Medicament ajouterMedicament(Long ordonnanceId, Medicament medicament) {
        Ordonnance ordonnance = ordonnanceRepository.findById(ordonnanceId)
                .orElseThrow(() -> new RuntimeException("Ordonnance introuvable"));

        medicament.setOrdonnance(ordonnance);
        System.out.println("➡️ Ajout du médicament : " + medicament);

        Medicament saved = medicamentRepository.save(medicament);
        System.out.println("✅ Médicament inséré : ID=" + saved.getId());

        return saved;
    }



    public List<Medicament> getAll() {
        return medicamentRepository.findAll();
    }

    public void supprimerMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }
}
