package br.com.arthivia.EasyInviteApi.models.entities;

import br.com.arthivia.EasyInviteApi.models.dtos.ColorSchema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "themes")
public class ThemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "theme_name")
    private String themeName;
    private String title;
    private String subTitle;
    @Column(name = "modal_title")
    private String modalTitle;
    @Column(name = "confirm_text")
    private String confirmText;
    @Column(name = "cover_url")
    private String coverUrl;
    @Column(name = "bg_image_url")
    private String bgImageUrl;
    @Column(name = "bg_prof_image_url")
    private String bgProfImageUrl;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "light_theme", columnDefinition = "jsonb")
    private ColorSchema lightTheme;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dark_theme", columnDefinition = "jsonb")
    private ColorSchema DarkTheme;
    @Column(name = "create_at")
    private LocalDateTime createdAt;

}
