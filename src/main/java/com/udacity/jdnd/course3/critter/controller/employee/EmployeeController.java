package com.udacity.jdnd.course3.critter.controller.employee;


import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.utility.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<EmployeeDTO> getEmployee(@RequestParam long id) {
        try {
            return ResponseEntity.ok(DTOUtils.convertEmployeeToEmployeeDTO(employeeService.getEmployee(id)));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeService.saveEmployee(DTOUtils.convertEmployeeDTOToEmployee(employeeDTO));
            employeeDTO.setId(employee.getId());
            return ResponseEntity.ok(employeeDTO);
        } catch (Throwable t) {
            System.out.println(t);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<Set<EmployeeSkillDTO>> saveEmployeeSkills(@RequestBody Set<EmployeeSkillDTO> employeeSkillDTOs) {
        try {
            return ResponseEntity.ok(DTOUtils.listConvertEmployeeSkillToEmployeeSkillDTO(employeeService.saveEmployeeSkills(DTOUtils.listConvertEmployeeSkillDTOToEmployeeSkill(employeeSkillDTOs))));
        } catch (Throwable t) {
            System.out.println(t);
            return ResponseEntity.badRequest().body(employeeSkillDTOs);
        }
    }

    @DeleteMapping("/employee-skill")
    public ResponseEntity<Long> deleteEmployeeSkill(@RequestParam Long employeeSkillId) {
        try {
            return ResponseEntity.ok(employeeService.deleteEmployeeSkill(employeeSkillId));
        } catch (Throwable t) {
            System.out.println(t);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteEmployee(@RequestParam Long id) {
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
}
