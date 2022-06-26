package com.udacity.jdnd.course3.critter.utility;

import com.udacity.jdnd.course3.critter.controller.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public final class DTOUtils {


    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        List<EmployeeSkill> employeeSkills = employee.getSkillLevels();
        List<EmployeeSkillDTO> employeeSkillDTOS = listConvertEmployeeSkillToEmployeeSkillDTO(employeeSkills);
        employee.setSkillLevels(new ArrayList<>());
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setSkillLevels(employeeSkillDTOS);
        return employeeDTO;
    }

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        List<EmployeeSkillDTO> employeeSkillDTOS = employeeDTO.getSkillLevels();
        List<EmployeeSkill> employeeSkills = listConvertEmployeeSkillDTOToEmployeeSkill(employeeDTO.getSkillLevels());
        employeeDTO.setSkillLevels(new ArrayList<>());
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setSkillLevels(employeeSkills);
        return employee;
    }

    public static EmployeeSkillDTO convertEmployeeSkillToEmployeeSKillDTO(EmployeeSkill employeeSkill) {
        EmployeeSkillDTO employeeSkillDTO = new EmployeeSkillDTO();
        BeanUtils.copyProperties(employeeSkill, employeeSkillDTO);
        return employeeSkillDTO;
    }

    public static EmployeeSkill convertEmployeeSkillDTOToEmployeeSkill(EmployeeSkillDTO employeeSkillDTO) {
        EmployeeSkill employeeSkill = new EmployeeSkill();
        BeanUtils.copyProperties(employeeSkillDTO,employeeSkill);
        return employeeSkill;
    }

    public static List<EmployeeSkill> listConvertEmployeeSkillDTOToEmployeeSkill(List<EmployeeSkillDTO> employeeSkillDTOS) {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        employeeSkillDTOS.forEach(employeeSkillDTO -> {
            employeeSkills.add(convertEmployeeSkillDTOToEmployeeSkill(employeeSkillDTO));
        });
        return employeeSkills;
    }

    public static List<EmployeeSkillDTO> listConvertEmployeeSkillToEmployeeSkillDTO(List<EmployeeSkill> employeeSkills) {
        List<EmployeeSkillDTO> employeeSkillDTOS = new ArrayList<>();
        employeeSkills.forEach(employeeSkill -> {
            employeeSkillDTOS.add(convertEmployeeSkillToEmployeeSKillDTO(employeeSkill));
        });
        return employeeSkillDTOS;
    }
}
