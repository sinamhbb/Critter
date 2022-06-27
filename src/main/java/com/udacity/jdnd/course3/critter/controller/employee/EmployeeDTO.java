package com.udacity.jdnd.course3.critter.controller.employee;

import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private Set<EmployeeSkillDTO> skillLevels;
    private Set<DayOfWeek> daysAvailable;

}
