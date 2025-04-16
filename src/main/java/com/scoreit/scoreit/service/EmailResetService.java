package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.EmailResetToken;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.PasswordResetToken;
import com.scoreit.scoreit.repository.EmailResetTokenRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailResetService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EmailResetTokenRepository tokenRepository;


    //Criar token para reset da senha
    public String createEmailResetToken(String email){
        Member member = (Member) memberRepository.findByEmail(email);

        String token = UUID.randomUUID().toString(); // cria token
        EmailResetToken resetToken = new EmailResetToken(); // instancia entidade do reset password
        resetToken.setToken(token); // seta o token
        resetToken.setMember(member); // seta o membro do token
        resetToken.setExpireDate(LocalDateTime.now().plusHours(1)); // seta o tempo de vida do token

        tokenRepository.save(resetToken); // salva o token
        return token; // retorna o token
    };

    public boolean isTokenValid(String token) {
        Optional<EmailResetToken> tokenOptional = tokenRepository.findByToken(token);
        return tokenOptional.isPresent() && tokenOptional.get().getExpireDate().isAfter(LocalDateTime.now());
    };

    public void updateEmail(String token, String newEmail){
        EmailResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("INVALID TOKEN."));

        if(resetToken.getExpireDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("EXPIRED TOKEN.");
        }
        Member member = resetToken.getMember();
        member.setEmail(newEmail);
        memberRepository.save(member);
        tokenRepository.delete(resetToken);
    };
}
