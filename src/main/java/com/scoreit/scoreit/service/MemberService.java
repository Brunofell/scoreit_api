package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.member.MemberUpdate;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Member> getAllMembers(){ // add pageable
        return repository.findAll();
    }

    public Member memberRegister(Member member){
        if(repository.findByEmail(member.getEmail()) != null){
            throw new RuntimeException("This email is alredy in use.");
        }

        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        var savedMember =  repository.save(member);

        favoriteListService.createFavoriteList(savedMember.getId(), "Favorites", "List of "+ savedMember.getName() +"'s favorite media!"); // criando lista de favs já

        return savedMember;
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
