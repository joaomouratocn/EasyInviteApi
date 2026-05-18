package br.com.arthivia.EasyInviteApi.models.dtos;

import br.com.arthivia.EasyInviteApi.models.entities.InviteEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record InviteDto(
        UUID id,
        String name,
        String slug,
        Integer age,
        String address,
        LocalDateTime eventDate,
        String mapUrl,
        List<String> description,
        boolean showAge,
        boolean confirmEnable,
        boolean enableTimer,
        boolean darkMode,
        String profileUrl,
        String status,
        UUID themeId,
        LocalDateTime createdDate
) {
    public InviteDto(InviteEntity inviteEntity) {
        this(inviteEntity.getId(),
                inviteEntity.getName(),
                inviteEntity.getSlug(),
                inviteEntity.getAge(),
                inviteEntity.getAddress(),
                inviteEntity.getEventDate(),
                inviteEntity.getMapUrl(),
                inviteEntity.getDescription(),
                inviteEntity.isShowAge(),
                inviteEntity.isConfirmEnable(),
                inviteEntity.isEnableTimer(),
                inviteEntity.isDarkMode(),
                inviteEntity.getProfileUrl(),
                inviteEntity.getStatus(),
                inviteEntity.getThemeId(),
                inviteEntity.getCreatedDate());
    }
}
