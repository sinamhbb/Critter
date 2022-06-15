package com.udacity.jdnd.course3.critter.controller.skill;


import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import com.udacity.jdnd.course3.critter.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;


    @PostMapping
    public ResponseEntity<?> saveSkill(@RequestBody Skill skill) {
        try {
            return ResponseEntity.ok(skillService.saveSkill(skill));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping
    public ResponseEntity<?> getSkill(@RequestParam String name) {
        try {
            return ResponseEntity.ok(skillService.getSkill(name));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSkill(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(skillService.deleteSkill(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

}
