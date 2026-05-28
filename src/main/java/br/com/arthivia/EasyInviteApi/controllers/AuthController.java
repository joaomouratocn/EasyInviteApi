package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.TokenRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.services.AuthService;
import br.com.arthivia.EasyInviteApi.security.JwtUtil;
import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final JwtUtil jwtUtil;

    @org.springframework.beans.factory.annotation.Value("${security.cookie.name:AUTH_TOKEN}")
    private String cookieName;

    @org.springframework.beans.factory.annotation.Value("${security.jwt.expiration-ms:604800000}")
    private long jwtExpirationMs;

    @PostMapping("/google")
    public ResponseEntity<UserResponseDto> autenticarComGoogle(@Valid @RequestBody TokenRequestDto request) {
        UserResponseDto user = authService.authOrRegister(request.token());
        String jwt = jwtUtil.generateToken(user);

        // build secure httpOnly cookie
        ResponseCookie cookie = ResponseCookie.from(cookieName, jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtExpirationMs / 1000)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new UserResponseDto(user));
    }
}
