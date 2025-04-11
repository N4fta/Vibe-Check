package com.neo.vibecheck.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.requests.PostPage;

public interface PostRepository {

    Post findById(long postId);

    Post save(Post post); // Returns a new ID if post is created

    PostPage findAll(String search,
            String author,
            List<String> tags,
            Boolean liked,
            Long userId, Pageable pageable);

    void deleteById(long postId);
}
