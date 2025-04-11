package com.neo.vibecheck.domain.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.neo.vibecheck.domain.requests.UpdatePostRequest;
import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.requests.CreatePostRequest;
import com.neo.vibecheck.domain.requests.PostPage;

public interface PostService {
    PostPage getPosts(String search,
            String author,
            List<String> tags,
            Boolean liked,
            Long userId,
            Pageable pageable);

    Post getPost(long postId);

    Post createPost(CreatePostRequest createPostRequest);

    void updatePost(UpdatePostRequest updatePostRequest);

    void deletePost(long postId);

    void likePost(long postId, long userId);

    void unlikePost(long postId, long userId);
}
