package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.member.MemberUpdate;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.VerificationToken;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private FavoriteListService favoriteListService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailConfirmationService emailConfirmationService;


    public List<Member> getAllMembers(){ // add pageable
        return repository.findAll();
    }

    public Member memberRegister(Member member) {
        if (repository.findByEmail(member.getEmail()) != null) {
            throw new RuntimeException("This email is already in use.");
        }

        // Criptografa a senha com BCrypt
        member.setPassword(encoder.encode(member.getPassword()));

        member.setEnabled(false);
        Member savedMember = repository.save(member);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedMember);
        verificationTokenRepository.save(verificationToken);

        emailConfirmationService.sendVerificationEmail(member.getEmail(), token);

        return savedMember;
    }


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



    public Member updateMember(MemberUpdate data){
        String encodedPassword = null;
        if(data.password()!= null){
            encodedPassword = encoder.encode(data.password());
        }
        var member = repository.getReferenceById(data.id());

        member.updateMember(data);

        if(encodedPassword != null){
            member.setPassword(encodedPassword);
        }

        return repository.save(member);
    }

    public ResponseEntity deleteUser(Long id) {
        var member = repository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found."));

        member.deleteMember();
        repository.save(member);

        return ResponseEntity.ok("User Diseabled.");
    }

    public Optional<Member> getMemberById(Long id){
        return repository.findById(id);
    }

    public List<Member> searchMembersByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
