package com.dataken.services;

import com.dataken.JerseyConfig;
import com.dataken.Log;
import com.dataken.dao.EmployeeDAO;
import com.dataken.pojo.Details;
import com.dataken.pojo.Employee;
import com.dataken.pojo.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/main")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MainService {
    private static final Logger log = LoggerFactory.getLogger(MainService.class);

    @Path("/hello/{message}")
    @GET
    public String sayHello(@PathParam("message") String message) {
        return "Hello from main service, msg: " + message;
    }

    @Path("/save")
    @POST
    @Modifier
    public void saveGlobalEmployee(Employee employee, @QueryParam("global") boolean global
            , @QueryParam("tenantId") String tenantId) {
        log.info(">>> THE GLOBAL PARAM RECIEVED: {}, Tenant ID: {}", global, tenantId);
        JerseyConfig.global.set(global);
        JerseyConfig.tenantContext.set(tenantId);
        EmployeeDAO dao = new EmployeeDAO();
        dao.persist(employee);
        Details details = calculateSalary();
        log.info("Salary calculated: " + details.getSalary());
        List<Employee> empList = (List<Employee>) queryAllMarker(details);
        log.info("Query all marker function invoked, return value: " + empList);
    }

    public Details calculateSalary() {
        return new Details(1000);
    }

    @Log(msg="doingSomething", args={EmployeeDAO.class})
    public Object queryAllMarker(Details details) {
        log.info("Inside the queryAllMarker");
        return "blah";
    }

}
