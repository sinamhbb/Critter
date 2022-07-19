package com.udacity.jdnd.course3.critter.controller.skill;

import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeSkillDTO {

    private Long id;

    private Skill skill;

    private Integer level;

    private Long employeeId;

    private final static ModelMapper mapper = new ModelMapper();

}
