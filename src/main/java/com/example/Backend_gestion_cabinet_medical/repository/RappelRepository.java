package com.example.Backend_gestion_cabinet_medical.repository;

import com.example.Backend_gestion_cabinet_medical.entity.Rappel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RappelRepository extends JpaRepository<Rappel, Long> {
    List<Rappel> findBySentFalse();
    List<Rappel> findByRendezVousId(Long rendezVousId);
}
