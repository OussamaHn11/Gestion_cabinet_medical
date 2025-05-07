package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.dto.OrdonnanceRequest;
import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import com.example.Backend_gestion_cabinet_medical.entity.Medicament;
import com.example.Backend_gestion_cabinet_medical.entity.Ordonnance;
import com.example.Backend_gestion_cabinet_medical.repository.ConsultationRepository;
import com.example.Backend_gestion_cabinet_medical.repository.OrdonnanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;
    private final ConsultationRepository consultationRepository;

    public Ordonnance ajouterOrdonnance(OrdonnanceRequest request, Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));

        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setFrequence(request.getFrequence());
        ordonnance.setDuree(request.getDuree());
        ordonnance.setConsultation(consultation);

        // Lier les médicaments
        List<Medicament> medicaments = request.getMedicaments();
        for (Medicament medicament : medicaments) {
            medicament.setOrdonnance(ordonnance); // liaison bidirectionnelle
        }
        ordonnance.setMedicaments(medicaments);

        consultation.setOrdonnance(ordonnance); // liaison consultation → ordonnance

        return ordonnanceRepository.save(ordonnance);
    }

    public Optional<Ordonnance> getOrdonnanceById(Long id) {
        return ordonnanceRepository.findById(id);
    }

    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceRepository.findAll();
    }

    public void supprimerOrdonnance(Long id) {
        if (!ordonnanceRepository.existsById(id)) {
            throw new IllegalArgumentException("L'ordonnance avec cet ID n'existe pas.");
        }
        ordonnanceRepository.deleteById(id);
    }
}
