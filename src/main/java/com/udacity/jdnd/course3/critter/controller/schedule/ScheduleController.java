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
        Converter<List<Long>, List<Employee>> listOfLongToListOfEmployees = ctx -> ctx.getSource().stream().map(Employee::new).collect(Collectors.toList());
        Converter<List<Long>, List<EmployeeSkill>> listOfLongToListOfEmployeeSkills = ctx -> ctx.getSource().stream().map(EmployeeSkill::new).collect(Collectors.toList());
        Converter<List<Long>, List<Pet>> listOfLongToListOfPets = ctx -> ctx.getSource().stream().map(Pet::new).collect(Collectors.toList());
        Converter<List<Long>, List<Customer>> listOfLongToListOfCustomers = ctx -> ctx.getSource().stream().map(Customer::new).collect(Collectors.toList());


        TypeMap<ScheduleDTO, Schedule> DTOToScheduleTypeMap = mapper.createTypeMap(ScheduleDTO.class, Schedule.class);
        DTOToScheduleTypeMap.addMappings(
                mapper -> {
                    mapper.using(listOfLongToListOfEmployees).map(ScheduleDTO::getEmployeeIds, Schedule::setEmployees);
                    mapper.using(listOfLongToListOfEmployeeSkills).map(ScheduleDTO::getActivities, Schedule::setActivities);
                    mapper.using(listOfLongToListOfPets).map(ScheduleDTO::getPetIds, Schedule::setPets);
                    mapper.using(listOfLongToListOfCustomers).map(ScheduleDTO::getCustomersIds, Schedule::setCustomers);
                });


        Converter<List<Employee>, List<Long>> listOfEmployeesToListOfLong = ctx -> ctx.getSource().stream().map(User::getId).collect(Collectors.toList());
        Converter<List<EmployeeSkill>, List<Long>> listOfEmployeeSkillsToListOfLong = ctx -> ctx.getSource().stream().map(EmployeeSkill::getId).collect(Collectors.toList());
        Converter<List<Pet>, List<Long>> listOfPetToListOfLong = ctx -> ctx.getSource().stream().map(Pet::getId).collect(Collectors.toList());
        Converter<List<Customer>, List<Long>> listOfCustomerToListOfLong = ctx -> ctx.getSource().stream().map(User::getId).collect(Collectors.toList());

        TypeMap<Schedule, ScheduleDTO> scheduleToDTOTypeMap = mapper.createTypeMap(Schedule.class, ScheduleDTO.class);
        scheduleToDTOTypeMap.addMappings(
                mapper -> {
                    mapper.using(listOfEmployeesToListOfLong).map(Schedule::getEmployees, ScheduleDTO::setEmployeeIds);
                    mapper.using(listOfEmployeeSkillsToListOfLong).map(Schedule::getActivities, ScheduleDTO::setActivities);
                    mapper.using(listOfPetToListOfLong).map(Schedule::getPets, ScheduleDTO::setPetIds);
                    mapper.using(listOfCustomerToListOfLong).map(Schedule::getCustomers, ScheduleDTO::setCustomersIds);
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
            return ResponseEntity.badRequest().build();
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
    public ResponseEntity<List<ScheduleDTO>> getScheduleForPet(@PathVariable long petId) throws Throwable {
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
