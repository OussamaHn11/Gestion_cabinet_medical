package com.example.Backend_gestion_cabinet_medical.service.Rappel;

import com.example.Backend_gestion_cabinet_medical.entity.Rappel;
import com.example.Backend_gestion_cabinet_medical.entity.RendezVous;
import com.example.Backend_gestion_cabinet_medical.repository.RappelRepository;
import com.example.Backend_gestion_cabinet_medical.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RappelService {

    private final RendezVousRepository rendezVousRepository;
    private final RappelRepository rappelRepository;
    private final EmailService emailService;

    // Exécution automatique tous les jours à 8h
    @Scheduled(cron = "0 0 8 * * *")
    public void envoyerRappelsAutomatiques() {
        List<RendezVous> rendezVousList = rendezVousRepository.findTodayRendezVous();

        for (RendezVous rdv : rendezVousList) {
            String email = rdv.getPatient().getEmail();
            String nom = rdv.getPatient().getNom();
            String heure = rdv.getDate().toLocalTime().toString();

            String sujet = "🕐 Rappel de votre rendez-vous médical";
            String contenu = String.format("""
                    <h3>Bonjour %s,</h3>
                    <p>Nous vous rappelons votre rendez-vous prévu aujourd'hui à <strong>%s</strong>.</p>
                    <p>Merci de votre ponctualité.</p>
                    """, nom, heure);

            // Envoi du mail
            emailService.envoyerEmail(email, sujet, contenu);

            // Enregistrement du rappel
            Rappel rappel = new Rappel();
            rappel.setRendezVous(rdv);
            rappel.setSendDate(LocalDateTime.now());
            rappel.setSent(true);
            rappelRepository.save(rappel);
        }

        System.out.println("✅ Tous les rappels de rendez-vous ont été envoyés.");
    }
}
