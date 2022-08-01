package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.common.BaseEntity;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.product.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String name;

    private String thumbnailPath;
    @Enumerated(EnumType.STRING)
    private PetSex sex;

    private Boolean isSpayed;

    private Integer age;

    private LocalDate birthday;

    private Float bodyWeight;

    private Boolean isPregnant;

    @Column(nullable = true)
    private Integer fatLevel;

    private Integer activityLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private PetImage petImage;

    @Builder
    public Pet(String name,
               PetSex sex,
               Integer age,
               LocalDate birthday,
               Boolean isSpayed,
               Boolean isPregnant,
               Float bodyWeight,
               Integer activityLevel,
               Breed breed,
               User user) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
        this.isSpayed = isSpayed;
        this.isPregnant = isPregnant;
        this.bodyWeight = bodyWeight;
        this.activityLevel = activityLevel;
        this.breed = breed;
        this.user = user;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
