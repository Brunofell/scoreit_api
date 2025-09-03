package com.scoreit.scoreit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailConfirmationService {

    private final RestClient resendClient;

    // URL pública do frontend (sem / no final), ex: https://www.scoreit.com.br
    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    // Locale padrão para compor a rota [locale]/confirma_conta
    @Value("${app.frontend.default-locale:pt}")
    private String defaultLocale;

    // remetente padrão (ex.: nao-responda@scoreit.com.br)
    @Value("${mail.from}")
    private String mailFrom;

    public EmailConfirmationService(RestClient resendClient) {
        this.resendClient = resendClient;
    }

    /**
     * Envia o e-mail de verificação usando a API do Resend.
     * Se falhar, lança RuntimeException para o chamador poder cancelar o cadastro.
     */
    public void sendVerificationEmailOrThrow(String to, String token) {
        final String subject = "Confirmação de E-mail - ScoreIt";

        final String encoded = URLEncoder.encode(token, StandardCharsets.UTF_8);
        final String confirmLink = String.format("%s/%s/confirma_conta?token=%s",
                trimTrailingSlash(frontendBaseUrl), defaultLocale, encoded);

        final String html = """
                <div style="font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;">
                  <p>Olá! Clique no botão abaixo para confirmar seu e-mail:</p>
                  <p>
                    <a href="%s" style="display:inline-block;padding:12px 18px;background:#0f5132;color:#fff;text-decoration:none;border-radius:6px;">
                      Confirmar E-mail
                    </a>
                  </p>
                  <p>Se você não solicitou este cadastro, ignore esta mensagem.</p>
                </div>
                """.formatted(confirmLink);

        ResendEmailRequest body = ResendEmailRequest.builder()
                .from(mailFrom)
                .to(to)
                .subject(subject)
                .html(html)
                .build();

        ResendEmailResponse resp = resendClient
                .post()
                .uri("/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(ResendEmailResponse.class);

        if (resp == null || resp.getId() == null || resp.getId().isBlank()) {
            throw new RuntimeException("Falha ao enviar e-mail de confirmação (Resend sem ID).");
        }
    }

    private String trimTrailingSlash(String url) {
        if (url == null) return "";
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    // ====== DTOs mínimos para a chamada do Resend ======

    public static final class ResendEmailRequest {
        private String from;
        private String to;
        private String subject;
        private String html;

        public ResendEmailRequest() {}

        private ResendEmailRequest(String from, String to, String subject, String html) {
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.html = html;
        }

        public static Builder builder() { return new Builder(); }
        public static final class Builder {
            private String from;
            private String to;
            private String subject;
            private String html;
            public Builder from(String v){ this.from = v; return this; }
            public Builder to(String v){ this.to = v; return this; }
            public Builder subject(String v){ this.subject = v; return this; }
            public Builder html(String v){ this.html = v; return this; }
            public ResendEmailRequest build(){ return new ResendEmailRequest(from, to, subject, html); }
        }

        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getHtml() { return html; }

        public void setFrom(String from) { this.from = from; }
        public void setTo(String to) { this.to = to; }
        public void setSubject(String subject) { this.subject = subject; }
        public void setHtml(String html) { this.html = html; }
    }

    public static final class ResendEmailResponse {
        // Resposta típica do Resend: { "id": "email_123...", "from": "...", ... }
        private String id;
        public ResendEmailResponse() {}
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
    }
}
