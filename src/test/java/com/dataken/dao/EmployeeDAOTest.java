package com.dataken.dao;

import com.dataken.JerseyConfig;
import com.dataken.pojo.Employee;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeDAOTest {

    @Test
    public void testPersist() {
        JerseyConfig.tenantContext.set("tenant1");
        Employee employee = new Employee();
        employee.setName("Lokesh");
        employee.setSalary(100.11);
        employee.setGlobal(true);
        EmployeeDAO dao = new EmployeeDAO();
        dao.persist(employee);
    }

}
