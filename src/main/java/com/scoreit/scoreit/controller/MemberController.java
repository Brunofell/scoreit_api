package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.AuthenticationRequest;
import com.scoreit.scoreit.dto.AuthenticationResponse;
import com.scoreit.scoreit.dto.MemberRegister;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.MemberService;
import com.scoreit.scoreit.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService service;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/get")
    public List<Member> getMembers(){
        return service.getAllMembers();
    }

    @PostMapping("/post")
    public ResponseEntity<Member> memberRegister(@Valid @RequestBody MemberRegister memberRegister){
        Member member = memberRegister.toModel();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.memberRegister(member));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest login){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                login.email(), login.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken( (Member) auth.getPrincipal());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

//    @PostMapping("/login")
//    public ResponseEntity login(AuthenticationRequest login){
//        return ResponseEntity.ok(service.login(login));
//    }

}
