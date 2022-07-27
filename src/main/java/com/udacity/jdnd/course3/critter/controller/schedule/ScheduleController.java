package com.udacity.jdnd.course3.critter.controller.schedule;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.skill.EmployeeSkill;
import com.udacity.jdnd.course3.critter.domain.user.User;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    private final static ModelMapper mapper = new ModelMapper();

    static {
        Converter<Set<Long>, Set<Employee>> setOfLongToSetOfEmployees = ctx -> ctx.getSource().stream().map(Employee::new).collect(Collectors.toSet());
        Converter<Set<Long>, Set<EmployeeSkill>> setOfLongToSetOfEmployeeSkills = ctx -> ctx.getSource().stream().map(EmployeeSkill::new).collect(Collectors.toSet());
        Converter<Set<Long>, Set<Pet>> setOfLongToSetOfPets = ctx -> ctx.getSource().stream().map(Pet::new).collect(Collectors.toSet());
        Converter<Set<Long>, Set<Customer>> setOfLongToSetOfCustomers = ctx -> ctx.getSource().stream().map(Customer::new).collect(Collectors.toSet());


        TypeMap<ScheduleDTO, Schedule> DTOToScheduleTypeMap = mapper.createTypeMap(ScheduleDTO.class, Schedule.class);
        DTOToScheduleTypeMap.addMappings(
                mapper -> {
                    mapper.using(setOfLongToSetOfEmployees).map(ScheduleDTO::getEmployeeIds, Schedule::setEmployees);
                    mapper.using(setOfLongToSetOfEmployeeSkills).map(ScheduleDTO::getActivityIds, Schedule::setActivities);
                    mapper.using(setOfLongToSetOfPets).map(ScheduleDTO::getPetIds, Schedule::setPets);
                    mapper.using(setOfLongToSetOfCustomers).map(ScheduleDTO::getCustomersIds, Schedule::setCustomers);
                });


        Converter<Set<Employee>, Set<Long>> setOfEmployeesToSetOfLong = ctx -> ctx.getSource().stream().map(User::getId).collect(Collectors.toSet());
        Converter<Set<EmployeeSkill>, Set<Long>> setOfEmployeeSkillsToSetOfLong = ctx -> ctx.getSource().stream().map(EmployeeSkill::getId).collect(Collectors.toSet());
        Converter<Set<Pet>, Set<Long>> setOfPetToSetOfLong = ctx -> ctx.getSource().stream().map(Pet::getId).collect(Collectors.toSet());
        Converter<Set<Customer>, Set<Long>> setOfCustomerToSetOfLong = ctx -> ctx.getSource().stream().map(User::getId).collect(Collectors.toSet());

        TypeMap<Schedule, ScheduleDTO> scheduleToDTOTypeMap = mapper.createTypeMap(Schedule.class, ScheduleDTO.class);
        scheduleToDTOTypeMap.addMappings(
                mapper -> {
                    mapper.using(setOfEmployeesToSetOfLong).map(Schedule::getEmployees, ScheduleDTO::setEmployeeIds);
                    mapper.using(setOfEmployeeSkillsToSetOfLong).map(Schedule::getActivities, ScheduleDTO::setActivityIds);
                    mapper.using(setOfPetToSetOfLong).map(Schedule::getPets, ScheduleDTO::setPetIds);
                    mapper.using(setOfCustomerToSetOfLong).map(Schedule::getCustomers, ScheduleDTO::setCustomersIds);
                });
    }

    private Schedule DTOToSchedule(ScheduleDTO scheduleDTO) {
        return mapper.map(scheduleDTO, Schedule.class);
    }

    private List<Schedule> DTOListToScheduleList(List<ScheduleDTO> scheduleDTOs) {
        return mapper.map(scheduleDTOs, new TypeToken<List<Schedule>>() {
        }.getType());
    }

    private ScheduleDTO scheduleToDTO(Schedule schedule) {
        return mapper.map(schedule, ScheduleDTO.class);
    }

    private List<ScheduleDTO> scheduleListToDTOList(List<Schedule> schedules) {
        return mapper.map(schedules, new TypeToken<List<ScheduleDTO>>() {
        }.getType());
    }


    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = scheduleService.createSchedule(DTOToSchedule(scheduleDTO));
            scheduleDTO.setId(schedule.getId());
            return ResponseEntity.ok(scheduleDTO);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().header("Error",t.getMessage()).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {

        try {
            return ResponseEntity.ok(scheduleListToDTOList(scheduleService.getAllSchedules()));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForPet(@PathVariable long petId) {
        try {
            return ResponseEntity.ok(scheduleListToDTOList(scheduleService.getScheduleByPetsId(petId)));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForEmployee(@PathVariable long employeeId) {
        try {
            return ResponseEntity.ok(scheduleListToDTOList(scheduleService.getSchedulesByEmployeesId(employeeId)));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForCustomer(@PathVariable long customerId) {
        try {
            return ResponseEntity.ok(scheduleListToDTOList(scheduleService.getScheduleByCustomersId(customerId)));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
