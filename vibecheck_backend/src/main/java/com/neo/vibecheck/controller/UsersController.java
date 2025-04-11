package com.neo.vibecheck.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neo.vibecheck.configuration.auth.token.AccessToken;
import com.neo.vibecheck.domain.AccessCodes;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.exceptions.UserNotFoundException;
import com.neo.vibecheck.domain.requests.CreateUserRequest;
import com.neo.vibecheck.domain.requests.LoginUserRequest;
import com.neo.vibecheck.domain.requests.RefreshTokenRequest;
import com.neo.vibecheck.domain.requests.UpdateUserRequest;
import com.neo.vibecheck.domain.services.LoginService;
import com.neo.vibecheck.domain.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final LoginService loginService;
    private final AccessToken accessToken;

    public UsersController(UserService userService, LoginService loginService, AccessToken accessToken) {
        this.userService = userService;
        this.loginService = loginService;
        this.accessToken = accessToken;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "username") Optional<String> username) {
        List<User> users = new ArrayList<User>();
        // If no username is provided, return all users
        if (username.isEmpty() || username.get().isBlank()) {
            users = userService.getUsers();
            for (User user : users) {
                user.setPassword("");
            }
            return ResponseEntity.ok(users);
        }
        final Optional<User> userOptional = Optional.of(userService.getUser(username.get()));
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userOptional.get().setPassword("");
        users.add(userOptional.get());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/validate")
    public ResponseEntity<AccessCodes> validateUser(@RequestBody @Valid LoginUserRequest loginUserRequest)
            throws UserNotFoundException {
        AccessCodes accessCodes = loginService.loginUser(loginUserRequest);

        return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + accessCodes.getAccessToken())
                .body(accessCodes);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessCodes> refresh(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest)
            throws UserNotFoundException {
        Long userId = refreshTokenRequest.getUserId();
        if (refreshTokenRequest.getRefreshToken() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (userId == null || userId < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        AccessCodes newTokens = loginService.refresh(userId, refreshTokenRequest.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + newTokens.getAccessToken())
                .body(newTokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest)
            throws UserNotFoundException {
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Long userId = accessToken.getUserId();
        if (refreshTokenRequest.getRefreshToken() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (userId == null || userId < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        loginService.revokeRefreshToken(userId, refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("userId") long userId) {
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (userId != accessToken.getUserId()) {
            return ResponseEntity.badRequest().build();
        }
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User newUser = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (updateUserRequest.getId() != accessToken.getUserId()) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(updateUserRequest);
        return ResponseEntity.noContent().build();
    }
}
