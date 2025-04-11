package com.neo.vibecheck.data;

import org.springframework.stereotype.Repository;

import com.neo.vibecheck.data.entity.LikeEntity;
import com.neo.vibecheck.data.entity.LikeId;
import com.neo.vibecheck.data.jpa.PostLikesRepositoryJpa;
import com.neo.vibecheck.domain.repositories.PostLikesRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
@Transactional
public class PostLikesRepositoryImpl implements PostLikesRepository {

    private final PostLikesRepositoryJpa postRepositoryJpa;

    @Override
    public void add(long postId, long userId) {
        LikeEntity likeEntity = new LikeEntity(new LikeId(postId, userId));
        postRepositoryJpa.save(likeEntity);
        return;
    }

    @Override
    public void remove(long postId, long userId) {
        LikeEntity likeEntity = new LikeEntity(new LikeId(postId, userId));
        postRepositoryJpa.delete(likeEntity);
        return;
    }

}
