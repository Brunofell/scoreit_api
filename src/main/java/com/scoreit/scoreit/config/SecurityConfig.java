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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 🔓 preflight precisa ser liberado
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/hello/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/feed/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/member/post").permitAll()
                        .requestMatchers(HttpMethod.POST, "/member/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/confirm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/change-email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reset-email").permitAll()
                        .requestMatchers("/spotify/api**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        // Railway → Variable FRONTEND_ORIGINS="https://scoreit.vercel.app,http://localhost:3000"
        String env = System.getenv("FRONTEND_ORIGINS");

        List<String> origins = new ArrayList<>();
        if (env != null && !env.isBlank()) {
            Arrays.stream(env.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(origins::add);
        }

        // Fallback seguro se a env não estiver setada
        if (origins.isEmpty()) {
            origins = List.of("https://scoreit.vercel.app", "http://localhost:3000");
        }

        CorsConfiguration config = new CorsConfiguration();
        // 🔒 Whitelist explícita (sem wildcard)
        config.setAllowedOrigins(origins);

        // Métodos realmente usados (inclui OPTIONS pro preflight)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Headers comuns do teu front; adicione se precisar de mais
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Se você precisar ler o header Authorization no client (geralmente não precisa),
        // deixe exposto. Não expomos nada além do necessário.
        config.setExposedHeaders(List.of("Authorization"));

        // Se um dia usar cookies/same-site, mantenha true; com Authorization header não é obrigatório, mas não atrapalha
        config.setAllowCredentials(true);

        // Cache do preflight (em segundos)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
