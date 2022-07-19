package com.udacity.jdnd.course3.critter.controller.employee;

import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequestDTO {
    private Set<EmployeeSkillDTO> skills;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
