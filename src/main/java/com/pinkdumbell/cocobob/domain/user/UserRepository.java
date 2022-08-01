package com.pinkdumbell.cocobob.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select distinct u from User u left join fetch u.pets where u.email = :email")
    Optional<User> findUserByEmailWithPet(@Param("email") String email);
}
