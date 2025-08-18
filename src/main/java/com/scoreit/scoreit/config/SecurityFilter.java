package com.scoreit.scoreit.config;

import com.scoreit.scoreit.entity.Member;
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

    // Rotas públicas (prefix match). Mantenha alinhado com SecurityConfig.
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
        // Libera preflight de CORS
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        String path = request.getRequestURI();
        for (String p : PUBLIC_PREFIXES) {
            if (path.startsWith(p)) return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = recoverToken(request);

        // Sem Authorization -> segue o fluxo; rotas protegidas vão exigir autenticação depois
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // validateToken deve devolver o "subject" (ex.: e-mail) OU lançar exceção se inválido/expirado
            String subject = tokenService.validateToken(token);
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails member = repository.findByEmail(subject);
                if (member != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            member,
                            null,
                            member.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (RuntimeException ex) {
            // Token inválido/expirado: NÃO derruba a requisição aqui.
            // Apenas não autentica. Se a rota exigir autenticação, o Spring barrará mais adiante.
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.substring(7);
    }
}
