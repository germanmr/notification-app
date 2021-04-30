package com.acme.notificacionapp.repository;

import com.acme.notificacionapp.entity.Employee;
import com.acme.notificacionapp.entity.QEmployee;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EmployeeDao {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void initiate(){
        employeeRepository.save(new Employee("Diana", "IT", 3000L));
        employeeRepository.save(new Employee("Linda", "Sales", 3000L));
        employeeRepository.save(new Employee("Mike", "IT", 3000L));
        employeeRepository.save(new Employee("Denise", "Admin", 3000L));
        employeeRepository.save(new Employee("Rose", "Admin", 3000L));
    }

    public void employee() {
        QEmployee employee = QEmployee.employee;

        // Max!!
        JPAQuery query = new JPAQuery(entityManager);
        Long fetch = (Long) query.select(employee.salary.max()).from(employee).fetchFirst();
//        queryFactory.selectFrom(department)
//                .where(department.size.eq(
//                        JPAExpressions.select(d.size.max()).from(d)))
//                .fetch();
//        JPAQuery query = new JPAQuery(entityManager);
//        Long maxSalary2 = (Long) query.select(employee.salary.max()).from(employee).fetchOne();

        BooleanExpression employeeIsAdmin = employee.dept.eq("Admin");
        BooleanExpression employeeSalaryOver3500 = employee.salary.gt(3500);

//        List<Employee> employees = (List<Employee>) employeeRepository.findAll(employeeIsAdmin.and(employeeSalaryOver3500));
//
//        System.out.println("employees: " + employees);
    }

}
