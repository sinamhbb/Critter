package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long id) {
        return scheduleRepository.findAllByPetId(id);
    }



}
