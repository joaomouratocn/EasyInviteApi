package br.com.arthivia.EasyInviteApi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "invites")
public class InviteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
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
    @Column(updatable = false)
    private String status;
    @Column(name = "theme_id")
    private String themeId;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDate;
}
