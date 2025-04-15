package com.example.Backend_gestion_cabinet_medical.AUTH;

import com.example.Backend_gestion_cabinet_medical.entity.Role;
import com.example.Backend_gestion_cabinet_medical.entity.Utilisateur;
import com.example.Backend_gestion_cabinet_medical.repository.UtilisateurRepository;
import com.example.Backend_gestion_cabinet_medical.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UtilisateurRepository repository;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    public LoginResponse register(RegisterRequest request) {
        User user = null; var jwtToken = jwtService.generateToken(user);
        log.info("JWT Token generated: {}", jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }
    public void ajouterUtilisateur(String username, String password, String email, String nom, String prénom, String téléphone, Role role) {
        // Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(password);

        // Créer l'utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(username);
        utilisateur.setPassword(encodedPassword);  // Le mot de passe est encodé
        utilisateur.setEmail(email);
        utilisateur.setNom(nom);
        utilisateur.setPrénom(prénom);
        utilisateur.setTéléphone(téléphone);
        utilisateur.setRole(role);

        // Sauvegarder l'utilisateur dans la base de données
        repository.save(utilisateur); // Utilisez 'repository' ici pour sauvegarder dans la base de données
    }

    public LoginResponse authenticate(LoginRequest request) {
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Adresse mail ou Mot de passe Incorrect"));

        // Comparer le mot de passe fourni avec le mot de passe encodé dans la base de données
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Username ou password Incorrect");
        }

        // Génération du token après validation du mot de passe
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // L'utilisateur doit avoir le mot de passe encodé
                .roles(user.getRole().name()) // Ajouter le rôle pour Spring Security
                .build();

        var jwtToken = jwtService.generateToken(userDetails);
        return new com.example.Backend_gestion_cabinet_medical.AUTH.LoginResponse(jwtToken);
    }
    public void modifierUtilisateur(Long id, RegisterRequest request) {
        Utilisateur utilisateur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Mettre à jour les champs si fournis
        if (request.getUsername() != null) utilisateur.setUsername(request.getUsername());
        if (request.getEmail() != null) utilisateur.setEmail(request.getEmail());
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getPrénom() != null) utilisateur.setPrénom(request.getPrénom());
        if (request.getTéléphone() != null) utilisateur.setTéléphone(request.getTéléphone());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() != null) utilisateur.setRole(request.getRole());

        log.info("Utilisateur modifié : id={}, username={}", id, utilisateur.getUsername());
        repository.save(utilisateur);
    }

    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        log.info("Utilisateur supprimé : id={}, username={}", id, utilisateur.getUsername());
        repository.delete(utilisateur);
    }

    public void modifierProfil(String username, RegisterRequest request) {
        Utilisateur utilisateur = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Ne pas permettre de modifier le rôle via le profil
        if (request.getUsername() != null) utilisateur.setUsername(request.getUsername());
        if (request.getEmail() != null) utilisateur.setEmail(request.getEmail());
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getPrénom() != null) utilisateur.setPrénom(request.getPrénom());
        if (request.getTéléphone() != null) utilisateur.setTéléphone(request.getTéléphone());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        log.info("Profil modifié : username={}", username);
        repository.save(utilisateur);
    }
}



