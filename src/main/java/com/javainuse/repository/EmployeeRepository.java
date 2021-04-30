package com.javainuse.repository;

import com.javainuse.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //, QuerydslPredicateExecutor<Employee>

}