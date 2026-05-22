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
    public ResponseEntity<UUID> save(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String eventDate,
            @RequestParam String address,
            @RequestParam(required = false) String mapUrl,
            @RequestParam List<String> description,
            @RequestParam Boolean showAge,
            @RequestParam Boolean enableTimer,
            @RequestParam Boolean confirmEnable,
            @RequestParam Boolean darkMode,
            @RequestParam UUID themeId,
            @RequestPart(required = false) MultipartFile profileFile) {

        var result = inviteService.saveInvite(name,
                age,
                eventDate,
                address,
                mapUrl,
                description,
                showAge,
                enableTimer,
                confirmEnable,
                darkMode,
                themeId, profileFile);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getInviteNumber() {
        var result = inviteService.getInviteNumber();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<InviteDto> getInvite(@PathVariable @Valid String slug) {
        var result = inviteService.getInvite(slug);
        return ResponseEntity.ok(result);
    }
}
