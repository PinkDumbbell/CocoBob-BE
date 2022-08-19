package com.pinkdumbell.cocobob.domain.pet.image;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetImageRepository extends JpaRepository<PetImage, Long> {
    Optional<PetImage> findPetImageByPet(Pet pet);
}
