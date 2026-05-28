package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;
import br.com.arthivia.EasyInviteApi.repositories.UserRepository;
import br.com.arthivia.EasyInviteApi.security.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Value("${spring.google.client-id}")
    private String googleClientId;

    public UserResponseDto authOrRegister(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    // Garante que o token foi gerado para o SEU client ID do Angular
                    .setAudience(Collections.singletonList(googleClientId)).build();

            // Esta linha valida a assinatura, expiração e o audience do Google automaticamente
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String googleUserId = payload.getSubject();
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                return userRepository.findByGoogleId(googleUserId).map(userEntity -> {
                    userEntity.setName(name);
                    userEntity.setLastLoginAt(java.time.LocalDateTime.now());
                    return userRepository.save(userEntity);
                }).orElseGet(() -> {
                    UserEntity newUser = new UserEntity(googleUserId, email, name, null);
                    return userRepository.save(newUser);
                }).toUserResponseDto();

            } else {
                throw new IllegalArgumentException("Token do Google inválido ou adulterado.");
            }
        } catch (IllegalArgumentException | GeneralSecurityException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
