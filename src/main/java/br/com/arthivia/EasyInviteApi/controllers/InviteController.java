package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.InviteDto;
import br.com.arthivia.EasyInviteApi.services.InviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invites")
public class InviteController {
    private final InviteService inviteService;

    @GetMapping("/count")
    public ResponseEntity<Long> getInviteNumber(){
        var result = inviteService.getInviteNumber();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{slug}")
    public ResponseEntity<InviteDto> getInvite(@PathVariable @Valid String slug){
        var result = inviteService.getInvite(slug);
        return ResponseEntity.ok(result);
    }
}
