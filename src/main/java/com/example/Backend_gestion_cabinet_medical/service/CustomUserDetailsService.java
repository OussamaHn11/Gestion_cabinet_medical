package com.example.Backend_gestion_cabinet_medical.service;

import com.example.Backend_gestion_cabinet_medical.entity.Role; // Import de votre enum Role
import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        // Récupérer le rôle directement depuis l'utilisateur
        Role role = utilisateur.getRole(); // Suppose que Utilisateur a une méthode getRole() retournant l'enum Role

        if (role == null) {
            throw new IllegalStateException("Rôle non défini pour l'utilisateur : " + username);
        }

        // Convertir l'enum Role en String pour Spring Security
        String roleName = role.name(); // Retourne "SECRETAIRE" ou "MEDECIN"

        return User.withUsername(utilisateur.getUsername())
                .password(utilisateur.getPassword())
                .roles(roleName) // Passer directement le nom du rôle
                .build();
    }
}