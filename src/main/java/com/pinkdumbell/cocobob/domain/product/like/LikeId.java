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
    private Long userId;
    private Long productId;
}
