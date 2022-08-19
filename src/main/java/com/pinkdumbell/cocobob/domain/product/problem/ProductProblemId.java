package com.pinkdumbell.cocobob.domain.product.problem;

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
public class ProductProblemId implements Serializable {
    private Long productId;
    private Long problemId;
}
