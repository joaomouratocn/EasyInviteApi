package br.com.arthivia.EasyInviteApi.repositories;

import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByGoogleId(String googleId);

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :#{#userEntity.password}, " +
            "u.sendNotifications = :#{#userEntity.sendNotifications}, " +
            "u.lastLoginAt = :#{#userEntity.lastLoginAt} " +
            "WHERE u.id = :#{#userEntity.id}")
    void updateUser(UserEntity userEntity);
}
