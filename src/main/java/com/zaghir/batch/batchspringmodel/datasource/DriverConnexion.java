package com.zaghir.batch.batchspringmodel.datasource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@EnableBatchProcessing
public class DriverConnexion {	

	@Autowired
	private DatasourceConfiguration datasources;
	
	public DatasourceConfiguration getDatasources() {
		return datasources;
	}
	public void setDatasources(DatasourceConfiguration datasources) {
		this.datasources = datasources;
	}

	@Bean
	@Qualifier(value="jdbctemplateDatabase1")
	public JdbcTemplate jdbctemplateDatabase1(){
		JdbcTemplate j = new JdbcTemplate(datasources.dataSourceOne());
		return j ;		
	}
	
	@Bean
	@Qualifier(value="jdbctemplateDatabase2")
	public NamedParameterJdbcTemplate jdbctemplateDatabase2(){
		NamedParameterJdbcTemplate j = new NamedParameterJdbcTemplate(datasources.dataSourceTwo());
		return j ;		
	}
	
	@Bean
	@Qualifier(value="jdbctemplateDatabase3")
	public JdbcTemplate jdbctemplateDatabase3(){
		JdbcTemplate j = new JdbcTemplate(datasources.dataSourceTree());
		return j ;		
	}

}
