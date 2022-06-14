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
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    @Column(length = 100)
    private String firstname;

    @Nationalized
    @Column(length = 100)
    private String lastname;

    @Column(length = 25)
    private String phoneNumber;

    @Column(length = 320)
    private String emailAddress;

    public User(String firstname, String lastname, String phoneNumber, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

}
