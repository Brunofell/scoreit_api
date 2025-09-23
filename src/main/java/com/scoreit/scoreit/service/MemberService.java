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
    private NotificationEmailService notificationEmailService;

    public List<Member> getAllMembers() {
        return repository.findAll();
    }

    @Transactional
    public Member memberRegister(Member member) {
        String email = (member.getEmail() == null) ? null : member.getEmail().trim().toLowerCase();
        String handle = (member.getHandle() == null) ? null : member.getHandle().trim().toLowerCase();
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        if (repository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Este e-mail já está sendo usado.");
        }
        if(repository.findByHandle(handle) != null){
            throw new IllegalArgumentException("Esse @ já está sendo utilizado.");
        }
        member.setEmail(email);
        member.setPassword(encoder.encode(member.getPassword()));
        member.setEnabled(false);
        Member savedMember = repository.save(member);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedMember);
        verificationTokenRepository.save(verificationToken);
        try {
            notificationEmailService.sendAccountVerificationOrThrow(email, token);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Não foi possível enviar o e-mail de confirmação. Tente novamente.");
        }
        return savedMember;
    }

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

    public List<Member> searchMembersByHandle(String handle) {
        return repository.findByHandleContainingIgnoreCase(handle);
    }
}
