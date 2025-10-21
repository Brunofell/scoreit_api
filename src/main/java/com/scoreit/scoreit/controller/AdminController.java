package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/created-on")
    public ResponseEntity<List<Member>> getMembersByCreationDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Member> members = memberService.getMembersByDate(date);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/created-between")
    public ResponseEntity<List<Member>> getMembersBetween(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<Member> members = memberService.getMembersByDateRange(start, end);
        return ResponseEntity.ok(members);
    }



}




