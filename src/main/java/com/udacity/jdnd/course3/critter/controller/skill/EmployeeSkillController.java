package com.udacity.jdnd.course3.critter.controller.skill;

import com.udacity.jdnd.course3.critter.service.EmployeeSkillService;
import com.udacity.jdnd.course3.critter.utility.DTOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee-skill")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;

    private final static ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<EmployeeSkillDTO> getEmployeeSkill(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(mapper.map(employeeSkillService.getEmployeeSkill(id), EmployeeSkillDTO.class));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }
}