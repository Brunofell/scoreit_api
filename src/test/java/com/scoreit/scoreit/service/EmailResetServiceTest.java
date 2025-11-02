package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.EmailResetToken;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.EmailResetTokenRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailResetServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EmailResetTokenRepository tokenRepository;

    @Mock
    private NotificationEmailService notificationEmailService;

    @InjectMocks
    private EmailResetService emailResetService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setId(1L);
        member.setEmail("user@example.com");
    }

    @Test
    void shouldCreateEmailResetTokenSuccessfully() {
        when(memberRepository.findByEmail("user@example.com")).thenReturn(member);

        String token = emailResetService.createEmailResetToken("user@example.com");

        assertNotNull(token);
        verify(tokenRepository).save(any(EmailResetToken.class));
        verify(notificationEmailService).sendEmailChangeOrThrow("user@example.com", token);
    }

    @Test
    void shouldThrowIfUserNotFoundOnCreateToken() {
        when(memberRepository.findByEmail("unknown@example.com")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> emailResetService.createEmailResetToken("unknown@example.com"));

        assertEquals("Usuário não encontrado.", ex.getMessage());
    }

    @Test
    void shouldThrowIfEmailSendFails() {
        when(memberRepository.findByEmail("user@example.com")).thenReturn(member);
        doThrow(new RuntimeException()).when(notificationEmailService)
                .sendEmailChangeOrThrow(eq("user@example.com"), anyString());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> emailResetService.createEmailResetToken("user@example.com"));

        assertEquals("Não foi possível enviar o e-mail de alteração. Tente novamente.", ex.getMessage());
    }

    @Test
    void shouldReturnTrueIfTokenValid() {
        EmailResetToken token = new EmailResetToken();
        token.setExpireDate(LocalDateTime.now().plusHours(1));

        when(tokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));

        assertTrue(emailResetService.isTokenValid("valid-token"));
    }

    @Test
    void shouldReturnFalseIfTokenExpiredOrNotFound() {
        EmailResetToken token = new EmailResetToken();
        token.setExpireDate(LocalDateTime.now().minusHours(1));

        when(tokenRepository.findByToken("expired-token")).thenReturn(Optional.of(token));
        when(tokenRepository.findByToken("notfound-token")).thenReturn(Optional.empty());

        assertFalse(emailResetService.isTokenValid("expired-token"));
        assertFalse(emailResetService.isTokenValid("notfound-token"));
    }

    @Test
    void shouldUpdateEmailSuccessfully() {
        EmailResetToken token = new EmailResetToken();
        token.setToken("token123");
        token.setExpireDate(LocalDateTime.now().plusHours(1));
        token.setMember(member);

        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));
        when(memberRepository.findByEmail("new@example.com")).thenReturn(null);

        emailResetService.updateEmail("token123", "new@example.com");

        assertEquals("new@example.com", member.getEmail());
        verify(memberRepository).save(member);
        verify(tokenRepository).delete(token);
    }

    @Test
    void shouldThrowIfTokenInvalidOrExpiredOrEmailExists() {
        // Token inválido
        when(tokenRepository.findByToken("invalid")).thenReturn(Optional.empty());
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> emailResetService.updateEmail("invalid", "new@example.com"));
        assertEquals("INVALID TOKEN.", ex1.getMessage());

        // Token expirado
        EmailResetToken expiredToken = new EmailResetToken();
        expiredToken.setToken("expired");
        expiredToken.setExpireDate(LocalDateTime.now().minusHours(1));
        expiredToken.setMember(member);
        when(tokenRepository.findByToken("expired")).thenReturn(Optional.of(expiredToken));
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                () -> emailResetService.updateEmail("expired", "new@example.com"));
        assertEquals("EXPIRED TOKEN.", ex2.getMessage());

        // Email já em uso
        EmailResetToken tokenValid = new EmailResetToken();
        tokenValid.setToken("valid");
        tokenValid.setExpireDate(LocalDateTime.now().plusHours(1));
        tokenValid.setMember(member);
        when(tokenRepository.findByToken("valid")).thenReturn(Optional.of(tokenValid));
        when(memberRepository.findByEmail("existing@example.com")).thenReturn(new Member());
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class,
                () -> emailResetService.updateEmail("valid", "existing@example.com"));
        assertEquals("The new email address is already in use.", ex3.getMessage());
    }
}
