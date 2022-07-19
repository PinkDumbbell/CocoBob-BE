package com.pinkdumbell.cocobob.domain.abnormal;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Abnormal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abnormal_id")
    private Long id;

    private String name;
}
