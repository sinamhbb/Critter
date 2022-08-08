package com.udacity.jdnd.course3.critter.domain.user;


/*
User class is a MappedSuperclass. This means that this class doesn't have a table by itself but rather is a class that the child class will inherit attributes from.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    public User(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    @Column(length = 100)
    private String name;
}
