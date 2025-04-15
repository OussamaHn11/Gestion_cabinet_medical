package com.example.Backend_gestion_cabinet_medical.repository;

import com.example.Backend_gestion_cabinet_medical.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
