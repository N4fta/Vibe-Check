package com.neo.vibecheck.data;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.neo.vibecheck.data.entity.PostEntity;
import com.neo.vibecheck.data.jpa.PostRepositoryJpa;
import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.repositories.PostRepository;
import com.neo.vibecheck.domain.requests.PostPage;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
@Transactional
public class PostRepositoryImpl implements PostRepository {

    private final PostRepositoryJpa postRepositoryJpa;
    private final ModelMapper mapper;

    @Override
    public Post findById(long postId) {
        Optional<PostEntity> postOptional = postRepositoryJpa.findById(postId);
        if (postOptional.isEmpty()) {
            return null;
        }
        return mapper.map(postOptional.get(), Post.class);
    }

    @Override
    public Post save(Post post) {
        PostEntity postEntity = postRepositoryJpa.save(mapper.map(post, PostEntity.class));
        return mapper.map(postEntity, Post.class);
    }

    @Override
    public PostPage findAll(String search, String author, List<String> tags, Boolean liked, Long userId,
            Pageable pageable) {
        Page<PostEntity> pageResults = postRepositoryJpa.findNew(search, author,
                tags.isEmpty() ? "" : tags.get(0).trim().toLowerCase(), liked, userId, pageable);
        // List<T> is not a simple Type so the Type must be created for the conversion
        // to work and return a populated list instead of an empty one
        List<Post> posts = mapper.map(pageResults.getContent(), new TypeToken<List<Post>>() {
        }.getType());
        return new PostPage(posts, pageResults.getNumber(), pageResults.getTotalPages());
    }

    @Override
    public void deleteById(long postId) {
        postRepositoryJpa.deleteById(postId);
    }
}
