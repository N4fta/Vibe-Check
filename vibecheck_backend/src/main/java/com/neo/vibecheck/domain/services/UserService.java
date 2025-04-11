package com.neo.vibecheck.domain.services;

import java.util.List;

import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.requests.CreateUserRequest;
import com.neo.vibecheck.domain.requests.UpdateUserRequest;

public interface UserService {
    List<User> getUsers();

    User getUser(long userId);

    User getUser(String username);

    User createUser(CreateUserRequest user);

    void updateUser(UpdateUserRequest user);

    void deleteUser(long userId);

    void deleteUser(String username);
}
