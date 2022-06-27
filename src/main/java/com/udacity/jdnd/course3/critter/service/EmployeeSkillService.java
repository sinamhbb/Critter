package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class EmployeeSkillService {

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    public EmployeeSkill getEmployeeSkill(Long id) throws Throwable {
        return employeeSkillRepository.findById(id).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }
}
