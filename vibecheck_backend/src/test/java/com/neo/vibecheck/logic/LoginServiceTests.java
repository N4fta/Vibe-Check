package com.neo.vibecheck.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.configuration.auth.token.AccessToken;
import com.neo.vibecheck.configuration.auth.token.AccessTokenEncoder;
import com.neo.vibecheck.domain.AccessCodes;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.exceptions.UserNotFoundException;
import com.neo.vibecheck.domain.repositories.UserRepository;
import com.neo.vibecheck.domain.requests.LoginUserRequest;
import com.neo.vibecheck.domain.services.LoginService;
import com.neo.vibecheck.logic.impl.LoginServiceImpl;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class LoginServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginServiceImpl loginService;

    private User exampleUser;

    @BeforeEach
    void init() {
        exampleUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedpassword")
                .build();
        loginService = new LoginServiceImpl(userRepository, passwordEncoder, accessTokenEncoder);
    }

    @Test
    void loginUser_ThrowsUserNotFoundException() {
        // Arrange
        LoginUserRequest request = new LoginUserRequest("nonexistentuser", "password");
        when(userRepository.findByUsername("nonexistentuser")).thenThrow(new IllegalArgumentException());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> loginService.loginUser(request));
        verify(userRepository).findByUsername("nonexistentuser");
    }

    @Test
    void refresh_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 2L;
        String token = "valid_refresh_token";
        when(userRepository.findById(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> loginService.refresh(userId, token));
        verify(userRepository).findById(userId);
    }

    @Test
    void refresh_ThrowsExceptionOnInvalidRefreshToken() {
        // Arrange
        Long userId = 1L;
        String token = "invalid_refresh_token";
        when(userRepository.findById(userId)).thenReturn(exampleUser);
        when(userRepository.checkForExistingRefreshToken(userId, token)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> loginService.refresh(userId, token)); // Change this to the correct
                                                                                         // exception type
        verify(userRepository).findById(userId);
        verify(userRepository).checkForExistingRefreshToken(userId, token);
    }

    @Test
    void revokeRefreshToken_ReturnsVoid() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        String token = "valid_refresh_token";
        when(userRepository.findById(userId)).thenReturn(exampleUser);
        when(userRepository.checkForExistingRefreshToken(userId, token)).thenReturn(true);

        // Act
        loginService.revokeRefreshToken(userId, token);

        // Assert
        verify(userRepository).findById(userId);
        verify(userRepository).checkForExistingRefreshToken(userId, token);
        verify(userRepository).revokeRefreshToken(userId, token);
    }

    @Test
    void revokeRefreshToken_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 2L;
        String token = "valid_refresh_token";
        when(userRepository.findById(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> loginService.revokeRefreshToken(userId, token));
        verify(userRepository).findById(userId);
    }

    @Test
    void revokeRefreshToken_ThrowsExceptionOnInvalidRefreshToken() {
        // Arrange
        Long userId = 1L;
        String token = "invalid_refresh_token";
        when(userRepository.findById(userId)).thenReturn(exampleUser);
        when(userRepository.checkForExistingRefreshToken(userId, token)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> loginService.revokeRefreshToken(userId, token)); // Change this to
                                                                                                    // the correct
                                                                                                    // exception type
        verify(userRepository).findById(userId);
        verify(userRepository).checkForExistingRefreshToken(userId, token);
    }
}