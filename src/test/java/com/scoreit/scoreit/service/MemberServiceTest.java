package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.member.Gender;
import com.scoreit.scoreit.dto.member.MemberUpdate;
import com.scoreit.scoreit.dto.member.Role;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.VerificationToken;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MemberServiceTest {


    @Mock
    private MemberRepository repository;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private NotificationEmailService notificationEmailService;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldRegisterMemberSuccessfully() {
        // Arrange (dados de entrada)
        Member newMember = new Member();
        newMember.setEmail("bruno@example.com");
        newMember.setPassword("123456");
        newMember.setHandle("@bruno");

        // Mocks de comportamento
        when(repository.findByEmail("bruno@example.com")).thenReturn(null);
        when(repository.findByHandle("@bruno")).thenReturn(null);
        when(encoder.encode("123456")).thenReturn("encoded_pass");
        when(repository.save(any(Member.class))).thenAnswer(invocation -> {
            Member saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // Act (executa o método)
        Member saved = memberService.memberRegister(newMember);

        // Assert (verifica o resultado)
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals("encoded_pass", saved.getPassword());
        assertFalse(saved.isEnabled());

        // Verifica interações
        verify(repository).findByEmail("bruno@example.com");
        verify(repository).save(any(Member.class));
        verify(verificationTokenRepository).save(any(VerificationToken.class));
        verify(notificationEmailService).sendAccountVerificationOrThrow(eq("bruno@example.com"), anyString());
    }


    @Test
    void shouldThrowWhenEmailIsInvalid() {
        Member m = new Member();
        m.setEmail(" ");
        assertThrows(IllegalArgumentException.class, () -> memberService.memberRegister(m));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        Member m = new Member();
        m.setEmail("bruno@example.com");
        when(repository.findByEmail("bruno@example.com")).thenReturn(new Member());
        assertThrows(IllegalArgumentException.class, () -> memberService.memberRegister(m));
    }

    @Test
    void shouldThrowWhenHandleAlreadyExists() {
        Member m = new Member();
        m.setEmail("bruno@example.com");
        m.setHandle("@b");
        when(repository.findByEmail(any())).thenReturn(null);
        when(repository.findByHandle("@b")).thenReturn(new Member());
        assertThrows(IllegalArgumentException.class, () -> memberService.memberRegister(m));
    }

    @Test
    void shouldThrowWhenEmailSendingFails() {
        Member m = new Member();
        m.setEmail("bruno@example.com");
        m.setPassword("123");
        m.setHandle("@b");

        when(repository.findByEmail(any())).thenReturn(null);
        when(repository.findByHandle(any())).thenReturn(null);
        when(encoder.encode("123")).thenReturn("encoded");
        when(repository.save(any())).thenReturn(m);
        doThrow(new RuntimeException("fail")).when(notificationEmailService)
                .sendAccountVerificationOrThrow(any(), any());

        assertThrows(IllegalArgumentException.class, () -> memberService.memberRegister(m));
    }

    // ------------------- confirmEmail -------------------

    @Test
    void shouldConfirmEmailSuccessfully() {
        Member member = new Member();
        member.setEnabled(false);
        VerificationToken token = new VerificationToken("abc", member);
        token.setExpiryDate(LocalDateTime.now().plusHours(1));

        when(verificationTokenRepository.findByToken("abc")).thenReturn(Optional.of(token));

        String result = memberService.confirmEmail("abc");

        assertEquals("E-mail confirmado com sucesso!", result);
        assertTrue(member.isEnabled());
        verify(repository).save(member);
        verify(verificationTokenRepository).delete(token);
    }

    @Test
    void shouldReturnInvalidTokenMessage() {
        when(verificationTokenRepository.findByToken("abc")).thenReturn(Optional.empty());
        assertEquals("Token inválido.", memberService.confirmEmail("abc"));
    }

    @Test
    void shouldReturnExpiredTokenMessage() {
        Member member = new Member();
        VerificationToken token = new VerificationToken("abc", member);
        token.setExpiryDate(LocalDateTime.now().minusHours(1));
        when(verificationTokenRepository.findByToken("abc")).thenReturn(Optional.of(token));
        assertEquals("Token expirado.", memberService.confirmEmail("abc"));
    }

    // ------------------- updateMember -------------------


    @Test
    void shouldUpdateMemberSuccessfully() {
        // Arrange
        Member m = new Member();
        m.setId(1L);
        m.setHandle("@old");
        m.setPassword("oldpass");

        when(repository.getReferenceById(1L)).thenReturn(m);
        when(repository.findByHandle("@new")).thenReturn(null);
        when(encoder.encode("123")).thenReturn("enc");
        when(repository.save(any(Member.class))).thenReturn(m);

        MemberUpdate update = new MemberUpdate(1L, "Bruno", "@new", LocalDate.of(2000, 1, 1), Gender.MASC, "123", "enc");

        // Act
        Member result = memberService.updateMember(update);

        // Assert
        assertEquals("@new", result.getHandle());
        assertEquals("enc", result.getPassword());
        assertEquals("Bruno", result.getName());
        verify(repository).save(m);
    }

    @Test
    void shouldThrowIfHandleAlreadyExists() {
        Member m = new Member();
        m.setId(1L);
        when(repository.getReferenceById(1L)).thenReturn(m);
        Member existing = new Member();
        existing.setId(2L);
        when(repository.findByHandle("@new")).thenReturn(existing);

        MemberUpdate update = new MemberUpdate(1L, null, "@new", null, null, null, null);

        assertThrows(IllegalArgumentException.class, () -> memberService.updateMember(update));
    }

    // ------------------- deleteUser -------------------

    @Test
    void shouldDeleteUserSuccessfully() {
        Member m = spy(new Member());
        m.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(m));

        ResponseEntity<String> res = memberService.deleteUser(1L);

        assertEquals("User Disabled.", res.getBody());
        verify(m).deleteMember();
        verify(repository).save(m);
    }

    // ------------------- getMemberById -------------------

    @Test
    void shouldReturnMemberById() {
        Member m = new Member();
        when(repository.findById(1L)).thenReturn(Optional.of(m));
        Optional<Member> result = memberService.getMemberById(1L);
        assertTrue(result.isPresent());
    }

    // ------------------- searchMembersByHandle -------------------

    @Test
    void shouldSearchMembersByHandle() {
        List<Member> list = List.of(new Member());
        when(repository.findByHandleContainingIgnoreCase("bru")).thenReturn(list);
        List<Member> result = memberService.searchMembersByHandle("bru");
        assertEquals(1, result.size());
    }

    // ------------------- toggleMemberStatus -------------------

    @Test
    void shouldToggleMemberStatus() {
        Member m = new Member();
        m.setEnabled(false);
        when(repository.findById(1L)).thenReturn(Optional.of(m));
        memberService.toggleMemberStatus(1L);
        assertTrue(m.isEnabled());
        verify(repository).save(m);
    }

    // ------------------- toggleMemberRole -------------------

    @Test
    void shouldToggleMemberRole() {
        Member m = new Member();
        m.setRole(Role.ROLE_USER);
        when(repository.findById(1L)).thenReturn(Optional.of(m));

        memberService.toggleMemberRole(1L);

        assertEquals(Role.ROLE_ADMIN, m.getRole());
        verify(repository).save(m);
    }

    // ------------------- listAllMembers -------------------

    @Test
    void shouldListAllMembers() {
        Page<Member> page = new PageImpl<>(List.of(new Member()));
        when(repository.searchMembers(eq("bru"), any(Pageable.class))).thenReturn(page);
        Page<Member> result = memberService.listAllMembers(0, 10, "bru");
        assertEquals(1, result.getTotalElements());
    }

    // ------------------- getMemberByIdOrThrow -------------------

    @Test
    void shouldGetMemberByIdOrThrow() {
        Member m = new Member();
        when(repository.findById(1L)).thenReturn(Optional.of(m));
        assertNotNull(memberService.getMemberByIdOrThrow(1L));
    }

    @Test
    void shouldThrowIfMemberNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> memberService.getMemberByIdOrThrow(1L));
    }

    // ------------------- updateMemberAdmin -------------------

    @Test
    void shouldUpdateMemberAdmin() {
        Member m = new Member();
        when(repository.findById(1L)).thenReturn(Optional.of(m));
        when(repository.save(any())).thenReturn(m);

        Member result = memberService.updateMemberAdmin(1L, "Bruno", "b@b.com", true);

        assertEquals("bruno", result.getName().toLowerCase());
        assertTrue(result.isEnabled());
        verify(repository).save(m);
    }

    // ------------------- getMembersByDate -------------------

    @Test
    void shouldGetMembersByDate() {
        List<Member> list = List.of(new Member());
        when(repository.findMembersByCreationDate(any(), any())).thenReturn(list);
        List<Member> result = memberService.getMembersByDate(LocalDate.now());
        assertEquals(1, result.size());
    }

    // ------------------- getMembersByDateRange -------------------

    @Test
    void shouldGetMembersByDateRange() {
        List<Member> list = List.of(new Member());
        when(repository.findMembersByCreationDate(any(), any())).thenReturn(list);
        List<Member> result = memberService.getMembersByDateRange(LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(1, result.size());
    }


}
