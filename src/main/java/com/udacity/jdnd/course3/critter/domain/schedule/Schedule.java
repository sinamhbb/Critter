package com.udacity.jdnd.course3.critter.domain.schedule;


import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany
    @JoinTable(name = "schedule_employee_pet", joinColumns = {@JoinColumn(name = "schedule_id")}, inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employee;

    @ManyToMany
    @JoinTable(name = "schedule_employee_pet", joinColumns = {@JoinColumn(name = "schedule_id")}, inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> pet;

    private Date date;

    @ManyToMany
    @JoinTable(name = "schedule_employee_pet", joinColumns = {@JoinColumn(name = "schedule_id")}, inverseJoinColumns = @JoinColumn(name = "employee_skill_id"))
    private Set<Skill> activities;
}
