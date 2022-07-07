package com.udacity.jdnd.course3.critter.domain.user.customer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Set<Customer> findByPetsId(Long id);
}
