package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.RegisterRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;
import br.com.arthivia.EasyInviteApi.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.google.client-id}")
    private String googleClientId;

    public UserResponseDto loginWithCredentials(String email, String rawPassword) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }
        UserEntity user = userOpt.get();

        // Se usuário foi criado via Google e não tem senha definida
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Conta criada via Google. Faça login com Google ou ative uma senha.");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        return user.toUserResponseDto();
    }

    public UserResponseDto registerLocal(RegisterRequestDto dto) {
        // Verifica se email já existe
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com esse e-mail.");
        }

        String hashed = passwordEncoder.encode(dto.password());

        UserEntity newUser = new UserEntity();
        newUser.setEmail(dto.email());
        newUser.setName(dto.name());
        newUser.setPassword(hashed);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setLastLoginAt(LocalDateTime.now());
        // googleId null para conta local

        UserEntity saved = userRepository.save(newUser);
        return saved.toUserResponseDto();
    }

    public UserResponseDto authOrRegisterGoogle(String idTokenString) {
        // 1. Validação prévia para evitar requisições desnecessárias ao Google
        if (idTokenString == null || idTokenString.trim().isEmpty()) {
            throw new IllegalArgumentException("O token enviado está vazio ou nulo.");
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

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
                // Se o token for inválido/expirou, o verifier retorna null
                throw new IllegalArgumentException("Token do Google inválido, expirou ou o Client ID está incorreto.");
            }

        } catch (GeneralSecurityException | IOException e) {
            // IMPORTANTE: Passamos o 'e' no construtor para herdar o StackTrace real (Causa Raiz)
            throw new RuntimeException("Erro interno ao validar autenticação com o Google: " + e.getMessage(), e);
        }
    }
}