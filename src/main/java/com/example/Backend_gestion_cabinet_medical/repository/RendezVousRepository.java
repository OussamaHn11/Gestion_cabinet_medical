package com.example.Backend_gestion_cabinet_medical.repository;

import com.example.Backend_gestion_cabinet_medical.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByPatient_Id(Long patientId);
    List<RendezVous> findByDateBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT r FROM RendezVous r WHERE FUNCTION('DATE', r.date) = CURRENT_DATE")
    List<RendezVous> findTodayRendezVous();
}