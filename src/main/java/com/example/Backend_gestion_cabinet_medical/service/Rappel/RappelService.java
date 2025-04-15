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

    // ⏰ Tous les jours à 8h du matin
    @Scheduled(cron = "0 0 8 * * *")
    public void envoyerRappelsAutomatiques() {
        List<RendezVous> rendezVousList = rendezVousRepository.findTodayRendezVous();

        for (RendezVous rdv : rendezVousList) {
            String patientEmail = rdv.getPatient().getEmail();
            String patientNom = rdv.getPatient().getNom();
            String heure = rdv.getDate().toLocalTime().toString();

            String subject = "🕐 Rappel de votre rendez-vous médical";
            String message = String.format("""
                    <h3>Bonjour %s,</h3>
                    <p>Nous vous rappelons votre rendez-vous prévu aujourd'hui à %s.</p>
                    <p>Merci de votre ponctualité.</p>
                    """, patientNom, heure);

            // Envoi du mail
            emailService.envoyerEmail(patientEmail, subject, message);

            // Sauvegarde du rappel
            Rappel rappel = new Rappel();
            rappel.setRendezVous(rdv);
            rappel.setSendDate(LocalDateTime.now());
            rappel.setSent(true);
            rappelRepository.save(rappel);
        }

        System.out.println("✅ Rappels envoyés  !");
    }
}
