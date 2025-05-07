package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.dto.PatientRequest;
import com.example.Backend_gestion_cabinet_medical.entity.DossierMedical;
import com.example.Backend_gestion_cabinet_medical.entity.Patient;
import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.PatientRepository;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Ajouter un patient
    public Patient ajouterPatient(PatientRequest request, Authentication authentication) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Patient patient = new Patient();
        patient.setNom(request.getNom());
        patient.setPrenom(request.getPrenom()); // correction ici
        patient.setTelephone(request.getTelephone());
        patient.setEmail(request.getEmail());
        patient.setDateNaissance(request.getDateNaissance()); // correction ici
        patient.setUtilisateur(utilisateur);

        // Créer un dossier médical vide
        DossierMedical dossier = new DossierMedical();
        dossier.setPatient(patient);
        dossier.setAntecedents("");
        dossier.setAllergies("");
        patient.setDossierMedical(dossier);

        return patientRepository.save(patient);
    }

    // Lister tous les patients
    public List<Patient> listerPatients(Authentication authentication) {
        return patientRepository.findAll(); // Optionnel : filtrer par utilisateur
    }

    // Obtenir un patient par ID
    public Patient obtenirPatientParId(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
    }

    // Modifier un patient
    public Patient modifierPatient(Long id, PatientRequest request, Authentication authentication) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        patient.setNom(request.getNom());
        patient.setPrenom(request.getPrenom()); // correction ici
        patient.setTelephone(request.getTelephone());
        patient.setEmail(request.getEmail());
        patient.setDateNaissance(request.getDateNaissance()); // correction ici

        return patientRepository.save(patient);
    }

    // Supprimer un patient
    public void supprimerPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
        patientRepository.delete(patient);
    }
}
