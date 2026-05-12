package br.com.arthivia.EasyInviteApi.models.dtos;

import br.com.arthivia.EasyInviteApi.models.entities.ThemeEntity;

import java.time.LocalDateTime;

public record ThemeDto(
        String id, String themeName, String title, String subTitle, String modalTitle,
        String confirmText, String getCoverUrl, String bgImageUrl, String getBgProfImageUrl, ColorSchema lightTheme,
        ColorSchema darkTheme, LocalDateTime createdAt
) {
    public ThemeDto(ThemeEntity themeEntity) {
        this(
                themeEntity.getId(),
                themeEntity.getThemeName(),
                themeEntity.getTitle(),
                themeEntity.getSubTitle(),
                themeEntity.getModalTitle(),
                themeEntity.getConfirmText(),
                themeEntity.getCoverUrl(),
                themeEntity.getBgImageUrl(),
                themeEntity.getBgProfImageUrl(),
                themeEntity.getLightTheme(),
                themeEntity.getDarkTheme(),
                themeEntity.getCreatedAt()
        );
    }
}
