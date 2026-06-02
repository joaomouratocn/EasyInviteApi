package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.InviteDto;
import br.com.arthivia.EasyInviteApi.models.entities.InviteEntity;
import br.com.arthivia.EasyInviteApi.repositories.InviteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final ImageService imageService;
    private final InviteRepository inviteRepository;
    private final ThemeService themeService;

    public Long getInviteNumber() {
        return inviteRepository.getInviteAmount();
    }

    public InviteDto getInvite(@Valid String slug) {
        var invite = inviteRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Invite not found!"));
        switch (invite.getStatus()) {
            case "EXP" -> throw new RuntimeException("Invite wait expired");
            case "WAP" -> throw new RuntimeException("Invite wait approval");
            case "ACT" -> {
                var theme = themeService.getThemeById(invite.getThemeId());
                return new InviteDto(invite, theme);
            }
            default -> throw new RuntimeException("Invite without status");
        }
    }

    public List<InviteDto> getInviteByUser(@Valid @NotBlank UUID userId) {
        var invites = inviteRepository.findByUserId(userId);
        return invites.stream().flatMap(List::stream).map(inviteEntity -> {
            var theme = themeService.getThemeById(inviteEntity.getThemeId());
            return new InviteDto(inviteEntity, theme);
        }).toList();
    }

    public UUID saveInvite(String name,
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
                           MultipartFile profileFile) {
        try {
            var imageUrl = imageService.saveImage(profileFile, "profiles");
            var status = "WAP";
            var newInviteEntity = new InviteEntity(name,
                    age,
                    eventDate,
                    address,
                    mapUrl,
                    description,
                    showAge,
                    enableTimer,
                    confirmEnable,
                    darkMode,
                    themeId,
                    status,
                    imageUrl);
            var result = inviteRepository.save(newInviteEntity);
            return result.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
