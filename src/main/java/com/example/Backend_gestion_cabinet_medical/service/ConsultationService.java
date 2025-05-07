package com.example.Backend_gestion_cabinet_medical.service;
import com.example.Backend_gestion_cabinet_medical.dto.ConsultationRequest;
import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import com.example.Backend_gestion_cabinet_medical.entity.DossierMedical;
import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.ConsultationRepository;
import com.example.Backend_gestion_cabinet_medical.repository.DossierMedicalRepository;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final DossierMedicalRepository dossierMedicalRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Ajouter une consultation
    public Consultation ajouterConsultation(Long dossierId, ConsultationRequest request) {
        // Récupérer le médecin authentifié
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur medecin = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupérer le dossier médical du patient
        DossierMedical dossier = dossierMedicalRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier médical non trouvé"));

        Consultation consultation = new Consultation();
        consultation.setDossierMedical(dossier);// Le dossier médical est lié
        consultation.setDate(LocalDateTime.now());  // La date de consultation est la date actuelle
        consultation.setDiagnostique(request.getDiagnostique());
        consultation.setMedecin(medecin);

        // Sauvegarder la consultation dans la base de données
        return consultationRepository.save(consultation);
    }

    // Modifier une consultation
    public Consultation modifierConsultation(Long id, ConsultationRequest request) {
        // Vérifier si la consultation existe
        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));

        // Mettre à jour les informations de la consultation
        existingConsultation.setDiagnostique(request.getDiagnostique());
        existingConsultation.setDate(request.getDate()); // Tu peux mettre à jour la date si nécessaire

        // Sauvegarder les modifications
        return consultationRepository.save(existingConsultation);
    }

    // Supprimer une consultation
    public void supprimerConsultation(Long id) {
        // Vérifier si la consultation existe
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));

        consultationRepository.delete(consultation);
    }
    // Obtenir une consultation par ID
    public Consultation getConsultationById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));
    }

    // Lister les consultations d’un dossier médical
    public List<Consultation> getConsultationsByDossier(Long dossierId) {
        return consultationRepository.findByDossierMedicalId(dossierId);
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

}
