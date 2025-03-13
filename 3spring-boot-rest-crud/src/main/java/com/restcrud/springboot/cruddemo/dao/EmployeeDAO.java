package com.restcrud.springboot.cruddemo.dao;

import com.restcrud.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();

    //------------With Service Layer----------
    void createSingleEmployee(Employee employee);
    void createMultipleEmployees(List<Employee> employees);

    Employee readSingleEmployee(int id);
    List<Employee> readMultipleEmployees(List<Integer> ids);
    List<Employee> readAllEmployees();

    void updateSingleEmployee(Employee employee);
    void updateMultipleEmployees(List<Employee> employees);

    void deleteSingleEmployee(int id);
    void deleteMultipleEmployees(List<Integer> ids);
    void deleteAllEmployees();


}
