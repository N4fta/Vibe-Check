package com.neo.vibecheck.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.data.entity.RefreshTokenEntity;
import com.neo.vibecheck.data.entity.UserEntity;
import com.neo.vibecheck.data.jpa.RefreshTokenRepositoryJpa;
import com.neo.vibecheck.data.jpa.UserRepositoryJpa;
import com.neo.vibecheck.domain.User;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserRepositoryImplTests {

    @Mock
    private UserRepositoryJpa userRepositoryJpa;

    @Mock
    private RefreshTokenRepositoryJpa refreshTokenRepositoryJpa;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .email("testuser@example.com")
                .username("testuser")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .admin(false)
                .build();

        user = User.builder()
                .id(1L)
                .email("testuser@example.com")
                .username("testuser")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .admin(false)
                .build();
    }

    @Test
    void testFindById() {
        when(userRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(mapper.map(any(), eq(User.class))).thenReturn(user);

        User result = userRepository.findById(1L);

        assertEquals(user, result);
        verify(userRepositoryJpa, times(1)).findById(1L);
        verify(mapper, times(1)).map(any(), eq(User.class));
    }

    @Test
    void testFindByUsername() {
        when(userRepositoryJpa.findByUsername(anyString())).thenReturn(userEntity);
        when(mapper.map(any(UserEntity.class), eq(User.class))).thenReturn(user);

        User result = userRepository.findByUsername("testuser");

        assertEquals(user, result);
        verify(userRepositoryJpa, times(1)).findByUsername("testuser");
        verify(mapper, times(1)).map(any(UserEntity.class), eq(User.class));
    }

    @Test
    void testSave() {
        when(mapper.map(any(User.class), eq(UserEntity.class))).thenReturn(userEntity);
        when(userRepositoryJpa.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapper.map(any(UserEntity.class), eq(User.class))).thenReturn(user);

        User result = userRepository.save(user);

        assertEquals(user, result);
        verify(mapper, times(1)).map(any(User.class), eq(UserEntity.class));
        verify(userRepositoryJpa, times(1)).save(any(UserEntity.class));
        verify(mapper, times(1)).map(any(UserEntity.class), eq(User.class));
    }

    @Test
    void testFindAll() {
        List<UserEntity> userEntities = Arrays.asList(userEntity);

        when(userRepositoryJpa.findAll()).thenReturn(userEntities);
        when(mapper.map(anyList(), eq(new TypeToken<List<User>>() {
        }.getType()))).thenReturn(Arrays.asList(user));

        List<User> result = userRepository.findAll();

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(userRepositoryJpa, times(1)).findAll();
        verify(mapper, times(1)).map(anyList(), eq(new TypeToken<List<User>>() {
        }.getType()));
    }

    @Test
    void testDeleteById() {
        userRepository.deleteById(1L);

        verify(userRepositoryJpa, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByUsername() {
        userRepository.deleteByUsername("testuser");

        verify(userRepositoryJpa, times(1)).deleteByUsername("testuser");
    }

    @Test
    void testSaveRefreshToken() {
        String refreshToken = "refreshToken";
        Long userId = 1L;
        Instant expiresAt = Instant.now().plusSeconds(3600);

        userRepository.saveRefreshToken(refreshToken, userId, expiresAt);

        verify(refreshTokenRepositoryJpa, times(1)).save(any(RefreshTokenEntity.class));
    }

    @Test
    void testCheckForExistingRefreshToken() {
        Long userId = 1L;
        String refreshToken = "refreshToken";

        when(refreshTokenRepositoryJpa.existsByUserIdAndTokenAndExpiresAtAfter(eq(userId), eq(refreshToken),
                any(Timestamp.class)))
                .thenReturn(true);

        Boolean result = userRepository.checkForExistingRefreshToken(userId, refreshToken);

        assertTrue(result);
        verify(refreshTokenRepositoryJpa, times(1)).existsByUserIdAndTokenAndExpiresAtAfter(eq(userId),
                eq(refreshToken), any(Timestamp.class));
    }

    @Test
    void testCheckForExistingRefreshToken_False() {
        Long userId = 1L;
        String refreshToken = "refreshToken";

        when(refreshTokenRepositoryJpa.existsByUserIdAndTokenAndExpiresAtAfter(eq(userId), eq(refreshToken),
                any(Timestamp.class)))
                .thenReturn(false);

        Boolean result = userRepository.checkForExistingRefreshToken(userId, refreshToken);

        assertFalse(result);
        verify(refreshTokenRepositoryJpa, times(1)).existsByUserIdAndTokenAndExpiresAtAfter(eq(userId),
                eq(refreshToken), any(Timestamp.class));
    }

    @Test
    void testRevokeRefreshToken() {
        Long userId = 1L;
        String refreshToken = "refreshToken";

        userRepository.revokeRefreshToken(userId, refreshToken);

        verify(refreshTokenRepositoryJpa, times(1)).revokeRefreshToken(userId, refreshToken);
    }
}