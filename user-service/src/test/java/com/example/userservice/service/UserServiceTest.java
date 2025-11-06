package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.model.UserProfile;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserProfile testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new UserProfile();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPhoneNumber("1234567890");
        testUser.setRole("USER");
        testUser.setActive(true);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        testUserDTO = UserDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .role("USER")
                .active(true)
                .build();
    }

    @Test
    void createUser_Success() {
        when(userRepository.save(any(UserProfile.class))).thenReturn(testUser);

        UserProfile result = userService.createUser(testUserDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserProfile result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }

    @Test
    void getAllUsers_Success() {
        List<UserProfile> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<UserProfile> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserProfile.class))).thenReturn(testUser);

        UserProfile result = userService.updateUser(1L, testUserDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(userRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(any(UserProfile.class));

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).delete(any(UserProfile.class));
    }

    @Test
    void activateUser_Success() {
        testUser.setActive(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserProfile.class))).thenReturn(testUser);

        UserProfile result = userService.activateUser(1L);

        assertTrue(result.getActive());
        verify(userRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void emailExists_True() {
        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        Boolean result = userService.emailExists("john.doe@example.com");

        assertTrue(result);
    }

    @Test
    void emailExists_False() {
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);

        Boolean result = userService.emailExists("new@example.com");

        assertFalse(result);
    }
}
