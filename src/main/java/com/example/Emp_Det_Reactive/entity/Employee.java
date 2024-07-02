package com.example.Emp_Det_Reactive.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("EmployeeDetailsManagement")
public class Employee {

    @Id
    @Column
    private int empNo;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

}
