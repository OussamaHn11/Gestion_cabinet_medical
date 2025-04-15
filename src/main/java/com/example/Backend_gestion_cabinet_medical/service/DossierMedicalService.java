package com.example.Backend_gestion_cabinet_medical.service;


import com.example.Backend_gestion_cabinet_medical.entity.DossierMedical;
import com.example.Backend_gestion_cabinet_medical.entity.Patient;
import com.example.Backend_gestion_cabinet_medical.repository.DossierMedicalRepository;
import com.example.Backend_gestion_cabinet_medical.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor

public class DossierMedicalService {

    private final DossierMedicalRepository dossierMedicalRepository;
    private final PatientRepository patientRepository;

    // Consulter le dossier médical d'un patient
    public DossierMedical consulterDossierMedical(Long patientId) {
        return dossierMedicalRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Dossier médical non trouvé"));
    }


    // Modifier le dossier médical d'un patient
    public DossierMedical modifierDossierMedical(Long patientId, DossierMedical dossierMedicalUpdated, Authentication authentication) {
        // Vérifier si le patient existe
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        // Récupérer le dossier médical associé
        DossierMedical dossierMedical = dossierMedicalRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Dossier médical non trouvé"));

        // Mettre à jour les informations du dossier médical
        dossierMedical.setAntecedents(dossierMedicalUpdated.getAntecedents());
        dossierMedical.setAllergies(dossierMedicalUpdated.getAllergies());


        return dossierMedicalRepository.save(dossierMedical);
    }
}
