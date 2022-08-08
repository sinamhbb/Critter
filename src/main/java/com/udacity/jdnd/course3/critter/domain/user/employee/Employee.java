package com.udacity.jdnd.course3.critter.domain.user.employee;


import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee extends User {

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<DayOfWeek> daysAvailable = new HashSet<>();;

    @ManyToMany(mappedBy = "employees")
    private Set<Schedule> schedules = new HashSet<>();;

    @OneToMany(mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<EmployeeSkill> skillLevels = new HashSet<>();


    public Employee(Long id) {
        super(id);
    }

    public void addSkillLevel(EmployeeSkill employeeSkill) {
        skillLevels.add( employeeSkill );
        employeeSkill.setEmployee( this );
        System.out.println("employee id: " +employeeSkill.getEmployee().getId() );
    }

    public void removeSkillLevel(EmployeeSkill employeeSkill) {
        skillLevels.remove( employeeSkill );
        employeeSkill.setEmployee( null );
    }


}

