package com.udacity.jdnd.course3.critter.controller.pet;

import com.udacity.jdnd.course3.critter.domain.pet.PetType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */

@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private Long id;
    private PetType type;
    private String name;
    private List<Long> ownerIds;
    private LocalDate birthDate;
    private String notes;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(List<Long> ownerIds) {
        this.ownerIds = ownerIds;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
