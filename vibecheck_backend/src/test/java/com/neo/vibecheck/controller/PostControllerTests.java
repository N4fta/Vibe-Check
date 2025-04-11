// package com.neo.vibecheck.controller;

// import static com.neo.vibecheck.logic.impl.JSONMapper.asJsonString;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import com.neo.vibecheck.domain.Post;
// import com.neo.vibecheck.domain.User;
// import com.neo.vibecheck.domain.requests.CreatePostRequest;
// import com.neo.vibecheck.domain.requests.LikePostRequest;
// import com.neo.vibecheck.domain.requests.PostPage;
// import com.neo.vibecheck.domain.requests.UpdatePostRequest;
// import com.neo.vibecheck.domain.services.PostService;

// @SpringBootTest
// @AutoConfigureMockMvc
// @ActiveProfiles("test")
// class PostControllerTests {

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private PostService postServiceMock;

// // #region Example Data
// private final User exampleUser1 = User.builder()
// .id(Long.valueOf(1))
// .email("john.doe@example.com")
// .username("john.doe")
// .password("password")
// .birthDate(LocalDate.of(1990, 1, 1))
// .build();

// private final User exampleUser2 = User.builder()
// .id(Long.valueOf(2))
// .email("jane.doe@example.com")
// .username("jane.doe")
// .password("password2")
// .birthDate(LocalDate.of(1992, 2, 2))
// .build();

// private final Post examplePost1 = Post.builder()
// .id(Long.valueOf(1))
// .owner(exampleUser1)
// .title("Test post name")
// .body("Test post body")
// .likes(2)
// .build();

// private final Post examplePost3 = Post.builder()
// .id(Long.valueOf(3))
// .owner(exampleUser1)
// .title("Test post name 3")
// .body("Test post body 3")
// .likes(6)
// .build();

// private final Post examplePost2 = Post.builder()
// .id(Long.valueOf(2))
// .owner(exampleUser2)
// .title("Test post name 2")
// .body("Test post body 2")
// .comments(List.of(examplePost3))
// .likes(4)
// .build();
// // #endregion

// @Test
// @WithMockUser(roles = "USER")
// void GET_Posts_ShouldReturnPosts_WhenNoSortParametersProvided() throws
// Exception {
// List<Post> postList = Arrays.asList(examplePost1, examplePost2,
// examplePost3);
// PostPage posts = new PostPage(postList, 0, 0);
// when(postServiceMock.getPosts("", "", List.of(), false, null,
// PageRequest.of(0, 10)))
// .thenReturn(posts);

// mockMvc.perform(MockMvcRequestBuilders
// .get("/posts")
// .contentType(MediaType.APPLICATION_JSON))
// .andExpect(status().isOk())
// .andExpect(jsonPath("$").isArray())
// .andExpect(jsonPath("$.length()").value(3))
// .andExpect(jsonPath("$[0].id").value(1))
// .andExpect(jsonPath("$[1].id").value(2))
// .andExpect(jsonPath("$[2].id").value(3));

// verify(postServiceMock).getPosts("", "", List.of(), false, null,
// PageRequest.of(0, 10));
// }

// @Test
// @WithMockUser(roles = "USER")
// void GET_Posts_ShouldReturnPostsSortedByTitleDescending() throws Exception {
// List<Post> postList = Arrays.asList(examplePost1, examplePost2,
// examplePost3);
// PostPage posts = new PostPage(postList, 0, 0);
// when(postServiceMock.getPosts("", "", List.of(), false, null,
// PageRequest.of(0, 10)))
// .thenReturn(posts);

// mockMvc.perform(MockMvcRequestBuilders
// .get("/posts")
// .param("sortProperty", "title")
// .param("ascending", "false")
// .contentType(MediaType.APPLICATION_JSON))
// .andExpect(status().isOk())
// .andExpect(jsonPath("$").isArray())
// .andExpect(jsonPath("$.length()").value(3))
// .andExpect(jsonPath("$[0].id").value(3))
// .andExpect(jsonPath("$[1].id").value(2))
// .andExpect(jsonPath("$[2].id").value(1));

// verify(postServiceMock).getPosts("", "", List.of(), false, null,
// PageRequest.of(0, 10));
// }

// @Test
// @WithMockUser(roles = "USER")
// void GET_Posts_ShouldReturnPostsSortedByTitleAscending() throws Exception {
// List<Post> postList = Arrays.asList(examplePost1, examplePost2,
// examplePost3);
// PostPage posts = new PostPage(postList, 0, 0);
// when(postServiceMock.getPosts("", "", List.of(), false, null,
// PageRequest.of(0, 10)))
// .thenReturn(posts);

