package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.MemberRegister;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService service;

    @GetMapping("/get")
    public List<Member> getMembers(){
        return service.getAllMembers();
    }

    @PostMapping("/post")
    public ResponseEntity<Member> memberRegister(@Valid @RequestBody MemberRegister memberRegister){
        Member member = memberRegister.toModel();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.memberRegister(member));
    }

}
