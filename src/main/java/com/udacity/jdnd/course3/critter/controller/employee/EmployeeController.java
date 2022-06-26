package com.udacity.jdnd.course3.critter.controller.employee;


import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.utility.DTOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DTOUtils dtoUtils;



    @GetMapping
    public ResponseEntity<?> getEmployee(@RequestParam long id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployee(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            List<EmployeeSkill> employeeSkills = new ArrayList<>();
            List<EmployeeSkillDTO> employeeSkillDTOS = employeeDTO.getSkillLevels();
            employeeDTO.getSkillLevels().forEach(employeeSkillDTO -> {
                employeeSkills.add(convertEmployeeSkillDTOToEmployeeSkill(employeeSkillDTO));
            });
            employeeDTO.setSkillLevels(new ArrayList<>());
            Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
            employee.setSkillLevels(employeeSkills);
            employee = employeeService.saveEmployee(employee);
            employeeDTO.setId(employee.getId());
            employeeDTO.setSkillLevels(employeeSkillDTOS);
            return ResponseEntity.ok(employeeDTO);
        } catch (Throwable t) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeDTO);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(employeeService.deleteEmployee(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }

    private static EmployeeSkillDTO convertEmployeeSkillToSKillDTO(EmployeeSkill employeeSkill) {
        EmployeeSkillDTO employeeSkillDTO = new EmployeeSkillDTO();
        BeanUtils.copyProperties(employeeSkill, employeeSkillDTO);
        return employeeSkillDTO;
    }

    private static EmployeeSkill convertEmployeeSkillDTOToEmployeeSkill(EmployeeSkillDTO employeeSkillDTO) {
        EmployeeSkill employeeSkill = new EmployeeSkill();
        BeanUtils.copyProperties(employeeSkillDTO,employeeSkill);
        return employeeSkill;
    }
}
