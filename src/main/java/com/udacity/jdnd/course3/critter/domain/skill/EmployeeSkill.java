package com.udacity.jdnd.course3.critter.domain.skill;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.controller.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

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

}
