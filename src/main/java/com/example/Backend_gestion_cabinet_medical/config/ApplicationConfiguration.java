package com.example.Backend_gestion_cabinet_medical.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
    private final UtilisateurRepository UtilisateurRepository;

    public ApplicationConfiguration(UtilisateurRepository UtilisateurRepository) {
        this.UtilisateurRepository = UtilisateurRepository;
    }

    @Bean
    UserDetailsService UtilisateurDetailsService() {
        return Utilisateurname -> (UserDetails) UtilisateurRepository.findByEmail(Utilisateurname)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(UtilisateurDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}