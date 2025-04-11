package com.neo.vibecheck.logic.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.repositories.UserRepository;
import com.neo.vibecheck.domain.requests.CreateUserRequest;
import com.neo.vibecheck.domain.requests.UpdateUserRequest;
import com.neo.vibecheck.domain.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream().toList();
    }

    @Override
    public User getUser(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        User user = User.builder()
                .email(createUserRequest.getEmail())
                .username(createUserRequest.getUsername())
                .password(encodedPassword)
                .birthDate(createUserRequest.getBirthDate())
                .build();
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(updateUserRequest.getId()); // Base user info.

        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getUsername() != null) {
            user.setUsername(updateUserRequest.getUsername());
        }
        if (updateUserRequest.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
            user.setPassword(encodedPassword);
        }
        if (updateUserRequest.getBirthDate() != null) {
            user.setBirthDate(updateUserRequest.getBirthDate());
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
}
