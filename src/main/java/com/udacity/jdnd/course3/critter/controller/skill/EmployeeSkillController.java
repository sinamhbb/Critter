package com.udacity.jdnd.course3.critter.controller.skill;

import com.udacity.jdnd.course3.critter.service.EmployeeSkillService;
import com.udacity.jdnd.course3.critter.utility.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-skill")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;

    @GetMapping
    public ResponseEntity<EmployeeSkillDTO> getEmployeeSkill(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(DTOUtils.convertEmployeeSkillToEmployeeSKillDTO(employeeSkillService.getEmployeeSkill(id)));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }
}