// mockMvc.perform(MockMvcRequestBuilders
// .get("/posts")
// .param("sortProperty", "title")
// .param("ascending", "true")
// .contentType(MediaType.APPLICATION_JSON))
// .andExpect(status().isOk())
// .andExpect(jsonPath("$").isArray())
// .andExpect(jsonPath("$.length()").value(3))
// .andExpect(jsonPath("$[0].id").value(1))
// .andExpect(jsonPath("$[1].id").value(2))
// .andExpect(jsonPath("$[2].id").value(3));

// verify(postServiceMock).getPosts("", "", List.of(), false, null,
// PageRequest.of(0, 10));
// }

// @Test
// @WithMockUser(roles = "USER")
// void POST_Post_ShouldReturnStatusCode201() throws Exception {
// CreatePostRequest request = CreatePostRequest.builder()
// .ownerId(Long.valueOf(1))
// .name("Test post")
// .body("Nulla id minim minim commodo. Sit consectetur ad ex officia fugiat
// mollit tempor in. Elit aliquip ad id ut et. Non culpa proident anim
// adipisicing consectetur pariatur aute ut adipisicing ex ullamco voluptate.
// Exercitation tempor esse sunt laboris et adipisicing irure cupidatat tempor
// nulla excepteur. Deserunt Lorem Lorem sunt fugiat laborum officia pariatur
// est.")
// .build();
// String postRequestJson = asJsonString(request);

// Post newPost = Post.builder()
// .id(Long.valueOf(1))
// .title("Test post")
// .body("Nulla id minim minim commodo. Sit consectetur ad ex officia fugiat
// mollit tempor in. Elit aliquip ad id ut et. Non culpa proident anim
// adipisicing consectetur pariatur aute ut adipisicing ex ullamco voluptate.
// Exercitation tempor esse sunt laboris et adipisicing irure cupidatat tempor
// nulla excepteur. Deserunt Lorem Lorem sunt fugiat laborum officia pariatur
// est.")
// .build();

// Mockito.when(postServiceMock.createPost(request)).thenReturn(newPost);

// mockMvc.perform(MockMvcRequestBuilders
// .post("/posts")
// .content(postRequestJson)
// .contentType(MediaType.APPLICATION_JSON)
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isCreated())
// .andExpect(jsonPath("$.title").value("Test post"));
// Mockito.verify(postServiceMock).createPost(request);
// }

// @Test
// @WithMockUser(roles = "USER")
// void PUT_Post_ShouldReturnStatusCode200() throws Exception {
// long id = 1;
// UpdatePostRequest request = UpdatePostRequest.builder()
// .postId(id)
// .ownerId(Long.valueOf(1))
// .name("Test post")
// .body("Nulla id minim minim commodo. Sit consectetur ad ex officia fugiat
// mollit tempor in. Elit aliquip ad id ut et. Non culpa proident anim
// adipisicing consectetur pariatur aute ut adipisicing ex ullamco voluptate.
// Exercitation tempor esse sunt laboris et adipisicing irure cupidatat tempor
// nulla excepteur. Deserunt Lorem Lorem sunt fugiat laborum officia pariatur
// est.")
// .build();
// String postRequestJson = asJsonString(request);

// mockMvc.perform(MockMvcRequestBuilders
// .put("/posts/" + id)
// .contentType(MediaType.APPLICATION_JSON)
// .content(postRequestJson))
// .andExpect(status().isNoContent());
// Mockito.verify(postServiceMock).updatePost(request);
// }

// @Test
// @WithMockUser(roles = "USER")
// void DELETE_Post_ShouldReturnStatusCode204() throws Exception {
// int id = 1;

// mockMvc.perform(MockMvcRequestBuilders
// .delete("/posts/" + id))
// .andExpect(status().isNoContent());
// Mockito.verify(postServiceMock).deletePost(id);
// }

// @Test
// @WithMockUser(roles = "USER")
// void POST_LikePost_ShouldReturnStatusCode204() throws Exception {
// long postId = 1;
// long userId = 1;
// LikePostRequest request = LikePostRequest.builder()
// .postId(postId)
// .build();
// String likeRequestJson = asJsonString(request);

// mockMvc.perform(MockMvcRequestBuilders
// .post("/posts/like")
// .content(likeRequestJson)
// .contentType(MediaType.APPLICATION_JSON)
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isNoContent());
// Mockito.verify(postServiceMock).likePost(postId, userId);
// }

// @Test
// @WithMockUser(roles = "USER")
// void DELETE_UnlikePost_ShouldReturnStatusCode204() throws Exception {
// long postId = 1;
// long userId = 1;
// LikePostRequest request = LikePostRequest.builder()
// .postId(postId)
// .build();
// String unlikeRequestJson = asJsonString(request);

// mockMvc.perform(MockMvcRequestBuilders
// .delete("/posts/unlike")
// .content(unlikeRequestJson)
// .contentType(MediaType.APPLICATION_JSON)
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isNoContent());
// Mockito.verify(postServiceMock).unlikePost(postId, userId);
// }
// }