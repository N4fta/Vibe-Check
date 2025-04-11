// package com.neo.vibecheck.controller;

// import static com.neo.vibecheck.logic.impl.JSONMapper.asJsonString; //
// Imports method, no need to declare class name every use
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.security.Key;
// import java.time.Instant;
// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.Date;
// import java.util.Map;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import
// com.neo.vibecheck.configuration.auth.token.impl.AccessTokenEncoderDecoderImpl;
// import com.neo.vibecheck.domain.User;
// import com.neo.vibecheck.domain.requests.CreateUserRequest;
// import com.neo.vibecheck.domain.requests.UpdateUserRequest;
// import com.neo.vibecheck.domain.services.UserService;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// @ExtendWith(SpringExtension.class)
// @ExtendWith(MockitoExtension.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// @Import(AccessTokenEncoderDecoderImpl.class)
// @ActiveProfiles("test")
// class UserControllerTests {

// @Value("${jwt.secret}")
// private String secret;

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private UserService userService;

// @Test
// void GET_Users_ShouldReturnStatusCode200() throws Exception {
// mockMvc.perform(MockMvcRequestBuilders
// .get("/users")
// .header("Authorization", "Bearer " + generateFakeJwtToken("USER"))
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isOk());
// Mockito.verify(userService).getUsers();

// }

// @Test
// void GET_SpecificUser_ShouldReturnStatusCode200() throws Exception {
// // Setup
// User exampleUser = User.builder()
// .id(Long.valueOf(1))
// .email("John Doe")
// .username("john.doe")
// .password("password")
// .birthDate(LocalDate.of(1990, 1, 1))
// .build();
// Mockito.when(userService.getUser("john.doe")).thenReturn(exampleUser);

// // Test & Verify
// mockMvc.perform(MockMvcRequestBuilders
// .get("/users/john.doe")
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isOk());
// Mockito.verify(userService).getUser("john.doe");
// }

// @Test
// void GET_NonExistentUser_ShouldReturnStatusCode404() throws Exception {

// mockMvc.perform(MockMvcRequestBuilders
// .get("/users/fake.user")
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isNotFound());
// Mockito.verify(userService).getUser("fake.user");
// }

// @Test
// void POST_User_ShouldReturnStatusCode201() throws Exception {
// CreateUserRequest request = CreateUserRequest.builder()
// .email("John Doe")
// .username("john.doe")
// .password("password")
// .birthDate(LocalDate.of(1990, 1, 1))
// .build();
// String userRequestJson = asJsonString(request);

// mockMvc.perform(MockMvcRequestBuilders
// .post("/users")
// .content(userRequestJson)
// .contentType(MediaType.APPLICATION_JSON)
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isCreated());
// Mockito.verify(userService).createUser(request);
// }

// @Test
// @WithMockUser(roles = "USER")
// void PUT_User_ShouldReturnStatusCode200() throws Exception {
// long id = 1;
// UpdateUserRequest request = UpdateUserRequest.builder()
// .id(Long.valueOf(1))
// .email("John Doe")
// .username("john.doe")
// .password("password")
// .birthDate(LocalDate.of(1990, 1, 1))
// .build();
// String userRequestJson = asJsonString(request);

// mockMvc.perform(MockMvcRequestBuilders
// .put("/users/" + id)
// .contentType(MediaType.APPLICATION_JSON)
// .content(userRequestJson))
// .andExpect(status().isNoContent());
// Mockito.verify(userService).updateUser(request);
// }

// @Test
// @WithMockUser(roles = "USER")
// void DELETE_User_ShouldReturnStatusCode204() throws Exception {
// int id = 1;

// mockMvc.perform(MockMvcRequestBuilders
// .delete("/users/" + id))
// .andExpect(status().isNoContent());
// Mockito.verify(userService).deleteUser(id);
// }

// public String generateFakeJwtToken(String role) {
// Instant now = Instant.now();
// Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
// Map<String, Object> claimsMap = Map.of("role", role, "userId", 1);

// return Jwts.builder()
// .setIssuedAt(Date.from(now))
// .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
// .addClaims(claimsMap)
// .signWith(key)
// .compact();
// }

// }
