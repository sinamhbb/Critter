package com.udacity.jdnd.course3.critter.domain.user;


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
