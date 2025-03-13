package com.restcrud.springboot.cruddemo.service;

import com.restcrud.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    //SERVICE LAYER ---------
    void createSingleEmployee(Employee theEmployee);
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
