package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private MemberService memberService;


    // Desativar / reativar conta
    @PatchMapping("/members/{id}/toggle")
    public ResponseEntity<String> toggleMemberStatus(@PathVariable Long id) {
        memberService.toggleMemberStatus(id);
        return ResponseEntity.ok("Status atualizado com sucesso!");
    }

    // Promover / rebaixar usuário
    @PatchMapping("/members/{id}/role")
    public ResponseEntity<String> toggleMemberRole(@PathVariable Long id) {
        memberService.toggleMemberRole(id);
        return ResponseEntity.ok("Papel (role) atualizado com sucesso!");
    }
}




