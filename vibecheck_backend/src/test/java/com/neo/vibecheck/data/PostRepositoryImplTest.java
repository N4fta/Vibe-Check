package com.neo.vibecheck.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.data.entity.PostEntity;
import com.neo.vibecheck.data.entity.UserEntity;
import com.neo.vibecheck.data.jpa.PostRepositoryJpa;
import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.requests.PostPage;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PostRepositoryImplTest {

        @Mock
        private PostRepositoryJpa postRepositoryJpa;

        @Mock
        private ModelMapper mapper;

        @InjectMocks
        private PostRepositoryImpl postRepository;

        private PostEntity postEntity;
        private Post post;
        private UserEntity userEntity;

        @BeforeEach
        public void setUp() {
                userEntity = UserEntity.builder().id(1L).build();

                postEntity = PostEntity.builder()
                                .id(1L)
                                .owner(userEntity)
                                .title("Test Title")
                                .body("Test Content")
                                .build();

                post = Post.builder()
                                .id(1L)
                                .owner(User.builder()
                                                .id(1L).build())
                                .title("Test Title")
                                .body("Test Content")
                                .build();
        }

        @Test
        void testFindById() {
                when(postRepositoryJpa.findById(1L)).thenReturn(Optional.of(postEntity));
                when(mapper.map(postEntity, Post.class)).thenReturn(post);

                Post result = postRepository.findById(1L);

                assertEquals(post, result);
                verify(postRepositoryJpa, times(1)).findById(1L);
                verify(mapper, times(1)).map(postEntity, Post.class);
        }

        @Test
        void testFindById_PostNotFound() {
                when(postRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

                Post result = postRepository.findById(1L);

                assertNull(result);
                verify(postRepositoryJpa, times(1)).findById(1L);
        }

        @Test
        void testSave() {
                when(mapper.map(post, PostEntity.class)).thenReturn(postEntity);
                when(postRepositoryJpa.save(postEntity)).thenReturn(postEntity);
                when(mapper.map(postEntity, Post.class)).thenReturn(post);

                Post result = postRepository.save(post);

                assertEquals(post, result);
                verify(mapper, times(1)).map(post, PostEntity.class);
                verify(postRepositoryJpa, times(1)).save(postEntity);
                verify(mapper, times(1)).map(postEntity, Post.class);
        }

        @Test
        void testFindAll() {
                List<PostEntity> postEntities = Arrays.asList(postEntity);
                Page<PostEntity> page = new PageImpl<>(postEntities, PageRequest.of(0, 10), 1);

                when(postRepositoryJpa.findNew("", "", "", false, null, PageRequest.of(0, 10)))
                                .thenReturn(page);
                when(mapper.map(postEntities, new TypeToken<List<Post>>() {
                }.getType())).thenReturn(Arrays.asList(post));

                PostPage result = postRepository.findAll("", "", List.of(), false, null, PageRequest.of(0, 10));

                assertEquals(1, result.getPosts().size());
                assertEquals(post, result.getPosts().get(0));
                verify(postRepositoryJpa, times(1)).findNew("", "", "", false, null, PageRequest.of(0, 10));
                verify(mapper, times(1)).map(postEntities, new TypeToken<List<Post>>() {
                }.getType());
        }

        @Test
        void testDeleteById() {
                postRepository.deleteById(1L);
                verify(postRepositoryJpa, times(1)).deleteById(1L);
        }
}