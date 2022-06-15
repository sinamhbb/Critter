package com.udacity.jdnd.course3.critter.domain.user.employee;


import com.udacity.jdnd.course3.critter.controller.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee extends User {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<DayOfWeek> daysAvailable;


}
