# Spring-Data-JPA 설정  
## Spring Data JPA 설정
### 의존성 추가 (pom.xml)
pom.xml  
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```
> 참고)  
 spring-boot-starter-data-jpa 라이브러리를 추가하면 자동으로 tomcat-jdbc가 추가된다고 한다.  
 [스프링 공식 가이드 문서](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html#boot-features-connect-to-production-database)  
   
## 샘플 Table 매핑 Entity, Repository 연결
### Entity
테이블 하나의 컬럼을 클래스의 필드와 매핑시킨다. 주의할 점은 getter/setter가 있어야 데이터를 접근할 수 있다.  
Getter/Setter는 Lombok을 통해 더 간소화 할수 있다.  

```java
package com.share.data.api.playground.jpa.sampleuser;

import javax.persistence.*;

@Entity
@Table(name = "sample_user")
public class SampleUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String vender; // oauth service 업체

    public SampleUserEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

}
``` 
  
#### @GeneratedValue

    
#### @Entity
데이터베이스의 스키마의 내용을 자바 클래스로 표현할 수 있는 대상을 Entity 클래스라고 한다.  
Entity 클래스는 해당 클래스에 @Entity 어노테이션을 선언하는 것으로 엔티티 매니저가 관리해야 할 대상임을 인식시킬수 있다.
즉, 생성한 클래스를 ORM 매핑으로 Spring Data JPA 와 연동시키기 위해서는 @Entity 어노테이션을 사용한다.  
  
#### @Table
@Entity 어노테이션을 사용할 때 실제 테이블명과 클래스명이 다를 경우 @Table 어노테이션으로 실제 클래스명을 직접 지정할 수 있다.  

```java
@Entity
@Table(name = "conn_test_jpa")
public class ConnTestEntity {
    
}
```

### Repository
Repository 는 Entity 단위로 데이터베이스 연산을 수행하는 역할을 한다. SQL로 작성하던 것을 메서드로 사용하게 된다.  
여기서는 개념에 대해 깊이 설명하기보다는 Repository를 생성하고 실행하는 방법만을 다룬다.

#### 1) Repository 인터페이스 생성 (사용자 정의)
1. JpaRepository<T, ID extends Serializable> 인터페이스를 상속하는 사용자 정의 Repository 인터페이스를 생성한다.    
  즉, JpaRepository<SampleUserEntity, Long> 을 상속하는 SampleUserRepository 를 생성한다.
  
2. 그리고 Repository 내부에 쿼리를 실행할 수 잇는 메서드를 작성한다.  
  만약 select ~ where 구문을 수행하고 싶다면 findBy\[Entity내의 필드명\](파라미터) 와 같이 기술한다.    
  즉, SampleUserEntity에 정의한 name으로 특정 값을 가져오려면 _SampleUserEntity findByName (String name);_ 으로 작성하면 된다.

ex)
```java
package com.share.data.api.playground.jpa.sampleuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleUserRepository extends JpaRepository<SampleUserEntity, Long> {

    public SampleUserEntity findByName(String name);
}
```
   
### Repository Test 코드 작성  

#### Test 1) 의존성 주입 정상 동작여부 확인
```java
package com.share.data.api.playground.jpa.sampleuser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SampleUserRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SampleUserRepository sampleUserRepository;

    @Test
    public void di(){

    }
}
```

테스트를 돌려보면 아래와 같은 문구가 나타난다.
```text
Hibernate: drop table conn_test_jpa if exists
Hibernate: drop table sample_user if exists
Hibernate: drop table user_auth_vo if exists
Hibernate: drop sequence if exists hibernate_sequence
Hibernate: create sequence hibernate_sequence start with 1 increment by 1
Hibernate: create table conn_test_jpa (id bigint not null, name varchar(255), primary key (id))
Hibernate: create table sample_user (id bigint not null, name varchar(255), vender varchar(255), primary key (id))
Hibernate: create table user_auth_vo (id bigint not null, password varchar(255), username varchar(255), primary key (id))
```
여기서는 실제 db가 아닌 hsql을 사용하게 된다.

#### Application 동작 확인

postgresql 의존성 추가
```xml
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>
```

##### application.properties에 DB 접속정보 추가
hikari datasource를 사용하지 않을 경우 database 정보를 아래와 같이 입력한다.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/stock_data
spring.datasource.username=postgres
spring.datasource.password=1234
```

##### createClob is not yet implemented 에러 해결
PgConnection이라는 jdbc Driver 관련 클래스에서 createClob() 메서드를 지원하지 않아서 생기는 문제이다
```properties
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

#### Datasourc 테스트
현재 hikari Datasource를 프로젝트에 설정해놓았기 때문에 아래의 코드를 입력한다.
> - DataSource에 @Qualifier로 명시적인 bean의 이름 (hikariDataSource)을 지정해주었다.    
  (bean 설정에서 name을 명시적으로 hikariDataSource로 지정했다.)  
> - Test클래스의 최 상단에 @SpringBootTest로 입력해준다.  
  ~~@DataJpaTest로 실행시 에러가 난다. 자동설정이 아닌 커스텀 설정상태에서 테스트는 SpringBootTest로 수행해야 하는 듯 하다.~~  


> 백기선님 강의를 들은 결과로 
> - @SpringBootTest 는 통합테스트(Integration Test)에서
> - @DataJpaTest 는 Slicing Test에서 
> 사용하는 것을 권장하고 있다.  

> - @SpringBootTest 는 Spring 설정에 실제 등록한 Datasource 설정으로 동작하고  
> - @DataJpaTest 는 h2(인메모리 데이터베이스)로 동작한다.

인메모리 데이터베이스가 더 가볍기 때문이라 한다. 
실 운영 DB에도 영향을 줘서 값이 변하거나 하는 문제때문인지 그런지 추측도 된다..  

```java
package com.share.data.api.playground.jpa.sampleuser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleUserRepositoryTest {

    @Autowired
    @Qualifier("hikariDataSource")
    DataSource hikariDataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SampleUserRepository sampleUserRepository;

    @Test
    public void di() throws SQLException {
        try(Connection conn = hikariDataSource.getConnection()){
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
        }
    }
}
```

DataSource Bean설정에는 아래와 같이 명시적으로 hikari datasoruce를 입력해준다.
> @Bean(name="hikariDatasource") 부분이 위의 테스트 코드에 @Qualifier와 일치하도록 맞춰줘야 한다.

```java
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

    @Bean(name = "hikariDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(){
        DataSource dataSource =
                DataSourceBuilder.create()
                        .type(HikariDataSource.class)
                        .build();


        return dataSource;
    }
}
```
  
만약 Hikari Datasource 설정없이 기본 설정을 사용하고 있다면 아래와 같이 작성한다. 
> - Qualifier 없이 기본적인 Autowired 구문 사용
> - @SpringBootTest가 아닌 @DataJpaTest 어노테이션 사용


```java
@RunWith(SpringRunner.class)
@DataJpaTest
public class SampleUserRepositoryTest {

    @Autowired
    DataSource hikariDataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SampleUserRepository sampleUserRepository;

    @Test
    public void di() throws SQLException {
        try(Connection conn = hikariDataSource.getConnection()){
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
        }
    }
}
```

테스트 결과
```text
postgres
jdbc:postgresql://localhost:5432/stock_data
PostgreSQL JDBC Driver
```




