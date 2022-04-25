package com.dataken.dao;

import com.dataken.JerseyConfig;
import com.dataken.pojo.Details;
import com.dataken.pojo.DynamicTable;
import com.dataken.pojo.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EmployeeDAO extends MainDAO {

    private static final Logger log = LoggerFactory.getLogger(EmployeeDAO.class);
    private static final String ANNOTATION_METHOD = "annotationData";
    private static final String ANNOTATION_FIELDS = "declaredAnnotations";
    private static final String ANNOTATIONS = "annotations";

    public void persist(Employee emp) {
        Session session = null;
        Transaction tx = null;
        try {
            String tenant = getTenantIdentifier();
            log.info("Global is: {}, Tenant is: {}", JerseyConfig.global.get(), tenant);
            session = sessionFactory.openSession();
            log.info("Got the hibernate session object ...");
            tx = session.beginTransaction();
            Table tableAnnotation = emp.getClass().getAnnotation(Table.class);
            log.info("Table in annotation: {}, Schema: {}", tableAnnotation.name(), tableAnnotation.schema());
            Table newVal = new DynamicTable("ABCD", "blah", "blah");

            alterAnnotationValueJDK8(Employee.class, Table.class, newVal);
            tableAnnotation = emp.getClass().getAnnotation(Table.class);
            log.info("--- After Table in annotation: {}, Schema: {}", tableAnnotation.name(), tableAnnotation.schema());

            session.save(emp);
            tx.commit();
            log.info("Saved the employee: " + emp.getName());
        } catch (Exception ex) {
            log.error("Failed saving the employee", ex);
            if ( tx != null ) {
                tx.rollback();
            }
        } finally {
            if ( session != null ) {
                session.close();
            }
        }
    }

    public List<Employee> queryAll(Details details) {
        log.info("Input received: " + details);
        Transaction tx = null;
        Session session = null;
        try {
            String tenant = getTenantIdentifier();
            log.info("Global is: {}, Tenant is: {}", JerseyConfig.global.get(), tenant);
            session = sessionFactory.openSession();
            log.info("Got the hibernate session object ...");
            tx = session.beginTransaction();
            List<Employee> employeeList = session.createQuery("From Employee e").list();
            tx.commit();
            log.info("Queried all the employee: " + employeeList.size());
            return employeeList;
        } catch (Exception ex) {
            log.error("Failed querying the employees", ex);
            if ( tx != null ) {
                tx.rollback();
            }
        } finally {
            if ( session != null ) {
                session.close();
            }
        }
        return Collections.emptyList();
    }

    private String getTenantIdentifier() {
        return JerseyConfig.global.get() ? "GLOBAL" : "TENANT";
    }

    @SuppressWarnings("unchecked")
    public static void alterAnnotationValueJDK8(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
        try {
            Method method = Class.class.getDeclaredMethod(ANNOTATION_METHOD, null);
            method.setAccessible(true);

            Object annotationData = method.invoke(targetClass);

            Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
            annotations.setAccessible(true);

            Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
            map.put(targetAnnotation, targetValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
