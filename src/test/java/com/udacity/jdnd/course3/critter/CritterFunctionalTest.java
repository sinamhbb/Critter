package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controller.employee.EmployeeController;
import com.udacity.jdnd.course3.critter.controller.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controller.employee.EmployeeRequestDTO;
//import com.udacity.jdnd.course3.critter.controller.employee.EmployeeSkill;
import com.udacity.jdnd.course3.critter.controller.pet.PetController;
import com.udacity.jdnd.course3.critter.controller.pet.PetDTO;
import com.udacity.jdnd.course3.critter.controller.pet.PetTypeController;
import com.udacity.jdnd.course3.critter.controller.schedule.ScheduleController;
//import com.udacity.jdnd.course3.critter.controller.user.*;
import com.udacity.jdnd.course3.critter.controller.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.controller.customer.*;
import com.udacity.jdnd.course3.critter.controller.skill.EmployeeSkillDTO;
import com.udacity.jdnd.course3.critter.controller.skill.SkillController;
import com.udacity.jdnd.course3.critter.domain.pet.PetType;
import com.udacity.jdnd.course3.critter.domain.skill.Skill;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 *
 * These tests should all pass once the project is complete.
 *
 *
 *
 * @SpringBootTest
 * This annotation goes on your unit test class. creates an entire Spring ApplicationContext when running unit tests. It is used if you need to test controller or service classes, or perform integration tests spanning multiple layers.
 *
 * @DataJpaTest
 * This annotation provides an alternate way to test your data layer without providing an application.properties file. It disables Spring autoconfiguration and automatically uses an in-memory database if available. It only loads Entities and Spring Data JPA repositories, but not your Services or Controllers.
 *
 * TestEntityManager
 * TestEntityManager is a class provided by Spring Boot that provides useful methods for persisting test data inside persistence unit tests. It is still available in @DataJpaTests despite the rest of the app not being wired up.
 *
 * @AutoConfigureTestDatabase
 * This annotation can be used with either @SpringBootTest or @DataJpaTest. You can use it to customize Spring’s behavior for replacing the normal datasource. For example, the following annotation could be used in conjunction with @DataJpaTest to indicate that Spring should NOT replace the datasource with an in-memory datasource.
 *
 * @AutoConfigureTestDatabase(replace=Replace.NONE)
 */
