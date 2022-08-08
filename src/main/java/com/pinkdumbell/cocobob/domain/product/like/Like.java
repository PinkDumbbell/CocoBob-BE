package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "\"like\"")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Like {
    @EmbeddedId
    private LikeId likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
}
