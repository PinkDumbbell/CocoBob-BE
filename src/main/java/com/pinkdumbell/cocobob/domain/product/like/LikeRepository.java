package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.user.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface LikeRepository extends JpaRepository<Like,LikeId> {

    Optional<Like> findByLikeId(LikeId likeId);
    Long countByProduct(Product product);

    @Query(value = "select l.product from Like l where l.user = :User")
    Page<Product> findAllByUserLike(@Param("User") User user, Pageable pageable);

    Optional<Like> findByProduct(Product product);

    Optional<Like> findByProductAndUser(Product product,User user);
}
