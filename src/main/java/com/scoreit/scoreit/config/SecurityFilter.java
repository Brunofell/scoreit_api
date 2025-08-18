package com.scoreit.scoreit.config;

import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final MemberRepository repository;

    public SecurityFilter(TokenService tokenService, MemberRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    // Prefixos públicos (alinhar com SecurityConfig)
    private static final Set<String> PUBLIC_PREFIXES = Set.of(
            "/member/login",
            "/member/post",
            "/member/confirm",
            "/api/forgot-password",
            "/api/reset-password",
            "/api/change-email",
            "/api/reset-email",
            "/spotify/api",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-resources",
            "/webjars",
            "/hello",
            "/feed"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Libera preflight CORS
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        String path = request.getRequestURI();
        for (String p : PUBLIC_PREFIXES) {
            if (path.startsWith(p)) return true;
            // também libera quando tem barra no fim
            if (path.startsWith(p + "/")) return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            try {
                // validateToken deve retornar o "subject" (email/username) OU lançar exceção se inválido
                String subject = tokenService.validateToken(token);
                if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails member = repository.findByEmail(subject);
                    if (member != null) {
                        var auth = new UsernamePasswordAuthenticationToken(
                                member, null, member.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (RuntimeException ex) {
                // Token inválido/expirado: não autentica e deixa fluxo seguir;
                // se a rota exigir auth, o Spring bloqueará depois.
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        return auth.substring(7);
    }
}
