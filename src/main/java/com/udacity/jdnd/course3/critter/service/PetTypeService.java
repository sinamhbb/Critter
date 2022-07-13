package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.pet.PetType;
import com.udacity.jdnd.course3.critter.domain.pet.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PetTypeService {
    @Autowired
    private PetTypeRepository petTypeRepository;

    public PetType savePetType(PetType petType) {
        return petTypeRepository.save(petType);
    }

    public void deletePetType( Long id) {
        petTypeRepository.deleteById(id);
    }

    public PetType getPetTypeById(Long id) {
        return petTypeRepository.findById(id).get();
    }
    public Set<PetType> getAllPetTypes() {
        return new HashSet<>(petTypeRepository.findAll());
    }
}
