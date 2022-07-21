package com.udacity.jdnd.course3.critter.controller.employee;


import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final static ModelMapper mapper = new ModelMapper();

    private Employee DTOToEmployee(EmployeeDTO employeeDTO) {
        return mapper.map(employeeDTO, Employee.class);
    }

    private EmployeeDTO employeeToDTO(Employee employee) {
        return mapper.map(employee, EmployeeDTO.class);
    }

    private Set<EmployeeDTO> employeeSetToDTOSet(Set<Employee> employees) {
        return mapper.map(employees, new TypeToken<Set<EmployeeDTO>>() {
        }.getType());
    }

    private EmployeeSkill DTOToEmployeeSkill(EmployeeSkillDTO employeeSkillDTO) {
        return mapper.map(employeeSkillDTO, EmployeeSkill.class);
    }

    private EmployeeSkillDTO employeeSkillToDTO(EmployeeSkill employeeSkill) {
        return mapper.map(employeeSkill, EmployeeSkillDTO.class);
    }

    private Set<EmployeeSkill> DTOListToEmployeeSkillList(Set<EmployeeSkillDTO> employeeSkillDTOS) {
        return mapper.map(employeeSkillDTOS, new TypeToken<Set<EmployeeSkill>>() {
        }.getType());
    }

    private Set<EmployeeSkillDTO> employeeSkillListToDTOList(Set<EmployeeSkill> employeeSkills) {
        return mapper.map(employeeSkills, new TypeToken<Set<EmployeeSkillDTO>>() {
        }.getType());
    }

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<EmployeeDTO> getEmployee(@RequestParam long id) {
        try {
            Employee employee = employeeService.getEmployee(id);
            return ResponseEntity.ok(employeeToDTO(employee));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeService.saveEmployee(DTOToEmployee(employeeDTO));
            return ResponseEntity.ok(employeeToDTO(employee));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeDTO);
        }
    }

    @PutMapping
    public ResponseEntity<Set<EmployeeSkillDTO>> saveEmployeeSkills(@RequestBody Set<EmployeeSkillDTO> employeeSkillDTOs) {
        try {
            Set<EmployeeSkill> employeeSkills = employeeService.saveEmployeeSkills(DTOListToEmployeeSkillList(employeeSkillDTOs));
            return ResponseEntity.ok(employeeSkillListToDTOList(employeeService.saveEmployeeSkills(DTOListToEmployeeSkillList(employeeSkillDTOs))));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
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
    public ResponseEntity<Set<EmployeeDTO>> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        try {
            Set<Employee> employees = employeeService.getEmployeeBySkillName(employeeRequestDTO);
            Set<EmployeeDTO> employeeDTOS = employeeSetToDTOSet(employees);
            return ResponseEntity.ok(employeeDTOS);
        } catch (Throwable t) {
            System.out.println(t);
            return ResponseEntity.badRequest().build();
        }
    }
}
