package com.udacity.jdnd.course3.critter.domain.skill;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeSkill {

    @Id
    @GeneratedValue
    private Long id;


    @OneToOne(fetch = FetchType.EAGER)
//    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Skill skill;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public EmployeeSkill(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EmployeeSkill employeeSkill = (EmployeeSkill) obj;
        return Objects.equals(employee, employeeSkill.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee);
    }
}
