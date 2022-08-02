package com.pinkdumbell.cocobob.domain.product;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long productId);

    Page<Product> findByNameContainingOrDescriptionContaining(String title, String content,
        Pageable pageable);
}
