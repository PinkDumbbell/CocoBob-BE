package com.pinkdumbell.cocobob.domain.product;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
    JpaSpecificationExecutor<Product>,ProductSearchQueryDsl {

    @Override
    Optional<Product> findById(Long productId);

}
