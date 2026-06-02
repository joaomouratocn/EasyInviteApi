package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.RegisterRequestDto;
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

    public UserEntity loginWithCredentials(String email, String rawPassword) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }
        UserEntity user = userOpt.get();

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Conta criada via Google. Faça login com Google ou ative uma senha.");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    public UserEntity registerLocal(RegisterRequestDto dto) {
        String hashed = passwordEncoder.encode(dto.password());
        Optional<UserEntity> existingUser = userRepository.findByEmail(dto.email());

        if (existingUser.isPresent()) {
            UserEntity userEntity = existingUser.get();
            userEntity.setPassword(hashed);
            userEntity.setSendNotifications(dto.sendNewsletter());
            userEntity.setLastLoginAt(LocalDateTime.now());
            userRepository.updateUser(userEntity);
            return userEntity;
        } else {
            UserEntity newUser = new UserEntity();
            newUser.setEmail(dto.email());
            newUser.setName(dto.name());
            newUser.setPassword(hashed);
            newUser.setSendNotifications(dto.sendNewsletter());
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setLastLoginAt(LocalDateTime.now());
            return userRepository.save(newUser);
        }
    }

    public UserEntity authOrRegisterGoogle(String idTokenString) {
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
                String pictureUrl = (String) payload.get("picture");

                return userRepository.findByEmail(email).map(userEntity -> {
                    userEntity.setName(name);
                    userEntity.setEmail(email);
                    userEntity.setGoogleId(googleUserId);
                    userEntity.setPictureUrl(pictureUrl);
                    userEntity.setLastLoginAt(java.time.LocalDateTime.now());
                    userRepository.updateUser(userEntity);
                    return userEntity;
                }).orElseGet(() -> {
                    UserEntity newUser = new UserEntity(googleUserId, email, name, pictureUrl);
                    return userRepository.save(newUser);
                });

            } else {
                throw new IllegalArgumentException("Token do Google inválido, expirou ou o Client ID está incorreto.");
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Erro interno ao validar autenticação com o Google: " + e.getMessage(), e);
        }
    }
}