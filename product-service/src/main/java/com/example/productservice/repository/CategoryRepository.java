package com.example.productservice.repository;

import com.example.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Category entity
 * Provides CRUD operations and custom queries
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByActiveTrue();

    Long countByActiveTrue();

    boolean existsByName(String name);
}
