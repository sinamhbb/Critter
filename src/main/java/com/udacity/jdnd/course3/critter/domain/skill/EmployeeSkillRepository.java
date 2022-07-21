package com.udacity.jdnd.course3.critter.domain.skill;

import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {
    @Query("SELECT e.employee FROM EmployeeSkill e WHERE e.skill.name = :name")
    Set<Employee> findBySkillName(String name);

    @Query("SELECT e.employee FROM EmployeeSkill e WHERE e.skill.id = :id")
    Set<Employee> findBySkillId(Long id);
}
