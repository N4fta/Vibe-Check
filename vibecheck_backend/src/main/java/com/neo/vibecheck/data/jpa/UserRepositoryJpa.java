package com.neo.vibecheck.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neo.vibecheck.data.entity.UserEntity;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);

    public void deleteByUsername(String username);
}
