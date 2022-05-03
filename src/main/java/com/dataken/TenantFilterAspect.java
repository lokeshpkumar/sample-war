package com.dataken;

import com.dataken.dao.EmployeeDAO;
import com.dataken.pojo.Details;
import com.dataken.pojo.Employee;
import com.dataken.pojo.Modifier;
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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Aspect
public class TenantFilterAspect {

    private static final Logger log = LoggerFactory.getLogger(TenantFilterAspect.class);

    @Context
    private HttpServletRequest request;

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

    @Around("execution (* com.dataken.services.*.*(..)) && @annotation(modifier)")
    public void modifier(ProceedingJoinPoint pjp, Modifier modifier) {
        Object target = pjp.getTarget();
        HttpServletRequest request = null;
        try {
            Field fieldRequest = target.getClass().getDeclaredField("request");
            fieldRequest.setAccessible(true);
            request = (HttpServletRequest) fieldRequest.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        log.info("Before executing the saveGlobalEmployeeMethod: {} <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", request.getSession());
        try {
            pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        log.info("After executing the saveGlobalEmployee method >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
