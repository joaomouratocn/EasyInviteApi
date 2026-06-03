package br.com.arthivia.EasyInviteApi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "invites")
@NoArgsConstructor
public class InviteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String address;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(updatable = false)
    private String slug;
    private Integer age;
    @Column(name = "map_url")
    private String mapUrl;
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "description", columnDefinition = "text[]")
    private List<String> description;
    @Column(name = "show_age")
    private boolean showAge;
    @Column(name = "confirm_enable")
    private boolean confirmEnable;
    @Column(name = "enable_timer")
    private boolean enableTimer;
    @Column(name = "dark_mode")
    private boolean darkMode;
    @Column(name = "profile_url")
    private String profileUrl;
    @Column(name = "user_id")
    private UUID userId;
    @Column(updatable = false)
    private String status;
    @Column(name = "theme_id")
    private UUID themeId;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    public InviteEntity(String name,
                        Integer age,
                        String eventDate,
                        String address,
                        String mapUrl,
                        List<String> description,
                        Boolean showAge,
                        Boolean enableTimer,
                        Boolean confirmEnable,
                        Boolean darkMode,
                        UUID themeId,
                        UUID userId,
                        String status,
                        String profileUrl) {
        this.name = name;
        this.age = age;
        this.eventDate = LocalDateTime.parse(eventDate);
        this.slug = UUID.randomUUID().toString();
        this.address = address;
        this.mapUrl = mapUrl;
        this.description = description;
        this.showAge = showAge;
        this.confirmEnable = confirmEnable;
        this.enableTimer = enableTimer;
        this.darkMode = darkMode;
        this.profileUrl = profileUrl;
        this.status = status;
        this.userId = userId;
        this.themeId = themeId;
    }
}
