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

    Optional<UserProfile> findByUserId(Long userId);

    Optional<UserProfile> findByEmail(String email);

    boolean existsByUserId(Long userId);

    boolean existsByEmail(String email);

    List<UserProfile> findByCity(String city);

    List<UserProfile> findByCountry(String country);

    @Query("SELECT u FROM UserProfile u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<UserProfile> searchByName(@Param("searchTerm") String searchTerm);

    @Query("SELECT u FROM UserProfile u ORDER BY u.createdAt DESC")
    List<UserProfile> findAllOrderedByCreatedDate();
}
