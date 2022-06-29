package com.udacity.jdnd.course3.critter.controller.skill;


import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeSkillDTO {
    private Long id;

    private Skill skill;
//    @Max(5)
//    @Min(1)
    private Integer level;

    private Long employeeId;

}
