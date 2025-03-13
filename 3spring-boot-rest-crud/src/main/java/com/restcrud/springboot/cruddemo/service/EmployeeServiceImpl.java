package com.restcrud.springboot.cruddemo.service;

import com.restcrud.springboot.cruddemo.dao.EmployeeDAO;
import com.restcrud.springboot.cruddemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


    //CRUD Operations in--------- Service-------------------
    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Transactional
    @Override
    public void createSingleEmployee(Employee theEmployee) {
        System.out.println("Service - called DAO to create Single Employee");
        theEmployee.setId(0); // Neutralize ID so DB auto-generates it
        employeeDAO.createSingleEmployee(theEmployee);
    }

    @Transactional
    @Override
    public void createMultipleEmployees(List<Employee> employees) {
        System.out.println("Service - called DAO to create Multiple Employee");
        if (employees == null || employees.isEmpty()) {
            throw new IllegalArgumentException("Employee list cannot be null or empty.");
        }

        for (Employee employee : employees) {
            employee.setId(0); // Neutralize ID to let the DB auto-generate it
        }
        employeeDAO.createMultipleEmployees(employees);
    }

    @Override
    public Employee readSingleEmployee(int id) {
        System.out.println("Service - called DAO to Read Single Employee");
        if (id <= 0) {
            throw new IllegalArgumentException("Valid ID must be provided for reading an employee. Provided ID: " + id);
        }
        return employeeDAO.readSingleEmployee(id);
    }

    @Override
    public List<Employee> readMultipleEmployees(List<Integer> ids) {
        System.out.println("Service - called DAO to Read Multiple Employee");
        if (ids == null || ids.isEmpty() || ids.stream().anyMatch(id -> id <= 0)) {
            throw new IllegalArgumentException("Valid IDs must be provided for reading multiple employees. Provided IDs: " + ids);
        }
        return employeeDAO.readMultipleEmployees(ids);
    }


    @Override
    public List<Employee> readAllEmployees() {
        System.out.println("Service - called DAO to Read All Employee");
        return employeeDAO.readAllEmployees();
    }

    @Transactional
    @Override
    public void updateSingleEmployee(Employee employee) {
        System.out.println("Service - called DAO to Update Single Employee");
        if (employee.getId() == 0 || employee.getId() < 0) {
            throw new IllegalArgumentException("Valid ID must be provided for updating an employee");
        }
        employeeDAO.updateSingleEmployee(employee);
    }

    @Transactional
    @Override
    public void updateMultipleEmployees(List<Employee> employees) {
        System.out.println("Service - called DAO to Update Multiple Employee");
        for (Employee employee : employees) {
            if (employee.getId() == 0 || employee.getId() <= 0 ) {
                throw new IllegalArgumentException("Valid ID must be provided for updating employee: " + employee);
            }
        }
        employeeDAO.updateMultipleEmployees(employees);
    }

    @Transactional
    @Override
    public void deleteSingleEmployee(int id) {
        System.out.println("Service - called DAO to delete Single Employee");
        if (id <= 0) {
            throw new IllegalArgumentException("Valid ID must be provided for deleting an employee. Provided ID: " + id);
        }
        employeeDAO.deleteSingleEmployee(id);
    }

    @Transactional
    @Override
    public void deleteMultipleEmployees(List<Integer> ids) {
        if (ids == null || ids.isEmpty() || ids.stream().anyMatch(id -> id <= 0)) {
            throw new IllegalArgumentException("Valid IDs must be provided for deleting multiple employees. Provided IDs: " + ids);
        }
        System.out.println("Service - called DAO to delete Multiple Employee");
        employeeDAO.deleteMultipleEmployees(ids);
    }

    @Transactional
    @Override
    public void deleteAllEmployees() {
        System.out.println("Service - called DAO to delete All Employee");
        employeeDAO.deleteAllEmployees();
    }
}
