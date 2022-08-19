package com.pinkdumbell.cocobob.domain.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select p " +
            "from Pet p join fetch p.user u join fetch p.breed " +
            "where u.email = :email and p.id = :petId")
    Optional<Pet> findByIdAndUserEmail(@Param("petId") Long petId, @Param("email") String email);
}
