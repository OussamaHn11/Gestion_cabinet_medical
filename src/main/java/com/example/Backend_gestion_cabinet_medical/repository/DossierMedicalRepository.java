package com.example.Backend_gestion_cabinet_medical.repository;

import com.example.Backend_gestion_cabinet_medical.entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {
    Optional<DossierMedical> findByPatientId(Long patientId);
}