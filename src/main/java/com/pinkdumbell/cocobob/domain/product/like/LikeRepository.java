package com.pinkdumbell.cocobob.domain.product.like;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,LikeId> {

    Optional<Like> findByLikeId(LikeId likeId);
}
