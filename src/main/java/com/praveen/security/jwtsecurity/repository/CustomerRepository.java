package com.praveen.security.jwtsecurity.repository;

import com.praveen.security.jwtsecurity.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
