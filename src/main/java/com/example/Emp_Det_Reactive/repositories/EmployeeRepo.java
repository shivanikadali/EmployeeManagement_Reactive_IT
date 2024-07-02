package com.example.Emp_Det_Reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.Emp_Det_Reactive.entity.Employee;

public interface EmployeeRepo extends ReactiveCrudRepository<Employee, Integer> {

}
