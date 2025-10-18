package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.member.MemberUpdate;
import com.scoreit.scoreit.dto.member.Role;
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

    @Transactional
    public Member updateMember(MemberUpdate data) {
        // Busca o membro que será atualizado
        var member = repository.getReferenceById(data.id());

        // Normaliza o handle
        String newHandle = (data.handle() != null) ? data.handle().trim().toLowerCase() : null;

        if (newHandle != null && !newHandle.isBlank()) {
            // Checa se já existe outro membro com o mesmo handle
            Member existing = repository.findByHandle(newHandle);
            if (existing != null && !existing.getId().equals(member.getId())) {
                throw new IllegalArgumentException("Esse @ já está sendo utilizado.");
            }
            member.setHandle(newHandle);
        }

        // Atualiza outros campos
        if (data.name() != null) member.setName(data.name());
        if (data.bio() != null) member.setBio(data.bio());
        if (data.birthDate() != null) member.setBirthDate(data.birthDate());
        if (data.gender() != null) member.setGender(data.gender());

        // Atualiza a senha se houver
        if (data.password() != null) {
            member.setPassword(encoder.encode(data.password()));
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


    /// ADMIN

    // Desativar / reativar conta
    public void toggleMemberStatus(Long id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        member.setEnabled(!member.isEnabled());
        repository.save(member);
    }

    // Promover / rebaixar usuário
    public void toggleMemberRole(Long id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (member.getRole() == Role.ROLE_ADMIN) {
            member.setRole(Role.ROLE_USER);
        } else {
            member.setRole(Role.ROLE_ADMIN);
        }

        repository.save(member);
    }
}
