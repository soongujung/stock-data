package com.stock.data.config.datasource.mybatis;

import com.stock.data.config.datasource.DataSourceConfig;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@Import(DataSourceConfig.class)
public class MybatisConfig {

	@Autowired
	private DataSourceConfig dataSourceConfig;

	private static final String MYBATIS_MAPPER_LOCATIONS = "classpath:mybatis/mapper/**/*.xml";
	private static final String MYBATIS_CONFIG_LOCATIONS = "classpath:mybatis/config/mybatis-config.xml";

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		sqlSessionFactoryBean.setDataSource(dataSourceConfig.dataSource());

		sqlSessionFactoryBean.setMapperLocations(
			new PathMatchingResourcePatternResolver().getResources(MYBATIS_MAPPER_LOCATIONS)
		);

		sqlSessionFactoryBean.setConfigLocation(
			resolver.getResource(MYBATIS_CONFIG_LOCATIONS)
		);

		return sqlSessionFactoryBean.getObject();
	}

	@Primary
	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "batchSqlSessionTemplate")
	public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
		sqlSessionTemplate.getSqlSessionFactory().getConfiguration().setDefaultFetchSize(200);
		return sqlSessionTemplate;
	}
}
