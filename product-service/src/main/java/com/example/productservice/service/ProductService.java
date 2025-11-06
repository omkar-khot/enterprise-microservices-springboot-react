package com.example.productservice.service;

import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Product operations
 * Defines business logic methods for product management
 */
public interface ProductService {
    
    Product createProduct(Product product);
    
    Product updateProduct(Long id, Product product);
    
    Optional<Product> getProductById(Long id);
    
    List<Product> getAllProducts();
    
    Page<Product> getProductsPaginated(Pageable pageable);
    
    List<Product> getProductsByCategory(Long categoryId);
    
    List<Product> searchProductsByName(String name);
    
    List<Product> getActiveProducts();
    
    void deleteProduct(Long id);
    
    boolean existsById(Long id);
}
