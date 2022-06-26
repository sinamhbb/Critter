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
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
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
}