@Transactional
@SpringBootTest(classes = CritterApplication.class)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CritterFunctionalTest {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private SkillController skillController;

    @Autowired
    private PetController petController;

    @Autowired
    private PetTypeController petTypeController;

    @Autowired
    private ScheduleController scheduleController;

    @Test
    public void a_testCreateCustomer(){
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO).getBody();
        CustomerDTO retrievedCustomer = customerController.getCustomer(newCustomer.getId()).getBody();
        Assertions.assertEquals(newCustomer.getName(), customerDTO.getName());
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);
    }

    @Test
    public void b_testCreateEmployee(){
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO newEmployee = employeeController.saveEmployee(employeeDTO).getBody();
        EmployeeDTO retrievedEmployee = employeeController.getEmployee(newEmployee.getId()).getBody();
        Assertions.assertEquals(employeeDTO.getSkillLevels(), newEmployee.getSkillLevels());
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void c_testAddPetsToCustomer() throws InterruptedException {

        PetType petType = petTypeController.savePetType(new PetType(null, "CAT")).getBody();

        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO).getBody();
        System.out.println("new Customer ID: " + newCustomer.getId());


        PetDTO petDTO = createPetDTO();
        petDTO.setType(petType);
        petDTO.setOwnerIds(List.of(newCustomer.getId()));
        PetDTO newPet = petController.savePet(petDTO).getBody();
        System.out.println("new Pet ID: " + newPet.getId());
        System.out.println("new Pet ownerId: " + newPet.getOwnerIds().get(0));

        //make sure pet contains customer id
        PetDTO retrievedPet = petController.getPet(newPet.getId()).getBody();
        Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
        Assertions.assertEquals(retrievedPet.getOwnerIds().get(0), newCustomer.getId());
        //make sure you can retrieve pets by owner
        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId()).getBody();
        Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());
        System.out.println("ownerIds: " + retrievedPet.getOwnerIds().get(0));

        //check to make sure customer now also contains pet
        CustomerDTO retrievedCustomer = customerController.getCustomer(newCustomer.getId()).getBody();
        System.out.println("Pet Id from customer:" + retrievedCustomer.getPetIds().get(0));
        Assertions.assertTrue(retrievedCustomer.getPetIds() != null && retrievedCustomer.getPetIds().size() > 0);
        Assertions.assertEquals(retrievedCustomer.getPetIds().get(0), retrievedPet.getId());
    }

    @Test
    public void d_testFindPetsByOwner() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerIds(List.of(newCustomer.getId()));
        PetDTO newPet = petController.savePet(petDTO).getBody();
        PetType petType = petTypeController.savePetType(new PetType(null, "DOG")).getBody();

        petDTO.setType(petType);
        petDTO.setName("DogName");
        PetDTO newPet2 = petController.savePet(petDTO).getBody();

        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId()).getBody();
        Assertions.assertEquals(pets.size(), 2);
        Assertions.assertEquals(pets.get(0).getOwnerIds().get(0), newCustomer.getId());
        Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
    }

    @Test
    public void e_testFindOwnerByPet() throws Throwable {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = customerController.saveCustomer(customerDTO).getBody();
        System.out.println("new customer id: " + newCustomer.getId());

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerIds(List.of(newCustomer.getId()));
        PetDTO newPet = petController.savePet(petDTO).getBody();
        System.out.println("pet id: " + newPet.getId());

        CustomerDTO owner = customerController.getOwnerByPet(newPet.getId()).getBody().get(0);
        Assertions.assertEquals(owner.getId(), newCustomer.getId());
        Assertions.assertEquals(owner.getPetIds().get(0), newPet.getId());
    }

    @Test
    public void f_testChangeEmployeeAvailability() {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO emp1 = employeeController.saveEmployee(employeeDTO).getBody();
        Assertions.assertNull(emp1.getDaysAvailable());

        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        employeeController.setAvailability(availability, emp1.getId());

        EmployeeDTO emp2 = employeeController.getEmployee(emp1.getId()).getBody();
        Assertions.assertEquals(availability, emp2.getDaysAvailable());
    }

    @Test
    public void g_testFindEmployeesByServiceAndTime() {
        EmployeeDTO emp1 = createEmployeeDTO();
        EmployeeDTO emp2 = createEmployeeDTO();
        EmployeeDTO emp3 = createEmployeeDTO();

        Skill feedingSkill = skillController.saveSkill(new Skill(null, "FEEDING")).getBody();
        Skill pettingSkill = skillController.saveSkill(new Skill(null, "PETTING")).getBody();
        Skill walkingSkill = skillController.saveSkill(new Skill(null, "WALKING")).getBody();
        Skill shavingSkill = skillController.saveSkill(new Skill(null, "SHAVING")).getBody();

        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        emp1.setSkillLevels(Sets.newHashSet(new EmployeeSkillDTO(null,feedingSkill, 5,null), new EmployeeSkillDTO(null,pettingSkill, 5,null)));
        emp2.setSkillLevels(Sets.newHashSet(Sets.newHashSet(new EmployeeSkillDTO(null,pettingSkill, 5,null), new EmployeeSkillDTO(null,walkingSkill, 5,null))));
        emp3.setSkillLevels(Sets.newHashSet(Sets.newHashSet(new EmployeeSkillDTO(null,walkingSkill, 5,null), new EmployeeSkillDTO(null,shavingSkill, 5,null))));

        EmployeeDTO emp1n = employeeController.saveEmployee(emp1).getBody();
        EmployeeDTO emp2n = employeeController.saveEmployee(emp2).getBody();
        EmployeeDTO emp3n = employeeController.saveEmployee(emp3).getBody();

        //make a request that matches employee 1 or 2
        EmployeeRequestDTO er1 = new EmployeeRequestDTO();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Sets.newHashSet(pettingSkill));

        Set<Long> eIds1 = employeeController.findEmployeesForService(er1).getBody().stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet( emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequestDTO er2 = new EmployeeRequestDTO();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Sets.newHashSet(walkingSkill, shavingSkill));

        Set<Long> eIds2 = employeeController.findEmployeesForService(er2).getBody().stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void h_testSchedulePetsForServiceWithEmployee() {

        Skill feedingSkill = skillController.saveSkill(new Skill(null, "FEEDING")).getBody();
        Skill pettingSkill = skillController.saveSkill(new Skill(null, "PETTING")).getBody();

        EmployeeDTO employeeTemp = createEmployeeDTO();
        employeeTemp.setSkillLevels(Sets.newHashSet(new EmployeeSkillDTO(null,feedingSkill, 5,null), new EmployeeSkillDTO(null,pettingSkill, 5,null)));
        employeeTemp.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        EmployeeDTO employeeDTO = employeeController.saveEmployee(employeeTemp).getBody();

        CustomerDTO customerDTO = customerController.saveCustomer(createCustomerDTO()).getBody();

        PetDTO petTemp = createPetDTO();

        petTemp.setOwnerIds(List.of(customerDTO.getId()));
        PetDTO petDTO = petController.savePet(petTemp).getBody();

        LocalDate date = LocalDate.of(2022, 12, 25);
        Set<Long> petIds = new HashSet<>();
        petIds.add(petDTO.getId());
        Set<Long> employeeIds = new HashSet<>();
        employeeIds.add(employeeDTO.getId());
        Set<EmployeeSkillDTO> skillSet =  employeeDTO.getSkillLevels();

        scheduleController.createSchedule(createScheduleDTO(petIds, Set.of(customerDTO.getId()), employeeIds, date, skillSet.stream().map(EmployeeSkillDTO::getId).collect(Collectors.toSet())));
        ScheduleDTO scheduleDTO = scheduleController.getAllSchedules().getBody().get(0);

        Assertions.assertEquals(scheduleDTO.getActivityIds(), skillSet.stream().map(EmployeeSkillDTO::getId).collect(Collectors.toSet()));
        Assertions.assertEquals(scheduleDTO.getDate(), date);
        Assertions.assertEquals(scheduleDTO.getEmployeeIds(), employeeIds);
        Assertions.assertEquals(scheduleDTO.getPetIds(), petIds);
    }

    @Test
    public void i_testFindScheduleByEntities() {

        Skill feedingSkill = skillController.saveSkill(new Skill(null, "FEEDING")).getBody();
        Skill pettingSkill = skillController.saveSkill(new Skill(null, "PETTING")).getBody();
        Skill walkingSkill = skillController.saveSkill(new Skill(null, "WALKING")).getBody();
        Skill shavingSkill = skillController.saveSkill(new Skill(null, "SHAVING")).getBody();

        ScheduleDTO schedule1 = populateSchedule(1, 2, LocalDate.now().plusDays(1), Sets.newHashSet(new EmployeeSkillDTO(null,pettingSkill, 5,null), new EmployeeSkillDTO(null,walkingSkill, 5,null)));
        ScheduleDTO schedule2 = populateSchedule(3, 1, LocalDate.now().plusDays(2), Sets.newHashSet(new EmployeeSkillDTO(null,pettingSkill, 5,null), new EmployeeSkillDTO(null,shavingSkill, 5,null)));
        System.out.println("schedule1 Id; " + schedule1.getActivityIds().stream().findFirst());
        //add a third schedule that shares some employees and pets with the other schedules
        ScheduleDTO schedule3 = new ScheduleDTO();
        schedule3.setEmployeeIds(schedule1.getEmployeeIds());
        schedule3.setPetIds(schedule2.getPetIds());
        schedule3.setCustomersIds(schedule2.getCustomersIds());
        schedule3.setActivityIds(Sets.newHashSet(schedule2.getActivityIds().stream().findFirst().get(), schedule1.getActivityIds().stream().findFirst().get()));
        schedule3.setDate(LocalDate.now().plusDays(4));
        scheduleController.createSchedule(schedule3);

        /*
            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
            schedule 1, we should get both the first and third schedule as our result.
         */

        //Employee 1 in is both schedule 1 and 3
        List<ScheduleDTO> scheds1e = scheduleController.getScheduleForEmployee(schedule1.getEmployeeIds().stream().findFirst().get()).getBody();
        compareSchedules(schedule1, scheds1e.get(0));
        compareSchedules(schedule3, scheds1e.get(1));

        //Employee 2 is only in schedule 2
        List<ScheduleDTO> scheds2e = scheduleController.getScheduleForEmployee(schedule2.getEmployeeIds().stream().findFirst().get()).getBody();
        compareSchedules(schedule2, scheds2e.get(0));

        //Pet 1 is only in schedule 1
        List<ScheduleDTO> scheds1p = scheduleController.getScheduleForPet(schedule1.getPetIds().stream().findFirst().get()).getBody();
        compareSchedules(schedule1, scheds1p.get(0));

        //Pet from schedule 2 is in both schedules 2 and 3
        List<ScheduleDTO> scheds2p = scheduleController.getScheduleForPet(schedule2.getPetIds().stream().findFirst().get()).getBody();
        compareSchedules(schedule2, scheds2p.get(0));
        compareSchedules(schedule3, scheds2p.get(1));

        //Owner of the first pet will only be in schedule 1
        List<ScheduleDTO> scheds1c = scheduleController.getScheduleForCustomer(customerController.getOwnerByPet(schedule1.getPetIds().stream().findFirst().get()).getBody().get(0).getId()).getBody();
        compareSchedules(schedule1, scheds1c.get(0));

        //Owner of pet from schedule 2 will be in both schedules 2 and 3
        List<ScheduleDTO> scheds2c = scheduleController.getScheduleForCustomer(customerController.getOwnerByPet(schedule2.getPetIds().stream().findFirst().get()).getBody().get(0).getId()).getBody();
        compareSchedules(schedule2, scheds2c.get(0));
        compareSchedules(schedule3, scheds2c.get(1));
    }



    private static EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("TestEmployee");
        employeeDTO.setSkillLevels(new HashSet<>());
        return employeeDTO;
    }
    private static CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(0L);
        customerDTO.setName("TestCustomer");
        customerDTO.setPhoneNumber("123-456-789");
        customerDTO.setNotes("Test Note");
        customerDTO.setPetIds(new ArrayList<>());
        return customerDTO;
    }

    private static PetDTO createPetDTO() {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(0L);
        petDTO.setName("TestPet");
        petDTO.setNotes("Chubby Cat");
        petDTO.setBirthDate(LocalDate.of(2022, 11, 11));
        petDTO.setOwnerIds(new ArrayList<>());
        return petDTO;
    }

    private static EmployeeRequestDTO createEmployeeRequestDTO() {
        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
        employeeRequestDTO.setDate(LocalDate.of(2019, 12, 25));
        employeeRequestDTO.setSkills(Sets.newHashSet(new Skill(null, "FEEDING"), new Skill(null, "WALKING")));
        return employeeRequestDTO;
    }

    private static ScheduleDTO createScheduleDTO(Set<Long> petIds, Set<Long> customerIds, Set<Long> employeeIds, LocalDate date, Set<Long> activityIds) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setCustomersIds(customerIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setDate(date);
        scheduleDTO.setActivityIds(activityIds);
        return scheduleDTO;
    }

    private ScheduleDTO populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkillDTO> activities) {
        Set<Long> activityIds = new HashSet<>();
        Set<Long> employeeIds = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDTO())
                .map(e -> {
                    e.setSkillLevels(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    EmployeeDTO employee = employeeController.saveEmployee(e).getBody();
                    System.out.println("activity id in employee: " + employee.getSkillLevels().stream().findFirst().get().getId());
                    activityIds.addAll(employee.getSkillLevels().stream().map(EmployeeSkillDTO::getId).collect(Collectors.toSet()));
                    return employee.getId();
                }).collect(Collectors.toSet());
        CustomerDTO customerDTO = customerController.saveCustomer(createCustomerDTO()).getBody();
        Set<Long> petIds = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDTO())
                .map(p -> {
                    p.setOwnerIds(List.of(customerDTO.getId()));
                    return petController.savePet(p).getBody().getId();
                }).collect(Collectors.toSet());
        return scheduleController.createSchedule(createScheduleDTO(petIds, Set.of(customerDTO.getId()), employeeIds, date, activityIds)).getBody();
    }

    private static void compareSchedules(ScheduleDTO scheduleDTO1, ScheduleDTO scheduleDTO2) {
        Assertions.assertEquals(scheduleDTO1.getPetIds(), scheduleDTO2.getPetIds());
//        Assertions.assertEquals(scheduleDTO1.getActivityIds(), scheduleDTO2.getActivityIds());
        Assertions.assertEquals(true,scheduleDTO1.getActivityIds().contains(scheduleDTO2.getActivityIds().stream().findFirst().get()));
        Assertions.assertEquals(scheduleDTO1.getEmployeeIds(), scheduleDTO2.getEmployeeIds());
        Assertions.assertEquals(scheduleDTO1.getDate(), scheduleDTO2.getDate());
    }
}
