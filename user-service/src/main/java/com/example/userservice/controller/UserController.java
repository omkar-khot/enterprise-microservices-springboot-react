package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.UserProfile;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for User operations
 * Provides endpoints for CRUD operations on users
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing user profiles")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * Create a new user
     */
    @PostMapping
    @Operation(summary = "Create new user", description = "Creates a new user profile")
    public ResponseEntity<UserProfile> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserProfile createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user profile by ID")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        UserProfile user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Get user by email
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves a user profile by email address")
    public ResponseEntity<UserProfile> getUserByEmail(@PathVariable String email) {
        UserProfile user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Get all users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all user profiles")
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        List<UserProfile> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get users with pagination
     */
    @GetMapping("/page")
    @Operation(summary = "Get users with pagination", description = "Retrieves users with pagination support")
    public ResponseEntity<Page<UserProfile>> getUsersPage(Pageable pageable) {
        Page<UserProfile> usersPage = userService.getUsersPage(pageable);
        return ResponseEntity.ok(usersPage);
    }

    /**
     * Update user
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user profile")
    public ResponseEntity<UserProfile> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        UserProfile updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user profile")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activate user account
     */
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate user", description = "Activates a user account")
    public ResponseEntity<UserProfile> activateUser(@PathVariable Long id) {
        UserProfile user = userService.activateUser(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Deactivate user account
     */
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user", description = "Deactivates a user account")
    public ResponseEntity<UserProfile> deactivateUser(@PathVariable Long id) {
        UserProfile user = userService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Search users by first name
     */
    @GetMapping("/search/firstname")
    @Operation(summary = "Search by first name", description = "Searches users by first name")
    public ResponseEntity<List<UserProfile>> searchByFirstName(@RequestParam String firstName) {
        List<UserProfile> users = userService.searchByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    /**
     * Search users by last name
     */
    @GetMapping("/search/lastname")
    @Operation(summary = "Search by last name", description = "Searches users by last name")
    public ResponseEntity<List<UserProfile>> searchByLastName(@RequestParam String lastName) {
        List<UserProfile> users = userService.searchByLastName(lastName);
        return ResponseEntity.ok(users);
    }

    /**
     * Get active users
     */
    @GetMapping("/active")
    @Operation(summary = "Get active users", description = "Retrieves all active users")
    public ResponseEntity<List<UserProfile>> getActiveUsers() {
        List<UserProfile> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by role
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieves users by role")
    public ResponseEntity<List<UserProfile>> getUsersByRole(@PathVariable String role) {
        List<UserProfile> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Count total users
     */
    @GetMapping("/count")
    @Operation(summary = "Count users", description = "Returns total number of users")
    public ResponseEntity<Long> countUsers() {
        Long count = userService.countUsers();
        return ResponseEntity.ok(count);
    }

    /**
     * Check if email exists
     */
    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Check email exists", description = "Checks if email already exists")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        Boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Service health check endpoint")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User Service is running");
    }
}
