package com.neo.vibecheck.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neo.vibecheck.configuration.auth.token.AccessToken;
import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.SortFieldPosts;
import com.neo.vibecheck.domain.requests.CreatePostRequest;
import com.neo.vibecheck.domain.requests.LikePostRequest;
import com.neo.vibecheck.domain.requests.PostPage;
import com.neo.vibecheck.domain.requests.UpdatePostRequest;
import com.neo.vibecheck.domain.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private final PostService postService;
    private final AccessToken accessToken;

    public PostsController(PostService postService, AccessToken accessToken) {
        this.postService = postService;
        this.accessToken = accessToken;
    }

    // https://stackoverflow.com/questions/78559357/name-for-argument-of-type-java-lang-string-not-specified-and-parameter-name-i
    @GetMapping
    public ResponseEntity<PostPage> getPosts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam("sortProperty") Optional<String> sortProperty,
            @RequestParam("searchTerm") Optional<String> searchTerm,
            @RequestParam("author") Optional<String> author,
            @RequestParam("tags") Optional<List<String>> tags,
            @RequestParam("liked") Optional<Boolean> liked,
            @RequestParam("ascending") Optional<Boolean> ascending) {
        SortFieldPosts sortPropertyEnum = SortFieldPosts.TITLE;
        try {
            sortPropertyEnum = SortFieldPosts.valueOf(sortProperty.orElse("TITLE").toUpperCase());
        } catch (Exception e) {
            System.out.println("Sort Property Invalid");
        }
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (ascending.isPresent() && Boolean.TRUE.equals(ascending.get())) {
            sortDirection = Sort.Direction.ASC;
        }

        // Creates a pageable and sorts it
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by(sortDirection, sortPropertyEnum.toString().toLowerCase()));

        PostPage posts = postService.getPosts(
                searchTerm.orElse("").trim().toLowerCase(),
                author.orElse("").trim().toLowerCase(),
                tags.orElse(List.of()),
                liked.orElse(false),
                accessToken.getUserId(),
                pageable);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePost(@RequestParam("id") long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        createPostRequest.setOwnerId(accessToken.getUserId());
        Post newPost = postService.createPost(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PutMapping
    public ResponseEntity<Void> updatePost(
            @RequestParam("id") long postId,
            @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        updatePostRequest.setPostId(postId);
        postService.updatePost(updatePostRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/like")
    public ResponseEntity<Void> likePost(@RequestBody @Valid LikePostRequest likePostRequest) {
        postService.likePost(likePostRequest.getPostId(), accessToken.getUserId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Void> unlikePost(@RequestBody @Valid LikePostRequest likePostRequest) {
        postService.unlikePost(likePostRequest.getPostId(), accessToken.getUserId());
        return ResponseEntity.noContent().build();
    }
}
