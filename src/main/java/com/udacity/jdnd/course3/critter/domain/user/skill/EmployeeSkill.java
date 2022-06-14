package com.udacity.jdnd.course3.critter.domain.user.skill;


import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeSkill {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "employee_skill_level", joinColumns = @JoinColumn(name = "employee_skill_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(name = "employee_skill_level", joinColumns = @JoinColumn(name = "employee_skill_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    @Min(1)
    @Max(5)
    private Integer level;

}
