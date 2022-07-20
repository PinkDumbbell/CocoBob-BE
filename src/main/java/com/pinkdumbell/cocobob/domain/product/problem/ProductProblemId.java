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
    @Column(name = "product_id")
    private Long product;
    @Column(name = "problem_id")
    private Long problem;
}
