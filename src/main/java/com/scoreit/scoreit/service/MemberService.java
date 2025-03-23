package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class MemberService {

    @Autowired
    private MemberRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/get")
    public List<Member> getAllMembers(){ // add pageable
        return repository.findAll();
    }

}
