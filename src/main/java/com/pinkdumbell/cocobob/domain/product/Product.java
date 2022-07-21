package com.pinkdumbell.cocobob.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String code;

    private String name;

    private Integer price;

    private String age;

    @Column(length = 1000)
    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;
}
