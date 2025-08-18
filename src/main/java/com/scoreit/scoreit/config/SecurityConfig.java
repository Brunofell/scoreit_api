package com.scoreit.scoreit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // CORS + CSRF desligado para API stateless
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                // Stateless
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Autorização
                .authorizeHttpRequests(auth -> auth
                        // ✅ Preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ Rotas públicas (com e sem barra no final)
                        .requestMatchers(HttpMethod.GET,
                                "/hello", "/hello/",
                                "/feed", "/feed/",
                                "/member/confirm", "/member/confirm/",
                                // swagger
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/member/post", "/member/post/",
                                "/member/login", "/member/login/",
                                "/api/forgot-password", "/api/forgot-password/",
                                "/api/reset-password", "/api/reset-password/",
                                "/api/change-email", "/api/change-email/",
                                "/api/reset-email", "/api/reset-email/"
                        ).permitAll()

                        // spotify callback público (ajuste se preciso)
                        .requestMatchers("/spotify/api**").permitAll()

                        // ✅ todo o resto autenticado
                        .anyRequest().authenticated()
                )

                // Filtro JWT antes do UsernamePasswordAuthenticationFilter
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        // Railway → Variables:
        // FRONTEND_ORIGINS="https://scoreit.vercel.app,http://localhost:3000"
        String env = System.getenv("FRONTEND_ORIGINS");

        List<String> origins = new ArrayList<>();
        if (env != null && !env.isBlank()) {
            Arrays.stream(env.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(origins::add);
        }

        // Fallback seguro
        if (origins.isEmpty()) {
            origins = List.of("https://scoreit.vercel.app", "http://localhost:3000");
        }

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // expõe só o necessário
        config.setExposedHeaders(List.of("Authorization"));
        // se usar cookies/same-site em algum momento
        config.setAllowCredentials(true);
        // cache do preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // aplica para toda a API
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration cfg
    ) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
