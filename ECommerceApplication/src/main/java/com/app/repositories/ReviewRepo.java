package com.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entites.Review;
import com.app.entites.Product;
import com.app.entites.User;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndProduct(User user, Product product);
    Page<Review> findByProduct(Product product, Pageable pageable);
}
