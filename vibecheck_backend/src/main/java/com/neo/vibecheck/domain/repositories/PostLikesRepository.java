package com.neo.vibecheck.domain.repositories;

public interface PostLikesRepository {
    void add(long postId, long userId);

    void remove(long postId, long userId);
}