package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.EmailResetToken;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.EmailResetTokenRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailResetService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailResetTokenRepository tokenRepository;

    @Autowired
    private NotificationEmailService notificationEmailService;

    /** Cria token e ENVIA o e-mail de confirmação de alteração. Rollback se envio falhar. */
    @Transactional
    public String createEmailResetToken(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        String token = UUID.randomUUID().toString();
        EmailResetToken resetToken = new EmailResetToken();
        resetToken.setToken(token);
        resetToken.setMember(member);
        resetToken.setExpireDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        try {
            notificationEmailService.sendEmailChangeOrThrow(member.getEmail(), token);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Não foi possível enviar o e-mail de alteração. Tente novamente.");
        }

        return token;
    }

    public boolean isTokenValid(String token) {
        Optional<EmailResetToken> tokenOptional = tokenRepository.findByToken(token);
        return tokenOptional.isPresent() && tokenOptional.get().getExpireDate().isAfter(LocalDateTime.now());
    }

    @Transactional
    public void updateEmail(String token, String newEmail) {
        EmailResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("INVALID TOKEN."));

        if (resetToken.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("EXPIRED TOKEN.");
        }

        // Verificar se o novo e-mail já está em uso
        if (memberRepository.findByEmail(newEmail) != null) {
            throw new IllegalArgumentException("The new email address is already in use.");
        }

        Member member = resetToken.getMember();
        member.setEmail(newEmail);
        memberRepository.save(member);
        tokenRepository.delete(resetToken);
    }
}
