package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.services.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invite")
public class InviteController {
    private final InviteService inviteService;

    @GetMapping("/count")
    public ResponseEntity<Long> getInviteNumber(){
        var result = inviteService.getInviteNumber();
        return ResponseEntity.ok(result);
    }
}
