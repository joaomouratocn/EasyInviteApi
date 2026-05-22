package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Value("${spring.google.client-id}")
    private String googleClientId;

    public UserResponseDto authOrRegister(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    // Garante que o token foi gerado para o SEU client ID do Angular
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

                // Esta linha valida a assinatura, expiração e o audience do Google automaticamente
                GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Atributos do usuário vindos do Google
                String googleUserId = payload.getSubject(); // ID único (sub)
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // Lógica do seu banco de dados usando Java 21 (Record ou Entity)
                return userRepository.findByGoogleId(googleUserId)
                        .map(userEntity -> updateUser(user, name))
                        .orElseGet(() -> userRepository.save(googleUserId, email, name));

            } else {
                throw new IllegalArgumentException("Token do Google inválido ou adulterado.");
            }
        } catch (IllegalArgumentException | GeneralSecurityException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
