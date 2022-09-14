package com.pinkdumbell.cocobob.domain.product;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
    JpaSpecificationExecutor<Product>,ProductSearchQueryDsl {

    @Cacheable(cacheNames = "productDetails", key = "#productId")
    @Override
    Optional<Product> findById(Long productId);

}
