package com.example.Emp_Det_Reactive.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Emp_Det_Reactive.entity.Employee;
import com.example.Emp_Det_Reactive.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("employee")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public Flux<Employee> getAll() throws InterruptedException {
        System.out.println(Thread.class);

        return employeeService.getAll().log();
    }

    @PostMapping
    public Mono<Employee> create(@RequestBody Employee employee) {
        return employeeService.create(employee).log();
    }
}
