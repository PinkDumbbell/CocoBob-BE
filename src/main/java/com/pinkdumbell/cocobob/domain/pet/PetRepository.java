package com.pinkdumbell.cocobob.domain.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select p from Pet p join fetch p.breed where p.user.id = :userId")
    List<Pet> findAllByUserIdWithBreed(@Param("userId") Long userId);
}
