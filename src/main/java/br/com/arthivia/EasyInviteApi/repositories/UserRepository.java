package br.com.arthivia.EasyInviteApi.repositories;

import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    // Busca principal do fluxo OAuth2: encontra pelo ID interno do Google
    Optional<UserEntity> findByGoogleId(String googleId);

    // Busca utilitária caso precise verificar se o e-mail já existe no sistema
    Optional<UserEntity> findByEmail(String email);
}
