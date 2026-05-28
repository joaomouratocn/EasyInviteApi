package br.com.arthivia.EasyInviteApi.security;

import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;
import br.com.arthivia.EasyInviteApi.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final String cookieName;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository,
                                   @Value("${security.cookie.name:AUTH_TOKEN}") String cookieName) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.cookieName = cookieName;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = extractTokenFromCookie(request);
            if (token != null && jwtUtil.isTokenValid(token)) {
                try {
                    Jws<Claims> claims = jwtUtil.parseToken(token);
                    String userId = claims.getBody().getSubject();
                    Optional<UserEntity> userOpt = userRepository.findById(java.util.UUID.fromString(userId));
                    if (userOpt.isPresent()) {
                        UserEntity user = userOpt.get();
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                } catch (Exception ignored) {
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) return cookie.getValue();
        }
        return null;
    }
}

