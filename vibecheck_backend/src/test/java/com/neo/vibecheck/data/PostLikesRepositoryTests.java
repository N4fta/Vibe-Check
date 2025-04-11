package com.neo.vibecheck.data;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.data.entity.LikeEntity;
import com.neo.vibecheck.data.entity.LikeId;
import com.neo.vibecheck.data.jpa.PostLikesRepositoryJpa;
import com.neo.vibecheck.domain.repositories.PostLikesRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PostLikesRepositoryTests {

    @Mock
    private PostLikesRepositoryJpa postLikesRepositoryJpa;

    @InjectMocks
    private PostLikesRepositoryImpl postLikesRepository;

    private LikeEntity likeEntity;

    @BeforeEach
    public void setUp() {
        likeEntity = new LikeEntity(new LikeId(1L, 1L));
    }

    @Test
    void testAdd() {
        postLikesRepository.add(1L, 1L);

        verify(postLikesRepositoryJpa, times(1)).save(new LikeEntity(new LikeId(1L, 1L)));
    }

    @Test
    void testRemove() {
        postLikesRepository.remove(1L, 1L);

        verify(postLikesRepositoryJpa, times(1)).delete(new LikeEntity(new LikeId(1L, 1L)));
    }
}