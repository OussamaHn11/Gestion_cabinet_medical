package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.DTO.RendezVousRequest;
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


    public RendezVous saveRendezVous( RendezVousRequest request ) {
        // Vérifier si le patient existe
        Long patientId = request.getPatientId();
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        RendezVous rendezVous = new RendezVous();
        rendezVous.setPatient(patient);
        rendezVous.setDate(request.getDate());
        rendezVous.setMotif(request.getMotif());

        // Enregistrer le rendez-vous
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }


    public RendezVous updateRendezVous(Long id, RendezVousRequest request) {
        return rendezVousRepository.findById(id).map(rdv -> {
            rdv.setDate(request.getDate());
            rdv.setMotif(request.getMotif());

            // Vérifier et mettre à jour le patient
            Patient patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
            rdv.setPatient(patient);

            return rendezVousRepository.save(rdv);
        }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
    }


    public void deleteRendezVous(Long id) {
        if (!rendezVousRepository.existsById(id)) {
            throw new RuntimeException("Rendez-vous non trouvé avec l'ID : " + id);
        }
        rendezVousRepository.deleteById(id);
    }

}
