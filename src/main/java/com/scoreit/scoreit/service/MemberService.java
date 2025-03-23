package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public List<Member> getAllMembers(){ // add pageable
        return repository.findAll();
    }

    public Member memberRegister(Member member){
        if(repository.findByEmail(member.getEmail()) != null){
            throw new RuntimeException("This email is alredy in use.");
        }

        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        member.setEnabled(false);

        return repository.save(member);
    }

    // login



}
