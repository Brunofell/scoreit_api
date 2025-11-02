package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.PasswordResetToken;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordResetServiceTest {

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private NotificationEmailService notificationEmailService;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePasswordResetTokenSuccess() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("user@test.com");

        when(memberRepository.findByEmail("user@test.com")).thenReturn(member);
        doNothing().when(notificationEmailService).sendPasswordResetOrThrow(anyString(), anyString());

        String token = passwordResetService.createPasswordResetToken("user@test.com");

        assertNotNull(token);
        verify(tokenRepository).save(any(PasswordResetToken.class));
        verify(notificationEmailService).sendPasswordResetOrThrow(eq("user@test.com"), eq(token));
    }

    @Test
    void testCreatePasswordResetTokenUserNotFound() {
        when(memberRepository.findByEmail("notfound@test.com")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.createPasswordResetToken("notfound@test.com"));

        assertEquals("Usuário não encontrado.", ex.getMessage());
    }

    @Test
    void testCreatePasswordResetTokenEmailFails() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("user@test.com");

        when(memberRepository.findByEmail("user@test.com")).thenReturn(member);
        doThrow(new RuntimeException("Failed")).when(notificationEmailService).sendPasswordResetOrThrow(anyString(), anyString());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.createPasswordResetToken("user@test.com"));

        assertEquals("Não foi possível enviar o e-mail de redefinição. Tente novamente.", ex.getMessage());
    }

    @Test
    void testIsTokenValid() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpireDate(LocalDateTime.now().plusMinutes(10));

        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));
        assertTrue(passwordResetService.isTokenValid("token123"));

        token.setExpireDate(LocalDateTime.now().minusMinutes(1));
        assertFalse(passwordResetService.isTokenValid("token123"));
    }

//    @Test
//    void testUpdatePasswordSuccess() {
//        Member member = new Member();
//        member.setId(1L);
//        PasswordResetToken token = new PasswordResetToken();
//        token.setToken("token123");
//        token.setExpireDate(LocalDateTime.now().plusMinutes(10));
//        token.setMember(member);
//
//        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));
//        doNothing().when(memberRepository).save(any(Member.class));
//        doNothing().when(tokenRepository).delete(token);
//
//        assertDoesNotThrow(() -> passwordResetService.updatePassword("token123", "newPassword"));
//        verify(memberRepository).save(member);
//        verify(tokenRepository).delete(token);
//        assertNotEquals("newPassword", member.getPassword()); // Senha deve ser criptografada
//    }

    @Test
    void testUpdatePasswordInvalidToken() {
        when(tokenRepository.findByToken("invalid")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.updatePassword("invalid", "newPassword"));

        assertEquals("INVALID TOKEN.", ex.getMessage());
    }

    @Test
    void testUpdatePasswordExpiredToken() {
        Member member = new Member();
        PasswordResetToken token = new PasswordResetToken();
        token.setExpireDate(LocalDateTime.now().minusMinutes(1));
        token.setMember(member);

        when(tokenRepository.findByToken("tokenExpired")).thenReturn(Optional.of(token));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.updatePassword("tokenExpired", "newPassword"));

        assertEquals("EXPIRED TOKEN.", ex.getMessage());
    }
}
