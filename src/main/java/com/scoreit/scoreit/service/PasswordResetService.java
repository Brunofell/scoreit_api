package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.PasswordResetToken;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NotificationEmailService notificationEmailService;

    /** Cria token e ENVIA o e-mail de redefinição via Resend. Rollback se envio falhar. */
    @Transactional
    public String createPasswordResetToken(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            // Por segurança, não revele se o e-mail existe; você pode optar por retornar OK silencioso.
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setMember(member);
        resetToken.setExpireDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        try {
            notificationEmailService.sendPasswordResetOrThrow(member.getEmail(), token);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Não foi possível enviar o e-mail de redefinição. Tente novamente.");
        }

        return token;
    }

    public boolean isTokenValid(String token) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
        return tokenOptional.isPresent() && tokenOptional.get().getExpireDate().isAfter(LocalDateTime.now());
    }

    @Transactional
    public void updatePassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("INVALID TOKEN."));

        if (resetToken.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("EXPIRED TOKEN.");
        }

        Member member = resetToken.getMember();
        member.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        memberRepository.save(member);
        tokenRepository.delete(resetToken);
    }
}
