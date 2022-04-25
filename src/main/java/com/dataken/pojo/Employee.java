package com.dataken.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee extends Person {

    private static final long serialVersionUID = 793772646277167967L;
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name=" + getName() + ", Tenant" + getTenantId() +
                '}';
    }
}
