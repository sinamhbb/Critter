package com.udacity.jdnd.course3.critter.domain.user.customer;


import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/*
Customer class is a polymorphic type that inherits attributes from User.java . This will allow this class to have its own table and Repository to access it.
*/


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends User {

    private String phoneNumber;

    @ManyToMany(mappedBy = "customers",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    @Column(length = 500)
    private String notes;

    public Customer(Long id) {
        super(id);
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
    }
}
