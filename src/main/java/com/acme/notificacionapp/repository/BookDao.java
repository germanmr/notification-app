package com.acme.notificacionapp.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookDao {

    public BookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PersistenceContext
    private EntityManager entityManager;

//    public List<Book> findBooksByAuthorNameAndTitle(String authorName, String title) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
//
//        Root<Book> book = cq.from(Book.class);
//        Predicate authorNamePredicate = cb.equal(book.get("author"), authorName);
//        Predicate titlePredicate = cb.like(book.get("title"), "%" + title + "%");
//        cq.where(authorNamePredicate, titlePredicate);
//
//        TypedQuery<Book> query = entityManager.createQuery(cq);
//        return query.getResultList();
//    }
//
//    @Transactional
//    public void insertWithEntityManager(Book book) {
//        this.entityManager.persist(book);
//    }
//
//    @Transactional
//    public void group() {
//        persistEmployees();
//        findEmployeeCountGroupByDept();
//        findEmployeeAvgSalariesGroupByDept();
//        findEmployeeCountGroupBySalary();
//        findEmployeeMaxSalariesGroupBySelectedDept();
//    }
//
//    @Transactional
//    public void findEmployeeCountGroupByDept() {
//        System.out.println("-- Employee count group by dept --");
////        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
//        Root<Employee> employee = query.from(Employee.class);
//        query.groupBy(employee.get(Employee_.dept));
//        query.multiselect(employee.get(Employee_.dept), criteriaBuilder.count(employee));
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
//        List<Object[]> resultList = typedQuery.getResultList();
//        resultList.forEach(objects ->
//                System.out.printf("Dept: %s, Count: %s%n", objects[0], objects[1]));
//        entityManager.close();
//    }
//
//    @Transactional
//    public void findEmployeeAvgSalariesGroupByDept() {
//        System.out.println("-- Employee avg salaries group by dept --");
////        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
//        Root<Employee> employee = query.from(Employee.class);
//        query.groupBy(employee.get(Employee_.dept));
//        query.multiselect(employee.get(Employee_.dept),
//                criteriaBuilder.avg(employee.get(Employee_.salary)));
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
//        List<Object[]> resultList = typedQuery.getResultList();
//        resultList.forEach(objects ->
//                System.out.printf("Dept: %s, Avg Salary: %s%n", objects[0], objects[1]));
//        entityManager.close();
//    }
//
//    @Transactional
//    public void findEmployeeCountGroupBySalary() {
//        System.out.println("-- Employee count group by salary --");
////        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
//        Root<Employee> employee = query.from(Employee.class);
//        query.groupBy(employee.get(Employee_.salary));
//        query.multiselect(employee.get(Employee_.salary), criteriaBuilder.count(employee));
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
//        List<Object[]> resultList = typedQuery.getResultList();
//        resultList.forEach(objects ->
//                System.out.printf("Salary: %s, Count: %s%n", objects[0], objects[1]));
//        entityManager.close();
//    }
//
//    @Transactional
//    public void findEmployeeMaxSalariesGroupBySelectedDept() {
//        System.out.println("-- Employees max salary group by dept - only in IT and Admin dept --");
////        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
//        Root<Employee> employee = query.from(Employee.class);
//        query.groupBy(employee.get(Employee_.dept));
//        query.multiselect(employee.get(Employee_.dept),
//                criteriaBuilder.max(employee.get(Employee_.salary)));
//        query.having(employee.get(Employee_.dept).in("IT", "Admin"));
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
//        List<Object[]> resultList = typedQuery.getResultList();
//        resultList.forEach(objects ->
//                System.out.printf("Dept: %s, Max Salary: %s%n", objects[0], objects[1]));
//        entityManager.close();
//    }

//    @Transactional
//    public void persistEmployees() {
//        Employee employee1 = Employee.create("Diana", "IT", 3000);
//        Employee employee2 = Employee.create("Rose", "Admin", 2000);
//        Employee employee3 = Employee.create("Denise", "Admin", 4000);
//        Employee employee4 = Employee.create("Mike", "IT", 3500);
//        Employee employee5 = Employee.create("Linda", "Sales", 2000);
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
//        Root<Employee> employee = query.from(Employee.class);
//        query.groupBy(employee.get(Employee_.salary));
//        query.multiselect(employee.get(Employee_.salary), criteriaBuilder.count(employee));
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
//        List<Object[]> resultList = typedQuery.getResultList();
//        resultList.forEach(objects ->
//                System.out.printf("Salary: %s, Count: %s%n", objects[0], objects[1]));
//        entityManager.close();
//    }
//
//    @Transactional
//    public void findEmployeeMaxSalariesGroupBySelectedDept() {
//        System.out.println("-- Employees max salary group by dept - only in IT and Admin dept --");
////        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
//        Root<Employee> employee = query.from(Employee.class);
//        query.groupBy(employee.get(Employee_.dept));
//        query.multiselect(employee.get(Employee_.dept),
//                criteriaBuilder.max(employee.get(Employee_.salary)));
//        query.having(employee.get(Employee_.dept).in("IT", "Admin"));
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
//        List<Object[]> resultList = typedQuery.getResultList();
//        resultList.forEach(objects ->
//                System.out.printf("Dept: %s, Max Salary: %s%n", objects[0], objects[1]));
//        entityManager.close();
//    }
//
//    @Transactional
//    public void persistEmployees() {
//        Employee employee1 = Employee.create("Diana", "IT", 3000);
//        Employee employee2 = Employee.create("Rose", "Admin", 2000);
//        Employee employee3 = Employee.create("Denise", "Admin", 4000);
//        Employee employee4 = Employee.create("Mike", "IT", 3500);
//        Employee employee5 = Employee.create("Linda", "Sales", 2000);
////        EntityManager entityManager = entityManagerFactory.createEntityManager();
////        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
////        entityManager.getTransaction().begin();
//        entityManager.persist(employee1);
//        entityManager.persist(employee2);
//        entityManager.persist(employee3);
//        entityManager.persist(employee4);
//        entityManager.persist(employee5);
////        entityManager.getTransaction().commit();
////        entityManager.close();
//        System.out.println("-- all employees --");
//        System.out.println(employee1);
//        System.out.println(employee2);
//        System.out.println(employee3);
//        System.out.println(employee4);
//        System.out.println(employee5);
//    }

}
