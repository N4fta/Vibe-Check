package com.neo.vibecheck.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.domain.Post;
import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.repositories.PostLikesRepository;
import com.neo.vibecheck.domain.repositories.PostRepository;
import com.neo.vibecheck.domain.requests.CreatePostRequest;
import com.neo.vibecheck.domain.requests.PostPage;
import com.neo.vibecheck.domain.requests.UpdatePostRequest;
import com.neo.vibecheck.domain.services.PostService;
import com.neo.vibecheck.domain.services.UserService;
import com.neo.vibecheck.logic.impl.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PostServiceTests {

        @Mock
        private PostRepository postRepository;

        @Mock
        private PostLikesRepository postLikesRepository;

        @Mock
        private UserService userService;

        private PostService postService;

        // #region Example Data
        private final User exampleUser1 = User.builder()
                        .id(Long.valueOf(1))
                        .email("john.doe@example.com")
                        .username("john.doe")
                        .password("password")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build();

        private final User exampleUser2 = User.builder()
                        .id(Long.valueOf(2))
                        .email("jane.doe@example.com")
                        .username("jane.doe")
                        .password("password2")
                        .birthDate(LocalDate.of(1992, 2, 2))
                        .build();

        private final Post examplePost1 = Post.builder()
                        .id(Long.valueOf(1))
                        .owner(exampleUser1)
                        .title("Test post name")
                        .body("Test post body")
                        .likes(2)
                        .build();

        private final Post examplePost2 = Post.builder()
                        .id(Long.valueOf(2))
                        .owner(exampleUser2)
                        .title("Test post name 2")
                        .body("Test post body 2")
                        .likes(4)
                        .build();
        // #endregion

        @BeforeEach
        void init() {
                postService = new PostServiceImpl(postRepository, postLikesRepository, userService, null);
        }

        @Test
        void getPosts_ReturnsListOfPosts() {
                // Arrange
                List<Post> expectedPosts = List.of(examplePost1, examplePost2);
                Mockito.when(postRepository.findAll("", "", List.of(), false, null, PageRequest.of(0, 10)))
                                .thenReturn(new PostPage(expectedPosts, 0, 1));

                // Act
                PostPage actualPosts = postService.getPosts("", "", List.of(), false, null, PageRequest.of(0, 10));

                // Assert
                assertEquals(expectedPosts, actualPosts.getPosts());
                Mockito.verify(postRepository).findAll("", "", List.of(), false, null, PageRequest.of(0, 10));
        }

        @Test
        void getPosts_ThrowsExceptionOnConnectionFailure() {
                // Arrange
                Mockito.when(postRepository.findAll("", "", List.of(), false, null, PageRequest.of(0, 10)))
                                .thenThrow(new RuntimeException("Failed to connect"));

                // Act & Assert
                assertThrows(RuntimeException.class,
                                () -> postService.getPosts("", "", List.of(), false, null, PageRequest.of(0, 10)));
                Mockito.verify(postRepository).findAll("", "", List.of(), false, null, PageRequest.of(0, 10));
        }

        @Test
        void getPost_ReturnsPost() {
                // Arrange
                Long postId = Long.valueOf(1);
                Post expectedPost = examplePost1;
                Mockito.when(postRepository.findById(postId)).thenReturn(expectedPost);

                // Act
                Post actualPost = postService.getPost(postId);

                // Assert
                assertEquals(expectedPost, actualPost);
                Mockito.verify(postRepository).findById(postId);
        }

        @Test
        void getPost_ThrowsExceptionOnNotFound() {
                // Arrange
                Long postId = Long.valueOf(1);
                Mockito.when(postRepository.findById(postId)).thenThrow(new RuntimeException("Post not found"));

                // Act & Assert
                assertThrows(RuntimeException.class, () -> postService.getPost(postId));
                Mockito.verify(postRepository).findById(postId);
        }

        @Test
        void getPost_ThrowsExceptionOnConnectionFailure() {
                // Arrange
                Long postId = Long.valueOf(1);
                Mockito.when(postRepository.findById(postId)).thenThrow(new RuntimeException("Failed to connect"));

                // Act & Assert
                assertThrows(RuntimeException.class, () -> postService.getPost(postId));
                Mockito.verify(postRepository).findById(postId);
        }

        @Test
        void createPost_ReturnsPost() {
                // Arrange
                CreatePostRequest request = CreatePostRequest.builder()
                                .ownerId(Long.valueOf(1))
                                .name("Test post name")
                                .body("Test post body")
                                .build();
                Post newPost = examplePost1;
                newPost.setId(null);
                newPost.setLikes(0);
                Mockito.doReturn(exampleUser1).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.save(any(Post.class))).thenReturn(newPost);

                // Act
                Post createdPost = postService.createPost(request);

                // Assert
                assertEquals(newPost, createdPost);
                Mockito.verify(postRepository).save(newPost);
        }

        @Test
        void createPost_ThrowsExceptionOnIntegrityConstraintViolation() {
                // Arrange
                CreatePostRequest request = CreatePostRequest.builder()
                                .ownerId(Long.valueOf(1))
                                .name("Test post name")
                                .body("Test post body")
                                .build();
                Post newPost = examplePost1;
                newPost.setId(null);
                newPost.setLikes(0);
                Mockito.doReturn(exampleUser1).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.save(newPost))
                                .thenThrow(new RuntimeException("Integrity constraint violation"));

                // Act & Assert
                assertThrows(RuntimeException.class, () -> postService.createPost(request));
                Mockito.verify(postRepository).save(newPost);
        }

        @Test
        void createPost_ThrowsExceptionWhenUserNotFound() {
                // Arrange
                CreatePostRequest request = CreatePostRequest.builder()
                                .ownerId(Long.valueOf(3)) // Assuming this ownerId does not exist
                                .name("Test post name")
                                .body("Test post body")
                                .build();
                Mockito.doReturn(null).when(userService).getUser(request.getOwnerId());

                // Act & Assert
                assertThrows(NullPointerException.class, () -> postService.createPost(request));
                Mockito.verify(postRepository, Mockito.never()).save(any(Post.class));
        }

        @Test
        void updatePost_OnlyOwner() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .ownerId(Long.valueOf(2))
                                .build();
                Post updatedPost = examplePost1;
                Mockito.doReturn(exampleUser2).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals(exampleUser2, updatedPost.getOwner());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_OnlyName() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .name("New post name")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals("New post name", updatedPost.getTitle());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_OnlyBody() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .body("New post body")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals("New post body", updatedPost.getBody());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_AllFields() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .ownerId(Long.valueOf(2))
                                .name("New post name")
                                .body("New post body")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.doReturn(exampleUser2).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals(exampleUser2, updatedPost.getOwner());
                assertEquals("New post name", updatedPost.getTitle());
                assertEquals("New post body", updatedPost.getBody());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_NullOwnerId() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .name("New post name")
                                .body("New post body")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals("New post name", updatedPost.getTitle());
                assertEquals("New post body", updatedPost.getBody());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_NullName() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .ownerId(Long.valueOf(2))
                                .body("New post body")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.doReturn(exampleUser2).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals(exampleUser2, updatedPost.getOwner());
                assertEquals("New post body", updatedPost.getBody());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_NullBody() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .ownerId(Long.valueOf(2))
                                .name("New post name")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.doReturn(exampleUser2).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act
                postService.updatePost(request);

                // Assert
                assertEquals(exampleUser2, updatedPost.getOwner());
                assertEquals("New post name", updatedPost.getTitle());
                Mockito.verify(postRepository).save(updatedPost);
        }

        @Test
        void updatePost_ThrowsExceptionWhenUserNotFound() {
                // Arrange
                UpdatePostRequest request = UpdatePostRequest.builder()
                                .postId(Long.valueOf(1))
                                .ownerId(Long.valueOf(3)) // Assuming this ownerId does not exist
                                .name("New post name")
                                .body("New post body")
                                .build();
                Post updatedPost = examplePost1;
                Mockito.doReturn(null).when(userService).getUser(request.getOwnerId());
                Mockito.when(postRepository.findById(request.getPostId())).thenReturn(updatedPost);

                // Act & Assert
                assertThrows(NullPointerException.class, () -> postService.updatePost(request));
                Mockito.verify(postRepository, Mockito.never()).save(updatedPost);
        }

        @Test
        void deletePost_ReturnsVoid() {
                // Arrange
                Long postId = Long.valueOf(1);
                // By default, Mockito returns null for a method call

                // Act
                postService.deletePost(postId);

                // Assert
                Mockito.verify(postRepository).deleteById(postId);
        }

        @Test
        void deletePost_ThrowsExceptionOnNotFound() {
                // Arrange
                Long postId = Long.valueOf(1);
                Mockito.doThrow(new RuntimeException("Post not found")).when(postRepository).deleteById(postId);

                // Act & Assert
                assertThrows(RuntimeException.class, () -> postService.deletePost(postId));
                Mockito.verify(postRepository).deleteById(postId);
        }

        @Test
        void deletePost_ThrowsExceptionOnIntegrityConstraintViolation() {
                // Arrange
                Long postId = Long.valueOf(1);
                Mockito.doThrow(new RuntimeException("Integrity constraint violation")).when(postRepository)
                                .deleteById(postId);

                // Act & Assert
                assertThrows(RuntimeException.class, () -> postService.deletePost(postId));
                Mockito.verify(postRepository).deleteById(postId);
        }

        @Test
        void likePost_ReturnsVoid() {
                // Arrange
                Long postId = Long.valueOf(1);
                Long userId = Long.valueOf(1);
                // Act
                postService.likePost(postId, userId);

                // Assert
                Mockito.verify(postLikesRepository).add(postId, userId);
        }

        @Test
        void unlikePost_ReturnsVoid() {
                // Arrange
                Long postId = Long.valueOf(1);
                Long userId = Long.valueOf(1);

                // Act
                postService.unlikePost(postId, userId);

                // Assert
                Mockito.verify(postLikesRepository).remove(postId, userId);
        }

        @Test
        void unlikePost_ThrowsExceptionOnNotFound() {
                // Arrange
                Long postId = Long.valueOf(1);
                Long userId = Long.valueOf(1);
                Mockito.doThrow(new RuntimeException("Post not found")).when(postLikesRepository).remove(postId,
                                userId);

                // Act & Assert
                assertThrows(RuntimeException.class, () -> postService.unlikePost(postId, userId));
                Mockito.verify(postLikesRepository).remove(postId, userId);
        }
}