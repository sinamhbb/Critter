package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.controller.employee.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Employee getEmployee(Long id) throws Throwable {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        return employee.get();
    }

    public Employee saveEmployee(Employee employee) throws Throwable {
        if(employee.getId() != 0L){
            employeeRepository.findById(employee.getId()).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        }
        Set<EmployeeSkill> employeeSkills = employee.getSkillLevels();
        employee.setSkillLevels(new HashSet<>());

        Employee savedEmployee =  employeeRepository.save(employee);
        employeeSkills.forEach(savedEmployee::addSkillLevel);
//        Employee employeeToReturn = new Employee(savedEmployee.getDaysAvailable(),null,savedEmployee.getSkillLevels());
//        employeeToReturn.setId(savedEmployee.getId());
//        employeeToReturn.setName(savedEmployee.getName());
        return savedEmployee;
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

    public Employee setAvailabilityFoEmployee(Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws Throwable {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(NoSuchElementException::new);
            employee.setDaysAvailable(daysAvailable);
            return employee;
    }

    public Set<Employee> getEmployeeBySkillName(Set<Long> employeeSkillIds) {
        Set<Employee> employees = new HashSet<>();
        employeeSkillIds.forEach(id -> {
            Set<Employee> employees1 = new HashSet<>(employeeSkillRepository.findBySkillId(id)).stream().map(EmployeeSkill::getEmployee).collect(Collectors.toSet());
            employees.addAll(employees1);
        });
        return employees;
    }

}
