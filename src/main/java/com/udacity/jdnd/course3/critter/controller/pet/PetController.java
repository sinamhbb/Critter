package com.udacity.jdnd.course3.critter.controller.pet;

import com.udacity.jdnd.course3.critter.controller.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.pet.PetType;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.Converter;
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

    private final static ModelMapper mapper = new ModelMapper();

    static {
        Converter<List<Long>, List<Customer>> listOfLongToListOfCustomers = ctx -> ctx.getSource().stream().map(Customer::new).collect(Collectors.toList());
        TypeMap<PetDTO, Pet> DTOToPetTypeMap = mapper.createTypeMap(PetDTO.class, Pet.class);
        DTOToPetTypeMap.addMappings(mapper -> mapper.using(listOfLongToListOfCustomers).map(PetDTO::getOwnerIds, Pet::setCustomers));


        Converter<List<Customer>, List<Long>> listOfPetsToListOfLongs = ctx -> ctx.getSource().stream().map(Customer::getId).collect(Collectors.toList());
        TypeMap<Pet, PetDTO> petToDTOTypeMap = mapper.createTypeMap(Pet.class, PetDTO.class);
        petToDTOTypeMap.addMappings(mapper -> mapper.using(listOfPetsToListOfLongs).map(Pet::getCustomers, PetDTO::setOwnerIds));
    }

    private Pet DTOToPet(PetDTO petDTO) {
        return mapper.map(petDTO, Pet.class);
    }

    private PetDTO petToDTO(Pet pet) {
        return mapper.map(pet, PetDTO.class);
    }

    private List<PetDTO> petListToDTOList(List<Pet> pets) {
        return mapper.map(pets, new TypeToken<List<PetDTO>>() {
        }.getType());
    }

    private List<Pet> DTOListToPetList(List<PetDTO> petDTOs) {
        return mapper.map(petDTOs, new TypeToken<List<Pet>>() {
        }.getType());
    }

    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody PetDTO petDTO) throws Throwable {
        try {
            Pet pet = DTOToPet(petDTO);
            pet = petService.savePet(pet);
            return ResponseEntity.ok(petToDTO(pet));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().body(petDTO);
        }
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long petId) throws Throwable {
        try {
            Pet pet = petService.getPet(petId);
            return ResponseEntity.ok(petToDTO(pet));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets() {
        try {
            return ResponseEntity.ok(petListToDTOList(petService.getPets()));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(@PathVariable Long ownerId) {
        try {
            List<PetDTO> petDTOs = petListToDTOList(new ArrayList<>(petService.getPetsByOwner(ownerId)));
            return ResponseEntity.ok(petDTOs);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }
}
