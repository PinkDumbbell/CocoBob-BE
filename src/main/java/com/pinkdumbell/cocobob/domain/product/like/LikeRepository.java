package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,LikeId> {

    Optional<Like> findByLikeId(LikeId likeId);
    Long countByProduct(Product product);

    Optional<Like> findByProduct(Product product);

    Optional<Like> findByProductAndUser(Product product,User user);
}
