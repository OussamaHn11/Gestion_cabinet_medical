package com.example.Backend_gestion_cabinet_medical.repository;

import com.example.Backend_gestion_cabinet_medical.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);

}

