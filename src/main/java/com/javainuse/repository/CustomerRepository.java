package com.javainuse.repository;

import com.javainuse.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    // , QuerydslPredicateExecutor<Customer>
    // Your query methods here
}