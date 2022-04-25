package com.dataken.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * MainDAO.java
 *
 */
public abstract class MainDAO implements java.io.Serializable {

    private static final Logger log = LoggerFactory.getLogger(MainDAO.class);
    private static final String tracePrefix = "[" + MainDAO.class.getSimpleName() + "]: ";
    protected static SessionFactory sessionFactory = buildSessionFactory("hibernate.cfg.xml");
    private static Configuration configuration;

    private static SessionFactory buildSessionFactory(String cfgPath) {
        try {
            if (sessionFactory == null) {
                log.info(tracePrefix + "-------- Building Session Factory ---------");
                StandardServiceRegistry standardRegistry = null;
                File file = new File(cfgPath);
                standardRegistry = new StandardServiceRegistryBuilder().configure(file).build();

                Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
                log.info(tracePrefix + "Metadata built");
                sessionFactory = metaData.getSessionFactoryBuilder().build();
            }
            return sessionFactory;
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed",ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Configuration getConfig() {
        if (configuration == null) {
            configuration = new Configuration().configure(new File("hibernate.cfg.xml"));
        }
        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}
