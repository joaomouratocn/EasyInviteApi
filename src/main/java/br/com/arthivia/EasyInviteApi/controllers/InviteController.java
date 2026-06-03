package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.InviteDto;
import br.com.arthivia.EasyInviteApi.services.InviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invites")
public class InviteController {
    private final InviteService inviteService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> saveInvite(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("eventDate") String eventDate,
            @RequestParam("address") String address,
            @RequestParam("mapUrl") String mapUrl,
            @RequestParam("description") List<String> description,
            @RequestParam("showAge") Boolean showAge,
            @RequestParam("enableTimer") Boolean enableTimer,
            @RequestParam("confirmEnable") Boolean confirmEnable,
            @RequestParam("darkMode") Boolean darkMode,
            @RequestParam("themeId") UUID themeId,
            @RequestParam("userId") UUID userId,
            @RequestPart("profileFile") MultipartFile profileFile) {

        UUID id = inviteService.saveInvite(name,
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
                profileFile);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InviteDto>> getInviteByUser(@PathVariable @Valid UUID userId) {
        var result = inviteService.getInviteByUser(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getInviteNumber() {
        var result = inviteService.getInviteNumber();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<InviteDto> getInviteById(@PathVariable @Valid UUID uuid) {
        var result = inviteService.getInviteById(uuid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<InviteDto> getInvite(@PathVariable @Valid String slug) {
        var result = inviteService.getInvite(slug);
        return ResponseEntity.ok(result);
    }
}
