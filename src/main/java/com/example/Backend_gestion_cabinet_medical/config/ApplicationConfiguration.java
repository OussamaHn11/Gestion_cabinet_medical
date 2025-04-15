package com.example.Backend_gestion_cabinet_medical.config;

import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class ApplicationConfiguration {
    private final UtilisateurRepository utilisateurRepository;

    public ApplicationConfiguration(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Rechercher l'utilisateur par son nom d'utilisateur
            Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

            // Convertir le rôle de l'utilisateur en une autorité Spring Security
            String role = "ROLE_" + utilisateur.getRole().name(); // Ajoute "ROLE_" pour correspondre à Spring Security

            // Créer un objet User Spring Security
            return org.springframework.security.core.userdetails.User
                    .withUsername(utilisateur.getUsername())
                    .password(utilisateur.getPassword())
                    .roles(utilisateur.getRole().name())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisation de BCrypt pour encoder les mots de passe
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}