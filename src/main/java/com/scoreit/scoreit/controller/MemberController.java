package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.badge.BadgeResponse;
import com.scoreit.scoreit.dto.member.MemberUpdate;
import com.scoreit.scoreit.dto.security.AuthenticationRequest;
import com.scoreit.scoreit.dto.security.AuthenticationResponse;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.BadgeService;
import com.scoreit.scoreit.service.FavoriteListContentService;
import com.scoreit.scoreit.service.FavoriteListService;
import com.scoreit.scoreit.service.MemberService;
import com.scoreit.scoreit.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService service;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FavoriteListContentService favoriteListContentService;

    @Autowired
    private FavoriteListService favoriteListService;

    // Removidos: VerificationTokenRepository e EmailConfirmationService (não usados aqui)

    @GetMapping("/get")
    public List<Member> getMembers() {
        return service.getAllMembers();
    }

    @GetMapping("/get/{id}")
    public Optional<Member> getMemberById(@PathVariable Long id) {
        return service.getMemberById(id);
    }

    @PostMapping("/post")
    public ResponseEntity<?> register(@RequestBody Member member) {
        try {
            System.out.println("[REGISTER] email=" + member.getEmail()); // DEBUG (não logar senha!)
            service.memberRegister(member);
            return ResponseEntity.ok("Cadastro realizado. Verifique seu e-mail.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam String token) {
        String result = service.confirmEmail(token);
        if ("E-mail confirmado com sucesso!".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest login) {
        try {
            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(login.email(), login.password());

            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Member) auth.getPrincipal());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas ou conta não confirmada.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Member> updateMember(@RequestBody @Valid MemberUpdate data) {
        return ResponseEntity.ok(service.updateMember(data));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    // ===== Favoritos =====

    @PostMapping("/favorites/{memberId}/{mediaId}/{mediaType}")
    public ResponseEntity<String> addContentToFavorites(
            @PathVariable Long memberId,
            @PathVariable String mediaId,
            @PathVariable String mediaType
    ) {
        try {
            favoriteListContentService.addContentInFavorites(memberId, mediaId, mediaType);
            return ResponseEntity.ok("Conteúdo adicionado aos favoritos!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/favoritesList/{memberId}")
    public ResponseEntity<?> getListByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(favoriteListService.getListByMemberId(memberId));
    }

    @GetMapping("/favoritesListContent/{memberId}")
    public List<FavoriteListContent> getFavoriteListContent(@PathVariable Long memberId) {
        return favoriteListContentService.getFavoriteListContent(memberId);
    }

    @DeleteMapping("/favoritesDelete/{memberId}/{mediaId}/{mediaType}")
    public ResponseEntity<String> removeContentFromFavorites(
            @PathVariable Long memberId,
            @PathVariable String mediaId,
            @PathVariable String mediaType
    ) {
        try {
            favoriteListContentService.removeContent(memberId, mediaId, mediaType);
            return ResponseEntity.ok("Conteúdo removido dos favoritos.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/is-favorited")
    public Map<String, Boolean> isFavorited(
            @RequestParam Long memberId,
            @RequestParam String mediaId
    ) {
        boolean favorited = favoriteListContentService.isContentFavorited(memberId, mediaId);
        return Map.of("favorited", favorited);
    }

    // ===== Badges =====

    @GetMapping("/{id}/badges")
    public List<BadgeResponse> getMemberBadges(@PathVariable Long id) {
        return badgeService.getBadgesByMemberId(id);
    }
}
