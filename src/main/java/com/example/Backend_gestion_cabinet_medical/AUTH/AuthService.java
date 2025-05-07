package com.example.Backend_gestion_cabinet_medical.AUTH;

import com.example.Backend_gestion_cabinet_medical.config.JwtService;
import com.example.Backend_gestion_cabinet_medical.entity.Medecin;
import com.example.Backend_gestion_cabinet_medical.entity.Secretaire;
import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void ajouterUtilisateur(String username, String password, String email, String nom, String prénom, String téléphone, String type) {
        String encodedPassword = passwordEncoder.encode(password);

        Utilisateur utilisateur;
        if ("SECRETAIRE".equalsIgnoreCase(type)) {
            utilisateur = new Secretaire();
        } else if ("MEDECIN".equalsIgnoreCase(type)) {
            Medecin medecin = new Medecin();
            medecin.setSpecialite("Généraliste"); // à adapter si nécessaire
            utilisateur = medecin;
        } else {
            throw new IllegalArgumentException("Type d'utilisateur inconnu : " + type);
        }

        utilisateur.setUsername(username);
        utilisateur.setPassword(encodedPassword);
        utilisateur.setEmail(email);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prénom);
        utilisateur.setTelephone(téléphone);

        repository.save(utilisateur);
        log.info("✅ Utilisateur enregistré : {}", username);
    }

    public LoginResponse authenticate(LoginRequest request) {
        Utilisateur utilisateur = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Nom d'utilisateur ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), utilisateur.getPassword())) {
            throw new RuntimeException("Nom d'utilisateur ou mot de passe incorrect");
        }

        String role = utilisateur instanceof Medecin ? "MEDECIN" : "SECRETAIRE";

        UserDetails userDetails = User.builder()
                .username(utilisateur.getUsername())
                .password(utilisateur.getPassword())
                .roles(role)
                .build();

        String token = jwtService.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());

        log.info("🔐 Token généré pour : {} | Rôles = {}", utilisateur.getUsername(), roles);
        return LoginResponse.builder()
                .token(token)
                .roles(roles)
                .build();
    }

    public void modifierUtilisateur(Long id, RegisterRequest request) {
        Utilisateur utilisateur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (request.getUsername() != null) utilisateur.setUsername(request.getUsername());
        if (request.getEmail() != null) utilisateur.setEmail(request.getEmail());
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getPrénom() != null) utilisateur.setPrenom(request.getPrénom());
        if (request.getTéléphone() != null) utilisateur.setTelephone(request.getTéléphone());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        repository.save(utilisateur);
        log.info("✏️ Utilisateur modifié : id={}, username={}", id, utilisateur.getUsername());
    }

    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        repository.delete(utilisateur);
        log.info("🗑️ Utilisateur supprimé : id={}, username={}", id, utilisateur.getUsername());
    }

    public void modifierProfil(String username, RegisterRequest request) {
        Utilisateur utilisateur = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (request.getUsername() != null) utilisateur.setUsername(request.getUsername());
        if (request.getEmail() != null) utilisateur.setEmail(request.getEmail());
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getPrénom() != null) utilisateur.setPrenom(request.getPrénom());
        if (request.getTéléphone() != null) utilisateur.setTelephone(request.getTéléphone());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        repository.save(utilisateur);
        log.info("🛠️ Profil mis à jour : {}", username);
    }
}