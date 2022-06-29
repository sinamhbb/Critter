package com.udacity.jdnd.course3.critter.utility;

import com.udacity.jdnd.course3.critter.controller.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import org.springframework.beans.BeanUtils;

import java.util.*;

public final class DTOUtils {


    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Set<EmployeeSkill> employeeSkills = employee.getSkillLevels();
        Set<EmployeeSkillDTO> employeeSkillDTOS = listConvertEmployeeSkillToEmployeeSkillDTO(employeeSkills);
        employee.setSkillLevels(new HashSet<>());
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setSkillLevels(employeeSkillDTOS);
        return employeeDTO;
    }

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        Set<EmployeeSkill> employeeSkills = listConvertEmployeeSkillDTOToEmployeeSkill(employeeDTO.getSkillLevels());
        employeeDTO.setSkillLevels(new HashSet<>());
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setSkillLevels(employeeSkills);
        return employee;
    }

    public static EmployeeSkillDTO convertEmployeeSkillToEmployeeSKillDTO(EmployeeSkill employeeSkill) {
        EmployeeSkillDTO employeeSkillDTO = new EmployeeSkillDTO();
        BeanUtils.copyProperties(employeeSkill, employeeSkillDTO);
        employeeSkillDTO.setEmployeeId(employeeSkill.getEmployee().getId());
        return employeeSkillDTO;
    }

    public static EmployeeSkill convertEmployeeSkillDTOToEmployeeSkill(EmployeeSkillDTO employeeSkillDTO) {
        EmployeeSkill employeeSkill = new EmployeeSkill();
        Employee employee = new Employee();
        employee.setId(employeeSkillDTO.getEmployeeId());
        BeanUtils.copyProperties(employeeSkillDTO,employeeSkill);
        employeeSkill.setEmployee(employee);
        return employeeSkill;
    }

    public static Set<EmployeeSkill> listConvertEmployeeSkillDTOToEmployeeSkill(Set<EmployeeSkillDTO> employeeSkillDTOS) {
        Set<EmployeeSkill> employeeSkills = new HashSet<>();
        employeeSkillDTOS.forEach(employeeSkillDTO -> {
            employeeSkills.add(convertEmployeeSkillDTOToEmployeeSkill(employeeSkillDTO));
        });
        return employeeSkills;
    }

    public static Set<EmployeeSkillDTO> listConvertEmployeeSkillToEmployeeSkillDTO(Set<EmployeeSkill> employeeSkills) {
        Set<EmployeeSkillDTO> employeeSkillDTOS = new HashSet<>();
        employeeSkills.forEach(employeeSkill -> {
            employeeSkillDTOS.add(convertEmployeeSkillToEmployeeSKillDTO(employeeSkill));
        });
        return employeeSkillDTOS;
    }
}
