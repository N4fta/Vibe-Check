package com.neo.vibecheck.others;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.configuration.auth.AuthenticationRequestFilter;
import com.neo.vibecheck.configuration.auth.token.AccessToken;
import com.neo.vibecheck.configuration.auth.token.AccessTokenDecoder;
import com.neo.vibecheck.configuration.auth.token.exception.InvalidAccessTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;

@ActiveProfiles("test")
class AuthenticationRequestFilterTest {

    @Mock
    private AccessTokenDecoder accessTokenDecoder;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private AuthenticationRequestFilter authenticationRequestFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNoAuthorizationHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        authenticationRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testInvalidAuthorizationHeaderFormat() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "InvalidFormat");
        MockHttpServletResponse response = new MockHttpServletResponse();

        authenticationRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testValidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer validToken");
        MockHttpServletResponse response = new MockHttpServletResponse();

        AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.getSubject()).thenReturn("user");
        when(accessToken.getRoles()).thenReturn(Set.of("ROLE_USER"));
        when(accessTokenDecoder.decode("validToken")).thenReturn(accessToken);

        authenticationRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals("user", userDetails.getUsername());
    }

    @Test
    void testInvalidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalidToken");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(accessTokenDecoder.decode("invalidToken")).thenThrow(new InvalidAccessTokenException("Invalid token"));

        authenticationRequestFilter.doFilterInternal(request, response, chain);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    }
}