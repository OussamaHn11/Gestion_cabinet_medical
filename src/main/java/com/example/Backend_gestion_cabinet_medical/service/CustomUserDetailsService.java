package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.entity.Medecin;
import com.example.Backend_gestion_cabinet_medical.entity.Secretaire;
import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        // Utiliser instanceof pour détecter le type
        String roleName;
        if (utilisateur instanceof Medecin) {
            roleName = "MEDECIN";
        } else if (utilisateur instanceof Secretaire) {
            roleName = "SECRETAIRE";
        } else {
            roleName = "UTILISATEUR"; // fallback si besoin
        }

        return User.withUsername(utilisateur.getUsername())
                .password(utilisateur.getPassword())
                .roles(roleName)
                .build();
    }
}
