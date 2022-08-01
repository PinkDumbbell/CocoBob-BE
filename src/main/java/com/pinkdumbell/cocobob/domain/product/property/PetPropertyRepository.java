package com.pinkdumbell.cocobob.domain.product.property;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetPropertyRepository extends JpaRepository<PetProperty, Long> {

    List<PetProperty> findAllByProduct(Long product_id);
}
