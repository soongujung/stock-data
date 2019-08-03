package com.share.data.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DataSourceConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String jdbcUrl;

    @Value("${spring.datasource.hikari.username}")
    private String dbUserName;

    @Value("${spring.datasource.hikari.password}")
    private String dbPassword;

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String jdbcClassName;

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    public HikariConfig hikariConfig(){
//        return new HikariConfig();
//    }

    /**
     * https://jojoldu.tistory.com/296
     * https://stackoverflow.com/questions/23287462/spring-boot-fails-to-load-datasource-using-postgresql-driver
     * http://blog.naver.com/PostView.nhn?blogId=admass&logNo=220979632501&beginTime=0&jumpingVid=&from=search&redirect=Log&widgetTypeCall=true
     * @return
     */
    @Bean(name = "hikariDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(){
        DataSource dataSource =
                DataSourceBuilder.create()
                        .type(HikariDataSource.class)
                        .build();

//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setUsername(dbUserName);
//        hikariConfig.setPassword(dbPassword);
//        hikariConfig.addDataSourceProperty("url", jdbcUrl);
//        hikariConfig.setDataSourceClassName(jdbcClassName);
//        hikariConfig.setLeakDetectionThreshold(2000);
//        hikariConfig.setPoolName("hikari-sgjung");
//
//        final HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
}
