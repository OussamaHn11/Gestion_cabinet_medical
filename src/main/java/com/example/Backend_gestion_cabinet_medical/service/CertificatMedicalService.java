package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.dto.CertificatMedicalRequest;
import com.example.Backend_gestion_cabinet_medical.entity.CertificatMedical;
import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import com.example.Backend_gestion_cabinet_medical.repository.CertificatMedicalRepository;
import com.example.Backend_gestion_cabinet_medical.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificatMedicalService {

    private final CertificatMedicalRepository certificatMedicalRepository;
    private final ConsultationRepository consultationRepository;

    public CertificatMedical ajouterCertificatMedical(CertificatMedicalRequest request) {
        CertificatMedical certificat = new CertificatMedical();
        certificat.setType(request.getType());
        certificat.setDuree(request.getDuree());
        certificat.setDateDebut(request.getDateDebut());

        if (request.getConsultationId() != null) {
            Consultation consultation = consultationRepository.findById(request.getConsultationId())
                    .orElseThrow(() -> new RuntimeException("Consultation introuvable"));
            certificat.setConsultation(consultation);
        }

        return certificatMedicalRepository.save(certificat);
    }

    public Optional<CertificatMedical> getCertificatMedicalById(Long id) {
        return certificatMedicalRepository.findById(id);
    }

    public List<CertificatMedical> getAllCertificats() {
        return certificatMedicalRepository.findAll();
    }
    public void supprimerCertificatMedical(Long id) {
        certificatMedicalRepository.findById(id).ifPresentOrElse(
                certificat -> {
                    certificat.setConsultation(null); // coupe la liaison s’il y a un cascade bloquant
                    certificatMedicalRepository.delete(certificat);
                },
                () -> {
                    throw new RuntimeException("Certificat introuvable");
                }
        );
    }

}
