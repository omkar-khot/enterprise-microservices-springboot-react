package com.example.userservice.repository;

import com.example.userservice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Find user profile by userId
     */
    Optional<UserProfile> findByUserId(Long userId);

    /**
     * Find user profile by email
     */
    Optional<UserProfile> findByEmail(String email);

    /**
     * Check if user exists by userId
     */
    boolean existsByUserId(Long userId);

    /**
     * Check if email is already taken
     */
    boolean existsByEmail(String email);

    /**
     * Find users by city
     */
    List<UserProfile> findByCity(String city);

    /**
     * Find users by country
     */
    List<UserProfile> findByCountry(String country);

    /**
     * Search users by first name or last name (case insensitive)
     */
    @Query("SELECT u FROM UserProfile u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<UserProfile> searchByName(@Param("searchTerm") String searchTerm);

    /**
     * Find all users ordered by creation date
     */
    @Query("SELECT u FROM UserProfile u ORDER BY u.createdAt DESC")
    List<UserProfile> findAllOrderedByCreatedDate();
}
