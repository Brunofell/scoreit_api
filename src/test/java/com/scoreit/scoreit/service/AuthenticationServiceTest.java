package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private MemberRepository repository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = new Member();
        member.setId(1L);
        member.setEmail("bruno@example.com");
        member.setPassword("123");
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        // Arrange
        when(repository.findByEmail("bruno@example.com")).thenReturn(member);

        // Act
        UserDetails userDetails = authenticationService.loadUserByUsername("bruno@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("bruno@example.com", userDetails.getUsername());
        assertEquals("123", userDetails.getPassword());
        verify(repository, times(1)).findByEmail("bruno@example.com");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(repository.findByEmail("unknown@example.com")).thenReturn(null);

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authenticationService.loadUserByUsername("unknown@example.com")
        );

        assertEquals("User not found: unknown@example.com", exception.getMessage());
        verify(repository, times(1)).findByEmail("unknown@example.com");
    }
}
