package com.scoreit.scoreit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Serviço centralizado para e-mails transacionais via Resend.
 * Exponho 3 métodos:
 *  - sendAccountVerificationOrThrow(to, token)
 *  - sendPasswordResetOrThrow(to, token)
 *  - sendEmailChangeOrThrow(to, token)
 */
@Service
public class NotificationEmailService {

    private final RestClient resendClient;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${app.frontend.default-locale:pt}")
    private String defaultLocale;

    // paths opcionais (customizáveis por env/properties)
    @Value("${app.frontend.verification-path:#{null}}")
    private String verificationPath; // ex: /pt/confirma_conta
    @Value("${app.frontend.reset-password-path:#{null}}")
    private String resetPasswordPath; // ex: /pt/nova_senha
    @Value("${app.frontend.reset-email-path:#{null}}")
    private String resetEmailPath; // ex: /pt/novo_email

    @Value("${mail.from}")
    private String mailFrom;

    public NotificationEmailService(RestClient resendClient) {
        this.resendClient = resendClient;
    }

    /* ========= Confirmação de conta ========= */
    public void sendAccountVerificationOrThrow(String to, String token) {
        final String subject = "Confirmação de E-mail - ScoreIt";
        final String path = nonBlank(verificationPath) ? verificationPath : ("/" + defaultLocale + "/confirma_conta");
        final String link = buildLink(path, token);

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
                """.formatted(link);

        sendResendEmailOrThrow(to, subject, html);
    }

    /* ========= Redefinição de senha ========= */
    public void sendPasswordResetOrThrow(String to, String token) {
        final String subject = "Redefinição de Senha - ScoreIt";
        final String path = nonBlank(resetPasswordPath) ? resetPasswordPath : ("/" + defaultLocale + "/nova_senha");
        final String link = buildLink(path, token);

        final String html = """
                <div style="font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;">
                  <p>Recebemos um pedido para redefinir sua senha no ScoreIt.</p>
                  <p>
                    <a href="%s" style="display:inline-block;padding:12px 18px;background:#0f5132;color:#fff;text-decoration:none;border-radius:6px;">
                      Redefinir Senha
                    </a>
                  </p>
                  <p>Se você não solicitou, ignore este e-mail.</p>
                </div>
                """.formatted(link);

        sendResendEmailOrThrow(to, subject, html);
    }

    /* ========= Alteração de e-mail ========= */
    public void sendEmailChangeOrThrow(String to, String token) {
        final String subject = "Confirmação de Alteração de E-mail - ScoreIt";
        final String path = nonBlank(resetEmailPath) ? resetEmailPath : ("/" + defaultLocale + "/novo_email");
        final String link = buildLink(path, token);

        final String html = """
                <div style="font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;">
                  <p>Para confirmar a alteração do seu e-mail no ScoreIt, clique abaixo:</p>
                  <p>
                    <a href="%s" style="display:inline-block;padding:12px 18px;background:#0f5132;color:#fff;text-decoration:none;border-radius:6px;">
                      Confirmar novo e-mail
                    </a>
                  </p>
                  <p>Se você não solicitou, ignore esta mensagem.</p>
                </div>
                """.formatted(link);

        sendResendEmailOrThrow(to, subject, html);
    }

    /* ========= Helpers ========= */
    private String buildLink(String path, String token) {
        final String base = trimTrailingSlash(frontendBaseUrl);
        final String encoded = URLEncoder.encode(token, StandardCharsets.UTF_8);
        return base + path + "?token=" + encoded;
    }

    private void sendResendEmailOrThrow(String to, String subject, String html) {
        ResendEmailRequest body = ResendEmailRequest.builder()
                .from(mailFrom)
                .to(to)
                .subject(subject)
                .html(html)
                .build();

        ResendEmailResponse resp = resendClient.post()
                .uri("/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(ResendEmailResponse.class);

        if (resp == null || resp.getId() == null || resp.getId().isBlank()) {
            throw new RuntimeException("Falha ao enviar e-mail (Resend sem ID).");
        }
    }

    private static String trimTrailingSlash(String url) {
        if (url == null) return "";
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
    private static boolean nonBlank(String s) { return s != null && !s.isBlank(); }

    /* ===== DTOs mínimos ===== */
    public static final class ResendEmailRequest {
        private String from;
        private String to;
        private String subject;
        private String html;
        public ResendEmailRequest() {}
        private ResendEmailRequest(String from, String to, String subject, String html) {
            this.from = from; this.to = to; this.subject = subject; this.html = html;
        }
        public static Builder builder() { return new Builder(); }
        public static final class Builder {
            private String from, to, subject, html;
            public Builder from(String v){ this.from=v; return this; }
            public Builder to(String v){ this.to=v; return this; }
            public Builder subject(String v){ this.subject=v; return this; }
            public Builder html(String v){ this.html=v; return this; }
            public ResendEmailRequest build(){ return new ResendEmailRequest(from,to,subject,html); }
        }
        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getHtml() { return html; }
        public void setFrom(String v){ this.from=v; }
        public void setTo(String v){ this.to=v; }
        public void setSubject(String v){ this.subject=v; }
        public void setHtml(String v){ this.html=v; }
    }

    public static final class ResendEmailResponse {
        private String id;
        public ResendEmailResponse() {}
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
    }
}
