package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import com.udacity.jdnd.course3.critter.domain.skill.SkillRepository;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill getSkill(String skill) throws Throwable {
        return skillRepository.findByName(skill).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }

    public Skill saveSkill(Skill skill) throws Throwable {
        if (skill.getId() != null) {
            Optional<Skill> savedSkill = skillRepository.findById(skill.getId());
            savedSkill.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        }
        return skillRepository.save(skill);
    }

    public Long deleteSkill(Long id) throws Throwable {
        Optional<Skill> skillToDelete = skillRepository.findById(id);
        skillToDelete.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        skillToDelete.ifPresent(skill -> {
            skillRepository.deleteById(id);
        });
        return skillToDelete.get().getId();
    }
}
