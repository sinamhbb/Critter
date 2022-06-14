package com.udacity.jdnd.course3.critter.domain.pet;


import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private PetType type;

    @Column(length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Customer> owner;

    @Temporal(TemporalType.DATE)
    private Date birthDate;


    private String notes;
}
