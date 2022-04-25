package com.dataken;

import com.dataken.dao.EmployeeDAO;
import com.dataken.pojo.Details;
import com.dataken.pojo.Employee;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Aspect
public class TenantFilterAspect {

    private static final Logger log = LoggerFactory.getLogger(TenantFilterAspect.class);

    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
    public void openSession() {
    }

    @AfterReturning(pointcut = "openSession()", returning = "obj")
    public void afterOpenSession(Object obj) {
        log.info("****************************************** AFTER OPEN SESSION: " + obj);
    }

    @Pointcut("execution (* com.dataken.services.MainService.calculateSalary(..))")
    public void calculateSalary() {
    }

    @AfterReturning(pointcut = "calculateSalary()", returning = "obj")
    public void afterCalculateSalary(Object obj) {
        Details details = (Details) obj;
        log.info("Value returned: " + details.getSalary());
        details.setSalary(5700);
        boolean global = JerseyConfig.global.get();
        log.info("Session object casted, is global: " + global);
    }

    @Around("execution(* *.queryAllMarker(*))  && @annotation(logAnnot)")
    public Object aroundObject(ProceedingJoinPoint pjp, Log logAnnot) {
        log.info("Inside the Annotation: {}", logAnnot);
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Log logAnnotation = methodSignature.getMethod().getAnnotation(Log.class);
        System.out.println("Annotation: " + Arrays.toString(logAnnotation.args()));

        Object[] args = pjp.getArgs();
        log.info("Method args: {}", args);

        JerseyConfig.global.set(false);
        EmployeeDAO dao = new EmployeeDAO();
        List employees = dao.queryAll(new Details(199));

        JerseyConfig.global.set(true);
        List employees2 = dao.queryAll(new Details(1990));

        employees.addAll(employees2);
        return employees;
    }

}
