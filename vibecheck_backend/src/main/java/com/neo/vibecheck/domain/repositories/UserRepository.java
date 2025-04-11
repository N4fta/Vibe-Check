package com.neo.vibecheck.domain.repositories;

import java.time.Instant;
import java.util.List;

import com.neo.vibecheck.domain.User;

public interface UserRepository {

    User findById(long userId);

    User findByUsername(String username);

    User save(User user); // Returns a new ID if user is created

    List<User> findAll();

    void deleteById(long userId);

    void deleteByUsername(String username);

    void saveRefreshToken(String refreshToken, Long userId, Instant expiresAt);

    Boolean checkForExistingRefreshToken(Long userId, String refreshToken);

    void revokeRefreshToken(long userId, String refreshToken);

}
