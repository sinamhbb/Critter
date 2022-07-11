package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.controller.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.pet.PetRepository;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    private final ModelMapper mapper = new ModelMapper();

    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        long startTime = System.currentTimeMillis();
        Schedule schedule = mapper.map(scheduleDTO, Schedule.class);

//        List<Employee> employees = new ArrayList<>();
//        scheduleDTO.getEmployeeIds().forEach(id -> {
//            employees.add(employeeRepository.findById(id).get());
//        });

        List<Pet> pets = new ArrayList<>();
        scheduleDTO.getPetIds().forEach(id -> {
            pets.add(petRepository.findById(id).get());
        });

        List<EmployeeSkill> activities = new ArrayList<>();
        scheduleDTO.getActivityIds().forEach(id -> {
            activities.add(employeeSkillRepository.findById(id).get());
        });

//        schedule.setEmployees(employees);
        schedule.setPets(pets);
        schedule.setActivities(activities);
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime  - startTime)); //134920200
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long id) {
        return scheduleRepository.findAllByPetsId(id);
    }

}
