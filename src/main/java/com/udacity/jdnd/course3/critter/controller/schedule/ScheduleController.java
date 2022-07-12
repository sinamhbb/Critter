package com.udacity.jdnd.course3.critter.controller.schedule;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = new Schedule();
            schedule.setId(scheduleDTO.getId());
            schedule.setDate(scheduleDTO.getDate());
            Schedule finalSchedule = schedule;
            scheduleDTO.getEmployeeIds().forEach(id -> {
                Employee employee = new Employee();
                employee.setId(id);
                finalSchedule.addEmployee(employee);
            });
            scheduleDTO.getActivities().forEach(id -> {
                EmployeeSkill employeeSkill = new EmployeeSkill();
                employeeSkill.setId(id);
                finalSchedule.addActivity(employeeSkill);
            });
            scheduleDTO.getPetIds().forEach(id -> {
                Pet pet = new Pet();
                pet.setId(id);
                finalSchedule.addPet(pet);
            });
            scheduleDTO.getCustomersIds().forEach(id -> {
                Customer customer = new Customer();
                customer.setId(id);
                finalSchedule.addCustomer(customer);
            });
            schedule = scheduleService.createSchedule(finalSchedule);
            scheduleDTO.setId(schedule.getId());
            return ResponseEntity.ok(scheduleDTO);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {

        try {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.getAllSchedules().forEach(schedule -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setDate(schedule.getDate());
            scheduleDTOS.add(scheduleDTO);
        });
            return ResponseEntity.ok(scheduleDTOS);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForPet(@PathVariable long petId) throws Throwable {
        try {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.getScheduleByPetsId(petId).forEach(schedule -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setActivities(schedule.getActivities().stream().map(EmployeeSkill::getId).collect(Collectors.toList()));
            scheduleDTO.setCustomersIds(schedule.getCustomers().stream().map(Customer::getId).collect(Collectors.toList()));
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(schedule.getDate());
            scheduleDTOS.add(scheduleDTO);
        });
            return ResponseEntity.ok(scheduleDTOS);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForEmployee(@PathVariable long employeeId)  {
        try {
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
            scheduleService.getSchedulesByEmployeesId(employeeId).forEach(schedule -> {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                scheduleDTO.setId(schedule.getId());
                scheduleDTO.setActivities(schedule.getActivities().stream().map(EmployeeSkill::getId).collect(Collectors.toList()));
                scheduleDTO.setCustomersIds(schedule.getCustomers().stream().map(Customer::getId).collect(Collectors.toList()));
                scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
                scheduleDTO.setDate(schedule.getDate());
                scheduleDTOS.add(scheduleDTO);
            });
            return ResponseEntity.ok(scheduleDTOS);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForCustomer(@PathVariable long customerId) {
        try {
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
            scheduleService.getScheduleByCustomersId(customerId).forEach(schedule -> {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                scheduleDTO.setId(schedule.getId());
                scheduleDTO.setActivities(schedule.getActivities().stream().map(EmployeeSkill::getId).collect(Collectors.toList()));
                scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
                scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
                scheduleDTO.setDate(schedule.getDate());
                scheduleDTOS.add(scheduleDTO);
            });
            return ResponseEntity.ok(scheduleDTOS);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
