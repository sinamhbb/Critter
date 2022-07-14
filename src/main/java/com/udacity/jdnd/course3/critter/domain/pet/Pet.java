package com.udacity.jdnd.course3.critter.domain.pet;


import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToOne(fetch = FetchType.EAGER)
    private PetType type;

    @Column(length = 100)
    @Nationalized
    private String name;

    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

    private LocalDate birthDate;

    @Column(length = 500)
    private String notes;

    public Pet(Long id) {
        this.id = id;
    }

    public void addCustomer(Customer customer) {
        customers.add( customer );
    }

    public void removeCustomer(Customer customer) {
        customers.remove( customer );
    }
}