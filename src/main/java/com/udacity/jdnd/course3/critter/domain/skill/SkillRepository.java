package com.udacity.jdnd.course3.critter.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

//    @Query("SELECT s FROM Skill s WHERE s.name = :name")
    Optional<Skill> findByName(String name);
}
