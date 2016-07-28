/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.config;

import javax.sql.DataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
//@EnableAspectJAutoProxy
//@EnableAsync
//@EnableScheduling
//@EnableCaching(mode = AdviceMode.ASPECTJ)
@PropertySource(value = { "classpath:environmental.properties" })
//@ComponentScan(basePackages = { ApplicationConfig.ALL_PACKAGES })
public class ApplicationConfig {

    protected static final String ALL_PACKAGES = "com.performancecarerx";

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
            final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
            dataSourceLookup.setResourceRef(true);

            DataSource jndiDataSource = null;

            try {
                    jndiDataSource = dataSourceLookup.getDataSource(environment.getRequiredProperty("spring.datasource.jndi"));
            } catch (Exception e) {
                    LOGGER.debug("Can't find JNDI Data Source", e); 
            }

            if (jndiDataSource == null) {
                    LOGGER.info("JNDI Data Source Not Found, Using Embedded.");

                    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                    dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
                    dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
                    dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
                    dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));

                    return dataSource;
            }

            return jndiDataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
            DataSourceTransactionManager txManager = new DataSourceTransactionManager();
            txManager.setDataSource(dataSource());
            return txManager;
    }

    /*Making dataSource() a bean is necessary for jooq to play nice with Spring transactions, but it throws exception  
      Error creating bean with name 'transactionAwareDataSource': Requested bean is currently in creation: Is there an unresolvable circular reference?
     */
    /*@Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
            TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy();
            proxy.setTargetDataSource(dataSource());
            return proxy;
    }*/

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
            TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy();
            proxy.setTargetDataSource(dataSource());
            return new DataSourceConnectionProvider(proxy);
    }

//	@Bean
//	public JooqExceptionTranslator exceptionTranslator() {
//		return new JooqExceptionTranslator();
//	}

    @Bean
    public DefaultConfiguration jooqConfig() {
            DefaultConfiguration config = new DefaultConfiguration();
            config.setSQLDialect(SQLDialect.MYSQL);
            config.setConnectionProvider(connectionProvider());
//            config.setExecuteListenerProvider(new DefaultExecuteListenerProvider(exceptionTranslator()));
            return config;
    }

    @Bean
    public DefaultDSLContext dslContext() {
            return new DefaultDSLContext(jooqConfig());
    }
}
