package com.example.Backend_gestion_cabinet_medical.AUTH;

import com.example.Backend_gestion_cabinet_medical.config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Slf4j

public class AuthController {
    private final AuthService service;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        service.ajouterUtilisateur(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getNom(),
                request.getPrenom(),
                request.getTelephone(),
                request.getType(),
                request.getSpecialite()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Utilisateur ajouté avec succès"));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {
        try {
            // Appelle le service pour authentifier
            LoginResponse response = service.authenticate(request);

            // Vérifie que le token est bien généré
            if (response.getToken() == null || response.getToken().isEmpty()) {
                log.error("🚨 Le token n'a pas été généré !");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Erreur interne : token non généré"));
            }

            log.info("✅ Authentification réussie pour utilisateur : {}", request.getUsername());
            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            log.warn("❌ Utilisateur non trouvé : {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Nom d'utilisateur ou mot de passe invalide"));

        } catch (BadCredentialsException e) {
            log.warn("❌ Mot de passe invalide pour : {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Nom d'utilisateur ou mot de passe invalide"));

        } catch (Exception e) {
            log.error("🚨 Erreur d'authentification : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erreur lors de l'authentification"));
        }
    }

    @PutMapping("/secretaires/{id}")
    @PreAuthorize("hasRole('MEDECIN')") // Seuls les médecins peuvent modifier
    public ResponseEntity<?> modifierSecretaire(@PathVariable Long id, @RequestBody RegisterRequest request) {
        try {
          //  log.info("Modification de l'utilisateur ID={} avec role={}", id, request.getRole());
            service.modifierUtilisateur(id, request);
            return ResponseEntity.ok(Map.of("message", "Secrétaire modifiée avec succès"));
        } catch (Exception e) {
            log.error("🚨 Erreur lors de la modification : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erreur interne lors de la modification de l'utilisateur: " + e.getMessage()));
        }
    }

    @DeleteMapping("/secretaires/{id}")
    @PreAuthorize("hasRole('MEDECIN')") // Seuls le médecin peut supprimer
    public ResponseEntity<?> supprimerSecretaire(@PathVariable Long id) {
        try {
            log.info("Suppression de l'utilisateur ID={}", id);
            service.supprimerUtilisateur(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("🚨 Erreur lors de la suppression : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erreur interne lors de la suppression de l'utilisateur: " + e.getMessage()));
        }
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()") // Tout utilisateur authentifié peut modifier son profil
    public ResponseEntity<?> modifierProfil(@RequestBody RegisterRequest request,
                                            Authentication authentication) {
        try {
            if (authentication == null) {
                log.error("🚨 Authentication est null - Vérifiez le token ou la configuration de sécurité");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Utilisateur non authentifié"));
            }
            String username = authentication.getName();
            log.info("Modification du profil de {}", username);
            service.modifierProfil(username, request);
            return ResponseEntity.ok(Map.of("message", "Profil modifié avec succès"));
        } catch (Exception e) {
            log.error("🚨 Erreur lors de la modification du profil : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erreur interne lors de la modification du profil: " + e.getMessage()));
        }
    }





}