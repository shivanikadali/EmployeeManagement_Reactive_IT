package com.example.Emp_Det_Reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Emp_Det_Reactive.entity.Employee;
import com.example.Emp_Det_Reactive.repositories.EmployeeRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public Flux<Employee> getAll() throws InterruptedException {
        System.out.println(Thread.class+"____________________________GetAll");
        Thread.sleep(2020);
        System.out.println(Thread.class+"____________________________GetAll");
        return employeeRepo.findAll();
    }

    public Mono<Employee> create(Employee employee) {
        System.out.println(Thread.class+"____________________________Create");
        return employeeRepo.save(employee);
    }

}
