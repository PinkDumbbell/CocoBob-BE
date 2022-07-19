package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String name;

    private String profilePhotoPath;

    private int sex;

    private Boolean isSpayed;

    private int age;

    private LocalDate birthday;

    private Float bodyWeight;

    private Boolean isPregnant;

    private int fatLevel;

    private int activityLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private PetImage petImage;


}
