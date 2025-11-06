package com.example.userservice.service.impl;

import com.example.userservice.model.UserProfile;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfile> findAll() {
        log.debug("Finding all user profiles");
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfile> findById(Long id) {
        log.debug("Finding user profile by ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfile> findByUserId(Long userId) {
        log.debug("Finding user profile by userId: {}", userId);
        return userRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfile> findByEmail(String email) {
        log.debug("Finding user profile by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        log.info("Creating new user profile for userId: {}", userProfile.getUserId());
        
        // Check if userId already exists
        if (userProfile.getUserId() != null && userRepository.existsByUserId(userProfile.getUserId())) {
            log.error("User with userId {} already exists", userProfile.getUserId());
            throw new IllegalArgumentException("User with userId " + userProfile.getUserId() + " already exists");
        }
        
        // Check if email already exists
        if (userProfile.getEmail() != null && userRepository.existsByEmail(userProfile.getEmail())) {
            log.error("User with email {} already exists", userProfile.getEmail());
            throw new IllegalArgumentException("User with email " + userProfile.getEmail() + " already exists");
        }
        
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());
        
        UserProfile savedProfile = userRepository.save(userProfile);
        log.info("Successfully created user profile with ID: {}", savedProfile.getId());
        
        return savedProfile;
    }

    @Override
    public UserProfile update(Long id, UserProfile userProfile) {
        log.info("Updating user profile with ID: {}", id);
        
        return userRepository.findById(id)
                .map(existingProfile -> {
                    // Update fields
                    if (userProfile.getFirstName() != null) {
                        existingProfile.setFirstName(userProfile.getFirstName());
                    }
                    if (userProfile.getLastName() != null) {
                        existingProfile.setLastName(userProfile.getLastName());
                    }
                    if (userProfile.getEmail() != null && !userProfile.getEmail().equals(existingProfile.getEmail())) {
                        // Check if new email is already taken
                        if (userRepository.existsByEmail(userProfile.getEmail())) {
                            throw new IllegalArgumentException("Email " + userProfile.getEmail() + " is already taken");
                        }
                        existingProfile.setEmail(userProfile.getEmail());
                    }
                    if (userProfile.getPhone() != null) {
                        existingProfile.setPhone(userProfile.getPhone());
                    }
                    if (userProfile.getAddress() != null) {
                        existingProfile.setAddress(userProfile.getAddress());
                    }
                    if (userProfile.getCity() != null) {
                        existingProfile.setCity(userProfile.getCity());
                    }
                    if (userProfile.getState() != null) {
                        existingProfile.setState(userProfile.getState());
                    }
                    if (userProfile.getCountry() != null) {
                        existingProfile.setCountry(userProfile.getCountry());
                    }
                    if (userProfile.getZipCode() != null) {
                        existingProfile.setZipCode(userProfile.getZipCode());
                    }
                    
                    existingProfile.setUpdatedAt(LocalDateTime.now());
                    
                    UserProfile updatedProfile = userRepository.save(existingProfile);
                    log.info("Successfully updated user profile with ID: {}", id);
                    
                    return updatedProfile;
                })
                .orElseThrow(() -> {
                    log.error("User profile with ID {} not found", id);
                    return new IllegalArgumentException("User profile with ID " + id + " not found");
                });
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting user profile with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            log.error("User profile with ID {} not found", id);
            throw new IllegalArgumentException("User profile with ID " + id + " not found");
        }
        
        userRepository.deleteById(id);
        log.info("Successfully deleted user profile with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(Long userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfile> searchByName(String searchTerm) {
        log.debug("Searching users by name: {}", searchTerm);
        return userRepository.searchByName(searchTerm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfile> findByCity(String city) {
        log.debug("Finding users by city: {}", city);
        return userRepository.findByCity(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfile> findByCountry(String country) {
        log.debug("Finding users by country: {}", country);
        return userRepository.findByCountry(country);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfile> findAllOrderedByCreatedDate() {
        log.debug("Finding all users ordered by created date");
        return userRepository.findAllOrderedByCreatedDate();
    }
}
