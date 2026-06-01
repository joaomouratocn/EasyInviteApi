package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.config.security.JwtUtil;
import br.com.arthivia.EasyInviteApi.models.dtos.CredentialRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.LoginRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.RegisterRequestDto;
import br.com.arthivia.EasyInviteApi.models.dtos.UserResponseDto;
import br.com.arthivia.EasyInviteApi.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
        UserResponseDto user = authService.loginWithCredentials(request.email(), request.password());
        String jwt = jwtUtil.generateToken(user);

        ResponseCookie cookie = buildAuthCookie(jwt, jwtExpirationMs / 1000);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        UserResponseDto user = authService.registerLocal(request);
        String jwt = jwtUtil.generateToken(user);

        ResponseCookie cookie = buildAuthCookie(jwt, jwtExpirationMs / 1000);

        return ResponseEntity.status(201)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @PostMapping("/google")
    public ResponseEntity<UserResponseDto> authWithGoogle(@Valid @RequestBody CredentialRequestDto request) {
        UserResponseDto user = authService.authOrRegisterGoogle(request.credential());
        String jwt = jwtUtil.generateToken(user);

        ResponseCookie cookie = buildAuthCookie(jwt, jwtExpirationMs / 1000);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();

        ResponseCookie cookie = buildAuthCookie("", 0);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    private ResponseCookie buildAuthCookie(String value, long maxAgeSeconds) {
        return ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(maxAgeSeconds)
                .sameSite("Lax")
                .build();
    }
}
