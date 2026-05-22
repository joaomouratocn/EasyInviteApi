package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.TokenRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/google")
    public ResponseEntity<UserResponseDto> autenticarComGoogle(@Valid @RequestBody TokenRequestDto request) {
        //TODO
    }
}
