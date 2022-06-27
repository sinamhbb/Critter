package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    public Employee getEmployee(Long id) throws Throwable {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        return employee.get();
    }

    public Employee saveEmployee(Employee employee) throws Throwable {
        if(employee.getId() != null){
            employeeRepository.findById(employee.getId()).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        }

        employee.getSkillLevels().forEach(employeeSkill -> {
            employeeSkill.setEmployee(employee);
        });

        return employeeRepository.save(employee);
    }

    public Set<EmployeeSkill> saveEmployeeSkills(Set<EmployeeSkill> employeeSkills) throws Throwable {
        Long employeeId = employeeSkills.stream().findFirst().orElseThrow(NullPointerException::new).getEmployee().getId();
        if(employeeSkills.stream().allMatch(employeeSkill -> Objects.equals(employeeSkill.getEmployee().getId(), employeeId))) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(NoSuchElementException::new);
            employeeSkills.forEach(employee::addSkillLevel);
            return employeeRepository.save(employee).getSkillLevels();
        }
        throw new Exception("Something went Wrong!");
    }

    public Long deleteEmployeeSkill(Long id) throws Throwable {
        Optional<EmployeeSkill> employeeSkillToBeDeleted = employeeSkillRepository.findById(id);
        EmployeeSkill savedEmployeeSkill = employeeSkillToBeDeleted.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        employeeSkillToBeDeleted.ifPresent(employeeSkill -> employeeSkillRepository.deleteById(id));
        return savedEmployeeSkill.getId();
    }

    public Long deleteEmployee(Long id) throws Throwable {
        Optional<Employee> employeeToBeDeleted = employeeRepository.findById(id);
        employeeToBeDeleted.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        employeeToBeDeleted.ifPresent(employee -> {
            employeeRepository.deleteById(id);
        });
        return id;
    }
}
