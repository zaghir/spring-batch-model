package com.zaghir.batch.batchspringmodel.datasource.impl;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaghir.batch.batchspringmodel.datasource.DatasourceConfiguration;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class DatabaseConfiguration implements DatasourceConfiguration {
	
	@Autowired
    private Environment env;

    private static final String JDBC_DRIVERCLASSNAME = "jdbc.driverClassName";
    
    @Bean
    @Primary
    public DataSource dataSourceOne() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty(JDBC_DRIVERCLASSNAME));
        dataSource.setUrl(env.getProperty("jdbc.database1.url"));
        dataSource.setUsername(env.getProperty("jdbc.database1.username"));
        dataSource.setPassword(env.getProperty("jdbc.database1.password"));

        return dataSource;
    }

    @Override
    @Bean
    public DataSource dataSourceTwo() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty(JDBC_DRIVERCLASSNAME));
        dataSource.setUrl(env.getProperty("jdbc.database2.url"));
        dataSource.setUsername(env.getProperty("jdbc.database2.username"));
        dataSource.setPassword(env.getProperty("jdbc.database2.password"));

        return dataSource;
    }
    
    @Override
    @Bean
    public DataSource dataSourceTree() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty(JDBC_DRIVERCLASSNAME));
        dataSource.setUrl(env.getProperty("jdbc.database3.url"));
        dataSource.setUsername(env.getProperty("jdbc.database3.username"));
        dataSource.setPassword(env.getProperty("jdbc.database3.password"));

        return dataSource;
    }


}
