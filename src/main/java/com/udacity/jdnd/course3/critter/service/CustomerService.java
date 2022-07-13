package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) throws Throwable {
        if (customer.getId() != 0L) {
            customerRepository.findById(customer.getId()).orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        }

        customer.getPets().forEach(pet -> pet.setCustomers(List.of(customer)));
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) throws Throwable {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.orElseThrow((Supplier<Throwable>) IndexOutOfBoundsException::new);
        return customerOptional.get();
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Set<Customer> getCustomersByPet (Long petId) {
        return customerRepository.findByPetsId(petId);
    }
}
