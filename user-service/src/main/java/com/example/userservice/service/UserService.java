package com.example.userservice.service;

import com.example.userservice.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Get all user profiles
     */
    List<UserProfile> findAll();

    /**
     * Find user profile by ID
     */
    Optional<UserProfile> findById(Long id);

    /**
     * Find user profile by userId
     */
    Optional<UserProfile> findByUserId(Long userId);

    /**
     * Find user profile by email
     */
    Optional<UserProfile> findByEmail(String email);

    /**
     * Create new user profile
     */
    UserProfile save(UserProfile userProfile);

    /**
     * Update existing user profile
     */
    UserProfile update(Long id, UserProfile userProfile);

    /**
     * Delete user profile by ID
     */
    void deleteById(Long id);

    /**
     * Check if user exists by ID
     */
    boolean existsById(Long id);

    /**
     * Check if userId is already taken
     */
    boolean existsByUserId(Long userId);

    /**
     * Check if email is already taken
     */
    boolean existsByEmail(String email);

    /**
     * Search users by name
     */
    List<UserProfile> searchByName(String searchTerm);

    /**
     * Find users by city
     */
    List<UserProfile> findByCity(String city);

    /**
     * Find users by country
     */
    List<UserProfile> findByCountry(String country);

    /**
     * Get all users ordered by creation date
     */
    List<UserProfile> findAllOrderedByCreatedDate();
}
