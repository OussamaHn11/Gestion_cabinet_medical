package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import com.example.Backend_gestion_cabinet_medical.entity.ExamenMedical;
import com.example.Backend_gestion_cabinet_medical.repository.ConsultationRepository;
import com.example.Backend_gestion_cabinet_medical.repository.ExamenMedicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamenMedicalService {

    private final ExamenMedicalRepository examenMedicalRepository;
    private final ConsultationRepository consultationRepository;

    public ExamenMedical ajouterExamen(Long consultationId, ExamenMedical examen) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));

        examen.setConsultation(consultation);
        return examenMedicalRepository.save(examen);
    }

    public List<ExamenMedical> getAll() {
        return examenMedicalRepository.findAll();
    }

    public void supprimerExamen(Long id) {
        examenMedicalRepository.deleteById(id);
    }
}
