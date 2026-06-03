package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.InviteDto;
import br.com.arthivia.EasyInviteApi.models.entities.InviteEntity;
import br.com.arthivia.EasyInviteApi.repositories.InviteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
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
        return getInviteDto(invite);
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
                           UUID userId,
                           MultipartFile profileFile) {
        System.out.println("O ENDPOINT FOI CHAMADO!");
        log.info("Nome recebido: " + userId);

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
                    userId,
                    status,
                    imageUrl);

            var result = inviteRepository.save(newInviteEntity);
            return result.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InviteDto getInviteById(@Valid UUID uuid) {
        var invite = inviteRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Invite not found!"));
        return getInviteDto(invite);
    }

    @NonNull
    private InviteDto getInviteDto(InviteEntity invite) {
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
}
