package com.udacity.jdnd.course3.critter.controller.employee;


import com.udacity.jdnd.course3.critter.controller.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final static ModelMapper mapper = new ModelMapper();

    static {
        Converter<Set<EmployeeSkillDTO>, Set<EmployeeSkill>> employeeSkillSetToDTOSet = ctx -> ctx.getSource().stream().map(EmployeeController::DTOToEmployeeSkill).collect(Collectors.toSet());
        TypeMap<EmployeeDTO, Employee> DTOToScheduleTypeMap = mapper.createTypeMap(EmployeeDTO.class, Employee.class);
        DTOToScheduleTypeMap.addMappings(
                mapper -> {
                    mapper.using(employeeSkillSetToDTOSet).map(EmployeeDTO::getSkillLevels, Employee::setSkillLevels);
                });
    }

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

    private static EmployeeSkill DTOToEmployeeSkill(EmployeeSkillDTO employeeSkillDTO) {
        return mapper.map(employeeSkillDTO, EmployeeSkill.class);
    }

    private EmployeeSkillDTO employeeSkillToDTO(EmployeeSkill employeeSkill) {
        return mapper.map(employeeSkill, EmployeeSkillDTO.class);
    }

    private Set<EmployeeSkill> DTOSetToEmployeeSkillSet(Set<EmployeeSkillDTO> employeeSkillDTOS) {
        return mapper.map(employeeSkillDTOS, new TypeToken<Set<EmployeeSkill>>() {
        }.getType());
    }

    private Set<EmployeeSkillDTO> employeeSkillSetToDTOSet(Set<EmployeeSkill> employeeSkills) {
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
            Employee employee = DTOToEmployee(employeeDTO);
            Employee savedEmployee = employeeService.saveEmployee(employee);
            System.out.println("employee id in controller: " + savedEmployee.getId());
            return ResponseEntity.ok(employeeToDTO(savedEmployee));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeDTO);
        }
    }

    @PutMapping
    public ResponseEntity<Set<EmployeeSkillDTO>> saveEmployeeSkills(@RequestBody Set<EmployeeSkillDTO> employeeSkillDTOs) {
        try {
            return ResponseEntity.ok(employeeSkillSetToDTOSet(employeeService.saveEmployeeSkills(DTOSetToEmployeeSkillSet(employeeSkillDTOs))));
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

    @PutMapping("/availability/{employeeId}")
    public ResponseEntity<EmployeeDTO> setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            Employee employee = employeeService.setAvailabilityFoEmployee(daysAvailable,employeeId);
            return ResponseEntity.ok(employeeToDTO(employee));
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<Set<EmployeeDTO>> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        try {
            System.out.println("num of emp: " + employeeRequestDTO.getSkills().stream().findFirst().get().getName());
            Set<Employee> employees = employeeService.getEmployeeBySkillName(employeeRequestDTO.getSkills().stream().map(Skill::getId).collect(Collectors.toSet()));
            System.out.println("num of emp: " + employees.size());
            employees.removeIf(employee -> !employee.getDaysAvailable().contains(employeeRequestDTO.getDate().getDayOfWeek()));
            System.out.println("num of emp: " + employees.size());
            Set<EmployeeDTO> employeeDTOS = employeeSetToDTOSet(employees);
            return ResponseEntity.ok(employeeDTOS);
        } catch (Throwable t) {
            System.out.println(t);
            return ResponseEntity.badRequest().build();
        }
    }
}
