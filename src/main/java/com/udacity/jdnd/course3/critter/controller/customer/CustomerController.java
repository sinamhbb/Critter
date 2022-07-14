package com.udacity.jdnd.course3.critter.controller.customer;

import com.udacity.jdnd.course3.critter.controller.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private final static ModelMapper mapper = new ModelMapper();
    static {
        Converter<List<Long>, List<Pet>> listOfLongToListOfPets = ctx -> ctx.getSource().stream().map(Pet::new).collect(Collectors.toList());
        TypeMap<CustomerDTO, Customer> DTOToCustomerTypeMap = mapper.createTypeMap(CustomerDTO.class, Customer.class);
        DTOToCustomerTypeMap.addMappings(mapper -> {
            mapper.using(listOfLongToListOfPets).map(CustomerDTO::getPetIds, Customer::setPets);
        });

        Converter<List<Pet>, List<Long>> listOfPetToListOfLong = ctx -> ctx.getSource().stream().map(Pet::getId).collect(Collectors.toList());
        TypeMap<Customer, CustomerDTO> customerToDTOTypeMap = mapper.createTypeMap(Customer.class, CustomerDTO.class);
        customerToDTOTypeMap.addMappings(mapper -> {
            mapper.using(listOfPetToListOfLong).map(Customer::getPets, CustomerDTO::setPetIds);
        });
    }

    private Customer DTOToCustomer(CustomerDTO customerDTO) {
        return mapper.map(customerDTO, Customer.class);
    }

    private List<Customer> DTOListToCustomerList(List<CustomerDTO> customerDTOs) {
        return mapper.map(customerDTOs, new TypeToken<List<Customer>>() {
        }.getType());
    }

    private CustomerDTO customerToDTO(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    private List<CustomerDTO> customerListToDTOList(List<Customer> customers) {
        return mapper.map(customers, new TypeToken<List<CustomerDTO>>() {
        }.getType());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            Customer customer = customerService.saveCustomer(DTOToCustomer(customerDTO));
            customerDTO.setId(customer.getId());
            return ResponseEntity.ok(customerDTO);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(customerDTO);
        }
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId){
        try {
            Customer customer = customerService.getCustomer(customerId);
            return ResponseEntity.ok(customerToDTO(customer));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        try {
            return ResponseEntity.ok(customerListToDTOList(customerService.getCustomers()));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<CustomerDTO>> getOwnerByPet(@PathVariable long petId){
        try {
            return ResponseEntity.ok(customerListToDTOList(new ArrayList<>(customerService.getCustomersByPet(petId))));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

}
