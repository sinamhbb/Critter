package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet) throws Throwable {
        if (pet.getId() != 0L ) {
            petRepository.findById(pet.getId()).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        }
        pet.getCustomers().forEach(customer -> customer.setPets(List.of(pet)));
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) throws Throwable {
        Optional<Pet> pet = petRepository.findById(id);
        pet.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        return pet.get();
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Set<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByCustomersId(ownerId);
    }
}
