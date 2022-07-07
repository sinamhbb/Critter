package com.udacity.jdnd.course3.critter.controller.pet;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    private final ModelMapper mapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody PetDTO petDTO) throws Throwable {
        try {
            Pet pet = mapper.map(petDTO,Pet.class);
            petDTO.getOwnerIds().forEach(ownerId -> {
                Customer customer = new Customer();
                customer.setId(ownerId);
                pet.addCustomer(customer);
            });
            petService.savePet(pet);
            return ResponseEntity.ok(petDTO);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(petDTO);
        }
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long petId) throws Throwable {
        try {
            Pet pet = petService.getPet(petId);
            PetDTO petDTO = mapper.map(pet, PetDTO.class);
            petDTO.setOwnerIds(pet.getCustomers().stream().map(Customer::getId).collect(Collectors.toList()));
            return ResponseEntity.ok(petDTO);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets(){
        try {
            return mapper.map(petService.getPets(),new TypeToken<List<PetDTO>>() {}.getType());
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(@PathVariable Long ownerId) {
        try {
            List<PetDTO> petDTOs = mapper.map(petService.getPetsByOwner(ownerId),new TypeToken<List<PetDTO>>() {}.getType());
            petDTOs.forEach(petDTO -> petDTO.setOwnerIds(List.of(ownerId))) ;
            return ResponseEntity.ok(petDTOs) ;
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }
}
