package com.dataken.pojo;

public class Details {

    private double salary;

    public Details(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Details{" +
                "salary=" + salary +
                '}';
    }
}
