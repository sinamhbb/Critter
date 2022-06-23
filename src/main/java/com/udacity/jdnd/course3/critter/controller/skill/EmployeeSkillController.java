package com.udacity.jdnd.course3.critter.controller.skill;

import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.EmployeeSkillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee-skill")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;

    @GetMapping
    public ResponseEntity<?> getEmployeeSkill(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(employeeSkillService.getEmployeeSkill(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> PostEmployeeSkill(@RequestBody EmployeeSkillDTO employeeSkillDTOl) {
        try {
            return ResponseEntity.ok(employeeSkillService.saveEmployeeSkill(convertEmployeeSkillDTOToEmployeeSkill(employeeSkillDTOl)));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployeeSkill(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(employeeSkillService.deleteEmployeeSkill(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
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