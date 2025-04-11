package com.neo.vibecheck.domain.services;

import com.neo.vibecheck.domain.AccessCodes;
import com.neo.vibecheck.domain.exceptions.UserNotFoundException;
import com.neo.vibecheck.domain.requests.LoginUserRequest;

public interface LoginService {
    AccessCodes loginUser(LoginUserRequest loginUserRequest) throws UserNotFoundException;

    AccessCodes refresh(Long userId, String token) throws UserNotFoundException;

    void revokeRefreshToken(Long userId, String token) throws UserNotFoundException;
}
