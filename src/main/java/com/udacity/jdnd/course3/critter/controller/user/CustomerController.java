package com.udacity.jdnd.course3.critter.controller.user;

import com.udacity.jdnd.course3.critter.controller.pet.PetDTO;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final ModelMapper mapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            Customer customer = mapper.map(customerDTO, Customer.class);
            customerDTO.getPetIds().forEach(petId -> {
                Pet pet = new Pet();
                pet.setId(petId);
                customer.addPet(pet);
            });
            customerDTO.setId(customerService.saveCustomer(customer).getId());
            return ResponseEntity.ok(customerDTO);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(customerDTO);
        }
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId){
        try {
            Customer customer = customerService.getCustomer(customerId);
            CustomerDTO customerDTO = mapper.map(customer, CustomerDTO.class);
            customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            return ResponseEntity.ok(customerDTO);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        try {
            return mapper.map(customerService.getCustomers(),new TypeToken<List<CustomerDTO>>() {}.getType());
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<CustomerDTO>> getOwnerByPet(@PathVariable long petId){
        try {
            List<CustomerDTO>  customerDTOS = mapper.map(customerService.getCustomersByPet(petId),new TypeToken<List<CustomerDTO>>() {}.getType());
            customerDTOS.forEach(petDTO -> {
                petDTO.setPetIds(List.of(petId));
            });
            return ResponseEntity.ok(customerDTOS);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

}
