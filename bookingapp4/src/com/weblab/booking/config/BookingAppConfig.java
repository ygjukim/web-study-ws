package com.weblab.booking.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({
	"com.weblab.booking.dao",
	"com.weblab.booking.service",
	"com.weblab.booking.controller",
	"com.weblab.booking.advice"
})
@PropertySource({"classpath:com/weblab/booking/config/db.properties"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
public class BookingAppConfig {
	@Autowired
	Environment env;

	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName(env.getProperty("db.driverName"));
		ds.setUrl(env.getProperty("db.url"));
		ds.setUsername(env.getProperty("db.userName"));
		ds.setPassword(env.getProperty("db.password"));
		ds.setInitialSize(5);
		ds.setMaxActive(15);
		ds.setMaxIdle(15);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(1000*60*3);
		ds.setTimeBetweenEvictionRunsMillis(1000*10);
		return ds;
	}
	
	@Bean
	@Scope("prototype")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean PlatformTransactionManager txManager() {
		DataSourceTransactionManager txManager
			= new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}
}
