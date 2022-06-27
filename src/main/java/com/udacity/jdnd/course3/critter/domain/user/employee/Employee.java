package com.udacity.jdnd.course3.critter.domain.user.employee;


import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee extends User {

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<DayOfWeek> daysAvailable;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeSkill> skillLevels;


    public void addSkillLevel(EmployeeSkill employeeSkill) {
        skillLevels.add( employeeSkill );
        employeeSkill.setEmployee( this );
    }

    public void removeSkillLevel(EmployeeSkill employeeSkill) {
        skillLevels.remove( employeeSkill );
        employeeSkill.setEmployee( null );
    }
}

