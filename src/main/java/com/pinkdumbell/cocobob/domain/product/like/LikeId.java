package com.pinkdumbell.cocobob.domain.product.like;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LikeId implements Serializable {
//    @Column(name = "user_id")
    private Long userId;
//    @Column(name = "product_id")
    private Long productId;
}
