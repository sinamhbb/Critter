package com.udacity.jdnd.course3.critter.controller.pet;


import com.udacity.jdnd.course3.critter.domain.pet.PetType;
import com.udacity.jdnd.course3.critter.service.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/pet-type")
public class PetTypeController {

    @Autowired
    private PetTypeService petTypeService;

    @PostMapping
    public ResponseEntity<PetType> savePetType(PetType petType) {
        try {
           return ResponseEntity.ok(petTypeService.savePetType(petType));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<PetType> getPetType(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(petTypeService.getPetTypeById(id));
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Set<PetType>> getAllPetTypes() {
        try {
            return ResponseEntity.ok(petTypeService.getAllPetTypes());

        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePetType(@PathVariable Long id) {
        try {
            petTypeService.deletePetType(id);
            return ResponseEntity.ok(id);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().build();
        }
    }

}
