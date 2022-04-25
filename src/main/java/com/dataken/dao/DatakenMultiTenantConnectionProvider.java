package com.dataken.dao;

import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatakenMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private static final Logger log = LoggerFactory.getLogger(DatakenMultiTenantConnectionProvider.class);

    private static final String URL = "jdbc:mysql://localhost:9306/%s";
    private static final long serialVersionUID = 4616848739855260009L;

    private final ConnectionProvider globalConnectionProvider = buildConnectionProvider( "defaultTenant" );
    private final ConnectionProvider tenantConnectionProvider = buildConnectionProvider( "dataken" );


    public DatakenMultiTenantConnectionProvider() {
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return globalConnectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return tenantIdentifier.equalsIgnoreCase("TENANT")
                ? tenantConnectionProvider
                : globalConnectionProvider;
    }

    private static ConnectionProvider buildC3P0ConnectionProvider(String dbName) {
        Map<String, String> props = new HashMap<>();
        Configuration cfg = MainDAO.getConfig();
        props.put( "hibernate.connection.driver_class", cfg.getProperty("hibernate.connection.driver_class") );
        // Inject dbName into connection url string.
        props.put( "hibernate.connection.url", String.format(URL, dbName) );
        props.put( "hibernate.connection.username", cfg.getProperty("hibernate.connection.username") );
        props.put( "hibernate.connection.password", cfg.getProperty("hibernate.connection.password") );

        props.put( "hibernate.c3p0.min_size", cfg.getProperty("hibernate.c3p0.min_size") );
        props.put( "hibernate.c3p0.max_size", cfg.getProperty("hibernate.c3p0.max_size") );
        props.put( "hibernate.c3p0.timeout", cfg.getProperty("hibernate.c3p0.timeout") );
        props.put( "hibernate.c3p0.idle_test_period", cfg.getProperty("hibernate.c3p0.idle_test_period") );

        C3P0ConnectionProvider connectionProvider = new C3P0ConnectionProvider();
        connectionProvider.configure(props);
        return connectionProvider;
    }

    private static ConnectionProvider buildConnectionProvider(String dbName) {
        Properties props = new Properties( null );
        props.put( "hibernate.connection.driver_class", "com.mysql.jdbc.Driver" );
        // Inject dbName into connection url string.
        props.put( "hibernate.connection.url", String.format( URL, dbName ) );
        props.put( "hibernate.connection.username", "dataken" );
        props.put( "hibernate.connection.password", "D2tak3n@AI" );

        // Note that DriverManagerConnectionProviderImpl is an internal class.  However, rather than creating
        // a ConnectionProvider, I'm using it for simplicity's sake.
        // DriverManagerConnectionProviderImpl obtains a Connection through the JDBC Driver#connect
        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
        connectionProvider.configure( props );
        return connectionProvider;
    }

}
