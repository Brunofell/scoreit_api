package com.scoreit.scoreit.dto;


import com.scoreit.scoreit.dto.security.AuthenticationRequest;
import com.scoreit.scoreit.dto.security.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationDtoTest {

    @Test
    void shouldStoreAuthenticationRequestValues() {
        AuthenticationRequest request = new AuthenticationRequest("user@example.com", "senha123");

        assertEquals("user@example.com", request.email());
        assertEquals("senha123", request.password());
    }

    @Test
    void shouldStoreAuthenticationResponseValue() {
        AuthenticationResponse response = new AuthenticationResponse("jwt-token-123");

        assertEquals("jwt-token-123", response.token());
    }

    @Test
    void authenticationRequestEqualsAndHashCode() {
        AuthenticationRequest req1 = new AuthenticationRequest("a@b.com", "123");
        AuthenticationRequest req2 = new AuthenticationRequest("a@b.com", "123");
        AuthenticationRequest req3 = new AuthenticationRequest("x@y.com", "456");

        assertEquals(req1, req2);
        assertNotEquals(req1, req3);
    }

    @Test
    void authenticationResponseEqualsAndHashCode() {
        AuthenticationResponse res1 = new AuthenticationResponse("token1");
        AuthenticationResponse res2 = new AuthenticationResponse("token1");
        AuthenticationResponse res3 = new AuthenticationResponse("token2");

        assertEquals(res1, res2);
        assertNotEquals(res1, res3);
    }
}
