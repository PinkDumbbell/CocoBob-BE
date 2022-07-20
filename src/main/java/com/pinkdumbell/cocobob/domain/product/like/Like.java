package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Like {
    @EmbeddedId
    private LikeId likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;
}
