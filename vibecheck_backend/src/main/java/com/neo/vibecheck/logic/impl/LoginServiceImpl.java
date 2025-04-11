package com.neo.vibecheck.logic.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neo.vibecheck.configuration.auth.token.AccessToken;
import com.neo.vibecheck.configuration.auth.token.AccessTokenEncoder;
import com.neo.vibecheck.domain.AccessCodes;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.exceptions.UserNotFoundException;
import com.neo.vibecheck.domain.repositories.UserRepository;
import com.neo.vibecheck.domain.requests.LoginUserRequest;
import com.neo.vibecheck.domain.services.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AccessTokenEncoder accessTokenEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenEncoder = accessTokenEncoder;

    }

    @Override
    public AccessCodes loginUser(LoginUserRequest loginUserRequest) throws UserNotFoundException {
        User user;
        try {
            user = userRepository.findByUsername(loginUserRequest.getUsername());
        } catch (IllegalArgumentException e) {
            throw new UserNotFoundException();
        }
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) {
            // throw new PasswordMismatchException("Password did not match", null);
        }
        return AccessCodes.builder()
                .accessToken(generateAccessToken(user))
                .refreshToken(generateRefreshToken(user))
                .userId(user.getId())
                .build();

    }

    @Override
    public AccessCodes refresh(Long userId, String token) throws UserNotFoundException {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!userRepository.checkForExistingRefreshToken(user.getId(), token)) {
            throw new NotImplementedException();
        }
        userRepository.revokeRefreshToken(user.getId(), token);
        return AccessCodes.builder()
                .userId(user.getId())
                .accessToken(generateAccessToken(user))
                .refreshToken(generateRefreshToken(user))
                .build();
    }

    @Override
    public void revokeRefreshToken(Long userId, String token) throws UserNotFoundException {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!userRepository.checkForExistingRefreshToken(user.getId(), token)) {
            throw new NotImplementedException();
        }
        userRepository.revokeRefreshToken(user.getId(), token);
    }

    private String generateAccessToken(User user) {
        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .roles(Set.of("USER"))
                        .userId(user.getId())
                        .subject(user.getUsername())
                        .build());
    }

    private String generateRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        userRepository.saveRefreshToken(token, user.getId(), Instant.now().plus(30, ChronoUnit.DAYS));
        return token;
    }
}
