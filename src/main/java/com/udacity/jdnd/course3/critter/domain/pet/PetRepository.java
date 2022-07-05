package com.udacity.jdnd.course3.critter.domain.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    Set<Pet> findByCustomersId(Long id);
}
