package com.neo.vibecheck.logic.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.neo.vibecheck.domain.LikeNotification;
import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.repositories.PostLikesRepository;
import com.neo.vibecheck.domain.repositories.PostRepository;
import com.neo.vibecheck.domain.requests.CreatePostRequest;
import com.neo.vibecheck.domain.requests.PostPage;
import com.neo.vibecheck.domain.requests.UpdatePostRequest;
import com.neo.vibecheck.domain.services.PostService;
import com.neo.vibecheck.domain.services.UserService;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public PostServiceImpl(PostRepository postRepository, PostLikesRepository postLikesRepository,
            UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.postRepository = postRepository;
        this.postLikesRepository = postLikesRepository;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public PostPage getPosts(String search, String author, List<String> tags, Boolean liked, Long userId,
            Pageable pageable) {
        return postRepository.findAll(search, author, tags, liked, userId, pageable);
    }

    @Override
    public Post getPost(long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Post createPost(CreatePostRequest createPostRequest) {
        User user = userService.getUser(createPostRequest.getOwnerId());

        if (user == null) {
            throw new NullPointerException("User not found");
        }

        Post post = Post.builder()
                .title(createPostRequest.getName())
                .body(createPostRequest.getBody())
                .trackId(createPostRequest.getTrackId())
                .owner(user)
                .build();
        return postRepository.save(post);
    }

    @Override
    public void updatePost(UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(updatePostRequest.getPostId()); // Base post information

        if (updatePostRequest.getOwnerId() != null) {
            User user = userService.getUser(updatePostRequest.getOwnerId());
            if (user == null) {
                throw new NullPointerException("User not found");
            }
            post.setOwner(user);
        }
        if (updatePostRequest.getName() != null) {
            post.setTitle(updatePostRequest.getName());
        }
        if (updatePostRequest.getBody() != null) {
            post.setBody(updatePostRequest.getBody());
        }
        postRepository.save(post);
    }

    @Override
    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public void likePost(long postId, long userId) {
        postLikesRepository.add(postId, userId);
        // User user = userService.getUser(userId);
        // Post post = postRepository.findById(postId);

        // LikeNotification notification = LikeNotification.builder()
        // .postOwnerId(post.getOwner().getId())
        // .postId(post.getId())
        // .likedUsername(user.getUsername())
        // .build();
        // messagingTemplate.convertAndSendToUser(post.getOwner().getUsername(),
        // "/queue/notifications",
        // notification);
    }

    @Override
    public void unlikePost(long postId, long userId) {
        postLikesRepository.remove(postId, userId);
    }

}
