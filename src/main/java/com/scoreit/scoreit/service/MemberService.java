package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.member.MemberUpdate;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.VerificationToken;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailConfirmationService emailConfirmationService;

    public List<Member> getAllMembers() { // TODO: adicionar Pageable quando necessário
        return repository.findAll();
    }

    /**
     * Registra o usuário e envia o e-mail de verificação via Resend.
     * Se o envio de e-mail falhar, a transação é revertida e o cadastro NÃO é concluído.
     */
    @Transactional
    public Member memberRegister(Member member) {
        if (repository.findByEmail(member.getEmail()) != null) {
            throw new IllegalArgumentException("Este e-mail já está sendo usado.");
        }

        // Criptografa a senha
        member.setPassword(encoder.encode(member.getPassword()));

        // O usuário só será habilitado após confirmar o e-mail
        member.setEnabled(false);
        Member savedMember = repository.save(member);

        // Gera e persiste o token de verificação
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedMember);
        verificationTokenRepository.save(verificationToken);

        // Envia o e-mail de verificação. Se der erro, aborta a transação.
        try {
            emailConfirmationService.sendVerificationEmailOrThrow(member.getEmail(), token);
        } catch (RuntimeException ex) {
            // Qualquer falha no envio invalida o cadastro
            // A @Transactional garante rollback do Member e do Token
            throw new IllegalArgumentException("Não foi possível enviar o e-mail de confirmação. Tente novamente.");
        }

        return savedMember;
    }

    /**
     * Confirma o e-mail com o token recebido.
     * Habilita o usuário e remove o token se tudo estiver ok.
     */
    @Transactional
    public String confirmEmail(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            return "Token inválido.";
        }

        VerificationToken verificationToken = optionalToken.get();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token expirado.";
        }

        Member member = verificationToken.getMember();
        member.setEnabled(true);
        repository.save(member);

        verificationTokenRepository.delete(verificationToken);

        return "E-mail confirmado com sucesso!";
    }

    public Member updateMember(MemberUpdate data) {
        String encodedPassword = null;
        if (data.password() != null) {
            encodedPassword = encoder.encode(data.password());
        }
        var member = repository.getReferenceById(data.id());

        member.updateMember(data);

        if (encodedPassword != null) {
            member.setPassword(encodedPassword);
        }

        return repository.save(member);
    }

    public ResponseEntity<String> deleteUser(Long id) {
        var member = repository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found."));
        member.deleteMember();
        repository.save(member);
        return ResponseEntity.ok("User Disabled.");
    }

    public Optional<Member> getMemberById(Long id) {
        return repository.findById(id);
    }

    public List<Member> searchMembersByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
