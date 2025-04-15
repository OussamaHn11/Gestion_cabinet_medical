package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.entity.Patient;
import com.example.Backend_gestion_cabinet_medical.entity.RendezVous;
import com.example.Backend_gestion_cabinet_medical.repository.PatientRepository;
import com.example.Backend_gestion_cabinet_medical.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;


    public RendezVous saveRendezVous(RendezVous rendezVous) {
        // Vérifier si le patient existe
        Long patientId = rendezVous.getPatient().getId();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        // Associer le patient au rendez-vous
        rendezVous.setPatient(patient);

        // Enregistrer le rendez-vous
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }


    public RendezVous updateRendezVous(Long id, RendezVous updatedRdv) {
        return rendezVousRepository.findById(id).map(rdv -> {
            rdv.setDate(updatedRdv.getDate());
            rdv.setMotif(updatedRdv.getMotif());

            // Vérifier et mettre à jour le patient
            Long patientId = updatedRdv.getPatient().getId();
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

            rdv.setPatient(patient);
            return rendezVousRepository.save(rdv);
        }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
    }

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }
}
