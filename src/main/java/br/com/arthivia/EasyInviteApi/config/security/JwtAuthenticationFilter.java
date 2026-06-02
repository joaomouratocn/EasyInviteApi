package br.com.arthivia.EasyInviteApi.config.security;

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
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final String cookieName;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository,
                                   @Value("${security.cookie.name}") String cookieName) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.cookieName = cookieName;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // ✅ Só verifica se ainda não há autenticação
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = extractTokenFromCookie(request);

            if (token != null && !token.isEmpty() && jwtUtil.isTokenValid(token)) {
                try {
                    Jws<Claims> claims = jwtUtil.parseToken(token);
                    String userId = claims.getBody().getSubject();

                    Optional<UserEntity> userOpt = userRepository.findById(java.util.UUID.fromString(userId));

                    if (userOpt.isPresent()) {
                        UserEntity user = userOpt.get();
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities() // ✅ usa os roles/autoridades do usuário
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                } catch (Exception e) {
                    // ✅ Loga o erro em produção (opcional)
                    logger.warn("Falha ao validar token JWT: " + e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}