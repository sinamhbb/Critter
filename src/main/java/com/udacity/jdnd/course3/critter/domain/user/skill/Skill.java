package com.udacity.jdnd.course3.critter.domain.user.skill;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Skill {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255)
    private String name;

    public Skill(String name) {
        this.name = name;
    }
}
