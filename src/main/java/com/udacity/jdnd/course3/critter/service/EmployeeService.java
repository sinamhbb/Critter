package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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

    public Long deleteEmployee(Long id) throws Throwable {
        Optional<Employee> employeeToBeDeleted = employeeRepository.findById(id);
        employeeToBeDeleted.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        employeeToBeDeleted.ifPresent(employee -> {
            employeeRepository.deleteById(id);
        });
        return id;
    }
}
