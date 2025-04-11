package com.neo.vibecheck.data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Repository;

import com.neo.vibecheck.data.entity.RefreshTokenEntity;
import com.neo.vibecheck.data.entity.UserEntity;
import com.neo.vibecheck.data.jpa.RefreshTokenRepositoryJpa;
import com.neo.vibecheck.data.jpa.UserRepositoryJpa;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;
    private final RefreshTokenRepositoryJpa refreshTokenRepositoryJpa;
    private final ModelMapper mapper;

    @Override
    public User findById(long userId) throws IllegalArgumentException {
        return mapper.map(userRepositoryJpa.findById(userId), User.class);
    }

    @Override
    public User findByUsername(String username) throws IllegalArgumentException {
        return mapper.map(userRepositoryJpa.findByUsername(username), User.class);
    }

    @Override
    public User save(User user) throws IllegalArgumentException {
        UserEntity userEntity = userRepositoryJpa.save(mapper.map(user, UserEntity.class));
        return mapper.map(userEntity, User.class);
    }

    @Override
    public List<User> findAll() throws IllegalArgumentException {
        return mapper.map(userRepositoryJpa.findAll(), new TypeToken<List<User>>() {
        }.getType());
    }

    @Override
    public void deleteById(long userId) {
        userRepositoryJpa.deleteById(userId);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepositoryJpa.deleteByUsername(username);
    }

    @Override
    public void saveRefreshToken(String refreshToken, Long userId, Instant expiresAt) {
        refreshTokenRepositoryJpa.save(
                RefreshTokenEntity.builder().userId(userId).token(refreshToken).createdAt(Timestamp.from(Instant.now()))
                        .expiresAt(Timestamp.from(expiresAt)).isRevoked(false).build());
    }

    @Override
    public Boolean checkForExistingRefreshToken(Long userId, String refreshToken) {
        return refreshTokenRepositoryJpa.existsByUserIdAndTokenAndExpiresAtAfter(userId, refreshToken,
                Timestamp.from(Instant.now()));
    }

    @Override
    public void revokeRefreshToken(long userId, String refreshToken) {
        refreshTokenRepositoryJpa.revokeRefreshToken(userId, refreshToken);
    }

}
