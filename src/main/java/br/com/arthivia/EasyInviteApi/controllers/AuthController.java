package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.config.security.JwtUtil;
import br.com.arthivia.EasyInviteApi.models.dtos.CredentialRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.LoginRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.RegisterRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @org.springframework.beans.factory.annotation.Value("${security.cookie.name}")
    private String cookieName;

    @org.springframework.beans.factory.annotation.Value("${security.jwt.expiration-ms}")
    private long jwtExpirationMs;

    @org.springframework.beans.factory.annotation.Value("${security.cookie.secure}")
    private boolean cookieSecure;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        var user = authService.loginWithCredentials(request.email(), request.password());
        String jwt = jwtUtil.generateToken(user);

        String cookieHeader = buildSetCookieHeader(jwt, jwtExpirationMs / 1000);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieHeader)
                .body(user.toUserResponseDto());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        var user = authService.registerLocal(request);
        String jwt = jwtUtil.generateToken(user);

        String cookieHeader = buildSetCookieHeader(jwt, jwtExpirationMs / 1000);

        return ResponseEntity.status(201)
                .header(HttpHeaders.SET_COOKIE, cookieHeader)
                .body(user.toUserResponseDto());
    }

    @PostMapping("/google")
    public ResponseEntity<UserResponseDto> authWithGoogle(@Valid @RequestBody CredentialRequestDto request) {
        var user = authService.authOrRegisterGoogle(request.credential());
        String jwt = jwtUtil.generateToken(user);

        String cookieHeader = buildSetCookieHeader(jwt, jwtExpirationMs / 1000);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieHeader)
                .body(user.toUserResponseDto());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();

        // Cookie com maxAge=0 para deletar
        String cookieHeader = buildSetCookieHeader("", 0);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookieHeader)
                .build();
    }

    private String buildSetCookieHeader(String token, long maxAgeSeconds) {
        String secureFlag = cookieSecure ? "; Secure" : "";
        return String.format(
                "%s=%s; Max-Age=%d; Path=/; HttpOnly%s; SameSite=Lax",
                cookieName,
                token,
                maxAgeSeconds,
                secureFlag
        );
    }
}