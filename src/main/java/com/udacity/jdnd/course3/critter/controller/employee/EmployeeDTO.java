package com.udacity.jdnd.course3.critter.controller.employee;

import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private long id;
    private String name;
    private List<EmployeeSkillDTO> skillLevels;
    private List<DayOfWeek> daysAvailable;

}
