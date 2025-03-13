package com.restcrud.springboot.cruddemo.dao;

import com.restcrud.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    // define field for entitymanager
    private EntityManager entityManager;


    // set up constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll() {

        System.out.println("running the query..");
        TypedQuery<Employee> queryResults = entityManager.createQuery("from Employee", Employee.class);

        System.out.println("queryResults - " + queryResults);

        List<Employee> employees = queryResults.getResultList();

        return employees;
    }



    //CRUD inside DAO using ---- SERVICE LAYER--- point - 7
    @Override
    public void createSingleEmployee(Employee employee) {
        entityManager.persist(employee);
        System.out.println("Single Employee Created by DAO");
    }

    @Override
    public void createMultipleEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
           entityManager.persist(employee);
        }
        System.out.println("Multiple Employees Created by DAO");
    }

    @Override
    public Employee readSingleEmployee(int id) {
        System.out.println("Single Employees Find by DAO");
        return entityManager.find(Employee.class, id);
    }

    @Override
    public List<Employee> readMultipleEmployees(List<Integer> ids) {
        System.out.println("Multiple Employees Find by DAO");
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.id IN :ids", Employee.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public List<Employee> readAllEmployees() {
        System.out.println("All Employees Find by DAO");
        return findAll();
    }

    @Override
    public void updateSingleEmployee(Employee employee) {
        entityManager.merge(employee);
        System.out.println("Updated Single Employees by DAO");
    }

    @Override
    public void updateMultipleEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            entityManager.merge(employee);
        }
        System.out.println("Updated Multiple Employees by DAO");
    }

    @Override
    public void deleteSingleEmployee(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Valid ID must be provided for deleting an employee");
        }
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
            System.out.println("Remove Single Employee by DAO");
        }
    }

    @Override
    public void deleteMultipleEmployees(List<Integer> ids) {
        for (Integer id : ids) {
            if (id == 0) {
                throw new IllegalArgumentException("Valid ID must be provided for deleting employee with ID: " + id);
            }
            deleteSingleEmployee(id);
        }
        System.out.println("Remove Multiple Employees by DAO");
    }

    @Override
    public void deleteAllEmployees() {
        entityManager.createQuery("DELETE FROM Employee").executeUpdate();
        System.out.println("Removed All Employees by DAO");
    }

}