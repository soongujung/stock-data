package com.stock.data.config.datasource.jpa;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/application-real-db.properties")
public class DataSourceConfig {

	@Value("${spring.datasource.hikari.jdbc-url}")
	private String jdbcUrl;

	@Value("${spring.datasource.hikari.username}")
	private String dbUserName;

	@Value("${spring.datasource.hikari.password}")
	private String dbPassword;

	@Value("${spring.datasource.hikari.driver-class-name}")
	private String jdbcClassName;

	@Bean(name="dataSource")
	@Primary
	@ConfigurationProperties("spring.datasource.hikari")
	public DataSource dataSource(){
		final HikariDataSource dataSource = new HikariDataSource();

		dataSource.setConnectionTestQuery("SELECT * FROM user");
		dataSource.setMaximumPoolSize(200);
		dataSource.setMaxLifetime(1000*60*30);
		dataSource.setConnectionTimeout(1000*60*30);
		dataSource.setMinimumIdle(10);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(dbUserName);
		dataSource.setPassword(dbPassword);
		dataSource.setLeakDetectionThreshold(1000*60*30);
		dataSource.setIdleTimeout(1000*60*30);
		dataSource.setAutoCommit(true);
		dataSource.setPoolName("stockdata_hikari_cp__api");

		return dataSource;
	}
}
