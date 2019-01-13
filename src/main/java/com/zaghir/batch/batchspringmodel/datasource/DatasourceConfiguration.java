package com.zaghir.batch.batchspringmodel.datasource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
public interface DatasourceConfiguration {
	
	@Bean
    public DataSource dataSourceOne();

    @Bean
    public DataSource dataSourceTwo();

    @Bean
	public DataSource dataSourceTree();

}
