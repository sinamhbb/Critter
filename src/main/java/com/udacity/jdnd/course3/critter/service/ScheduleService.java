package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule) {
        if (schedule.getDate().isAfter(LocalDate.now())) {
            if (schedule.getPets().stream().anyMatch(pet -> pet.getCustomers().isEmpty())) {
                throw new IllegalArgumentException("This Pet Doesn't Have an Owner");
            }
            return scheduleRepository.save(schedule);
        } else {
            throw new IllegalArgumentException("Schedule Date is not valid");
        }
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByEmployeesId(Long id) throws Throwable {
        return scheduleRepository.findAllByEmployeesId(id).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }

    public List<Schedule> getScheduleForPet(Long id) throws Throwable {
        return scheduleRepository.findAllByPetsId(id).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }

    public List<Schedule> getScheduleByPetsId(Long id) throws Throwable {
        return scheduleRepository.findAllByPetsId(id).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }

    public List<Schedule> getScheduleByActivitiesId(Long id) throws Throwable {
        return scheduleRepository.findAllByActivitiesId(id).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }

    public List<Schedule> getScheduleByCustomersId(Long id) throws Throwable {
        return scheduleRepository.findAllByCustomersId(id).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
    }
}
