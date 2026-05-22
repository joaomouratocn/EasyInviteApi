package br.com.arthivia.EasyInviteApi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String googleId;

    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login_at", insertable = false, updatable = false)
    private LocalDateTime lastLoginAt;

    // Construtor auxiliar para novos cadastros
    public UserEntity(String googleId, String email, String name, String pictureUrl) {
        this.googleId = googleId;
        this.email = email;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.lastLoginAt = LocalDateTime.now();
    }
}
