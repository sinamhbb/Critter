package com.udacity.jdnd.course3.critter.domain.schedule;


import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToMany
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany
    private List<EmployeeSkill> activities = new ArrayList<>();

    @ManyToMany
    private List<Customer> customers = new ArrayList<>();

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public void addActivity(EmployeeSkill employeeSkill) {
        this.activities.add(employeeSkill);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
}
