package com.neo.vibecheck.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.domain.User;
import com.neo.vibecheck.domain.exceptions.PasswordMismatchException;
import com.neo.vibecheck.domain.exceptions.UserNotFoundException;
import com.neo.vibecheck.domain.repositories.UserRepository;
import com.neo.vibecheck.domain.requests.CreateUserRequest;
import com.neo.vibecheck.domain.requests.LoginUserRequest;
import com.neo.vibecheck.domain.requests.UpdateUserRequest;
import com.neo.vibecheck.domain.responses.LoginResponse;
import com.neo.vibecheck.domain.services.UserService;
import com.neo.vibecheck.logic.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private final User exampleUser1 = User.builder()
            .id(Long.valueOf(1))
            .email("John Doe")
            .username("john.doe")
            .password("password")
            .birthDate(LocalDate.of(1990, 1, 1))
            .build();

    private final User exampleUser2 = User.builder()
            .id(Long.valueOf(2))
            .email("Jane Doe")
            .username("jane.doe")
            .password("password2")
            .birthDate(LocalDate.of(1992, 2, 2))
            .build();

    @BeforeEach
    void init() {
        // Initializing in field declaration did not work
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void getUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> expectedUsers = List.of(
                exampleUser1,
                exampleUser2);
        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void getUsers_ThrowsExceptionOnConnectionFailure() {
        // Arrange
        Mockito.when(userRepository.findAll()).thenThrow(new RuntimeException("Failed to connect"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUsers());
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void getUser_ReturnsUser() {
        // Arrange
        String username = "john.doe";
        User expectedUser = exampleUser1;
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.getUser(username);

        // Assert
        assertEquals(expectedUser, actualUser);
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void getUser_ThrowsExceptionOnNotFound() {
        // Arrange
        String username = "john.doe";
        Mockito.when(userRepository.findByUsername(username)).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUser(username));
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void getUserById_ReturnsUser() {
        // Arrange
        long userId = 1L;
        User expectedUser = exampleUser1;
        Mockito.when(userRepository.findById(userId)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.getUser(userId);

        // Assert
        assertEquals(expectedUser, actualUser);
        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_ThrowsExceptionOnNotFound() {
        // Arrange
        long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUser(userId));
        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void updateUser_ReturnsVoid() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(Long.valueOf(1))
                .email("John Doe")
                .username("john.doe")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        User updatedUser = exampleUser1;
        Mockito.when(userRepository.findById(request.getId())).thenReturn(updatedUser);

        // Act
        userService.updateUser(request);

        // Assert
        Mockito.verify(userRepository).save(updatedUser);
    }

    @Test
    void updateUser_ThrowsExceptionOnNotFound() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(Long.valueOf(1))
                .email("John Doe")
                .username("john.doe")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        User updatedUser = exampleUser1;
        updatedUser.setId(null);
        Mockito.when(userRepository.findById(request.getId())).thenReturn(updatedUser);
        Mockito.when(userRepository.save(updatedUser)).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(request));
        Mockito.verify(userRepository).save(updatedUser);
    }

    @Test
    void updateUser_ThrowsExceptionOnIntegrityConstraintViolation() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(Long.valueOf(1))
                .email("John Doe")
                .username("john.doe")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        User updatedUser = exampleUser1;
        updatedUser.setId(null);
        Mockito.when(userRepository.findById(request.getId())).thenReturn(updatedUser);
        Mockito.when(userRepository.save(updatedUser))
                .thenThrow(new RuntimeException("Integrity constraint violation"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(request));
        Mockito.verify(userRepository).save(updatedUser);
    }

    @Test
    void updateUser_UpdatesEmailOnly() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(Long.valueOf(1))
                .email("new.email@example.com")
                .build();
        User existingUser = exampleUser1;
        Mockito.when(userRepository.findById(request.getId())).thenReturn(existingUser);

        // Act
        userService.updateUser(request);

        // Assert
        assertEquals("new.email@example.com", existingUser.getEmail());
        assertEquals("john.doe", existingUser.getUsername());
        assertEquals("password", existingUser.getPassword());
        assertEquals(LocalDate.of(1990, 1, 1), existingUser.getBirthDate());
        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_UpdatesUsernameOnly() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(Long.valueOf(1))
                .username("new.username")
                .build();
        User existingUser = exampleUser1;
        Mockito.when(userRepository.findById(request.getId())).thenReturn(existingUser);

        // Act
        userService.updateUser(request);

        // Assert
        assertEquals("John Doe", existingUser.getEmail());
        assertEquals("new.username", existingUser.getUsername());
        assertEquals("password", existingUser.getPassword());
        assertEquals(LocalDate.of(1990, 1, 1), existingUser.getBirthDate());
        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_UpdatesBirthDateOnly() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(Long.valueOf(1))
                .birthDate(LocalDate.of(1995, 5, 5))
                .build();
        User existingUser = exampleUser1;
        Mockito.when(userRepository.findById(request.getId())).thenReturn(existingUser);

        // Act
        userService.updateUser(request);

        // Assert
        assertEquals("John Doe", existingUser.getEmail());
        assertEquals("john.doe", existingUser.getUsername());
        assertEquals("password", existingUser.getPassword());
        assertEquals(LocalDate.of(1995, 5, 5), existingUser.getBirthDate());
        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void deleteUser_ReturnsVoid() {
        // Arrange
        String username = "john.doe";
        // By default, Mockito returns null for a method call

        // Act
        userService.deleteUser(username);

        // Assert
        Mockito.verify(userRepository).deleteByUsername(username);
    }

    @Test
    void deleteUser_ThrowsExceptionOnNotFound() {
        // Arrange
        String username = "john.doe";
        Mockito.doThrow(new RuntimeException("User not found")).when(userRepository).deleteByUsername(username);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(username));
        Mockito.verify(userRepository).deleteByUsername(username);
    }

    @Test
    void deleteUser_ThrowsExceptionOnIntegrityConstraintViolation() {
        // Arrange
        String username = "john.doe";
        Mockito.doThrow(new RuntimeException("Integrity constraint violation")).when(userRepository)
                .deleteByUsername(username);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(username));
        Mockito.verify(userRepository).deleteByUsername(username);
    }

    @Test
    void deleteUserById_ReturnsVoid() {
        // Arrange
        long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        Mockito.verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserById_ThrowsExceptionOnNotFound() {
        // Arrange
        long userId = 1L;
        Mockito.doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(userId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
        Mockito.verify(userRepository).deleteById(userId);
    }
}
