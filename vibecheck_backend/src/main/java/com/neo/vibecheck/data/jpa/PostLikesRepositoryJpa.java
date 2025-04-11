package com.neo.vibecheck.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neo.vibecheck.data.entity.LikeEntity;
import com.neo.vibecheck.data.entity.LikeId;

public interface PostLikesRepositoryJpa extends JpaRepository<LikeEntity, LikeId> {

}
