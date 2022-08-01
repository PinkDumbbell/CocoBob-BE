package com.pinkdumbell.cocobob.domain.product.property;

import com.pinkdumbell.cocobob.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetPropertyRepository extends JpaRepository<PetProperty, Long> {

    List<PetProperty> findAllByProduct(Product product);
}
