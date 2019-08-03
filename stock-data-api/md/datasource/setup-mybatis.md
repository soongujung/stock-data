# Mybatis 설정
  
## SqlSessionTemplate, SqlSessionFactory, SqlSession 객체

- sqlSession   
  Mybatis는 하나의 쿼리 단위를 실행할때 sqlSession 객체를 사용한다.  
  
- sqlSessionFactory  
  이름에서 알수 있듯이 Factory 패턴을 사용했을 거다. 즉, SqlSession 객체를 찍어내는 녀석이다.  
  즉, SqlSession 타입의 인스턴스를 찍어내주는 녀석이다.  
  
- sqlSessionTemplate  
  SqlSessionFactory, SqlSession을 Spring에서 사용하고자 할때 SqlSessionTemplate을 사용한다.  
  즉, SqlSessionTemplate는 mybatis-spring-boot-starter에서 제공하는 클래스라는 것을 짐작할 수 있다.    
  그 이유는 예외처리를 Mybatis가 아닌 Spring의 DataAccessException으로 치환시켜주는 역할을 해주기 때문이다.  
  
- sqlSessionFactoryBuilder  
  Spring이 아닌 다른곳에서 사용하고자 할때 SqlSessionFactoryBuilder를 사용한다.  

## 참고자료
> 검색어(구글)   
spring boot mybatis postgresq

- [Mybatis 공식 레퍼런스](http://www.mybatis.org/mybatis-3/ko/getting-started.html)  
    
- [Spring Boot, Mybatis, HikariCP, Postgresql - \[가리사니 님 블로그 \]](https://gs.saro.me/dev?page=6&tn=469)  
  보통 웹 상에 Mybatis, Spring Boot 연동 자료를 보면 불필요한 의존성까지 추가하는 자료들이 있다.  
    
  여기서는 필요한 것만 의존성으로 추가하면 된다는 것을 알려주고 있고  
    
  다중 DB 접속이 있다는 가정하에 application.properties가 아닌 hikari_pgsql.properties 파일을 따로 생성해 설정하는 과정을 알려주고 있다.  
    
  추후 공부를 하거나, 새로운것을 만들어야 하는데 자료가 없다 싶으면 이 블로그를 참고해봐야 할듯하다.  
    
> 사실 Mybatis를 Spring에서 사용하기 위해서는 mybatis-spring 또는 mybatis-spring-boot-starter만 필요하다.  
  SqlSessionTemplate이 Spring에서 사용하기 위한 SqlSessionFactory를 이용하기 위한 도구이기 때문이다.  
  (다른 입문 책들에 이러한 내용들이 설명되어 있기 때문에 조금만 차분히 읽어보면 이 내용이 어떤 내용인지 알수 있다.)  
   

## Troouble Shoots
Mybatis 설정을 하면서 Trouble Shoot을 할수 있도록 도와준 참고자료들이다.

### 1) ClassNotFoundException: org.apache.ibatis.annotations.Mapper   
- [Mybatis java.lang.NoClassDefFoundError, \[국내 블로그\]](https://developer-kylee.tistory.com/5)  
  에러로그가 Mybatis.java.lang.NClassDefFoundError 까지만 일치했지만 문제를 해결해나가는 과정에 도움이 되었던 자료.  
  
> 검색어 
  ClassNotFoundException: org.apache.ibatis.annotations.Mapper
  

### 2) Datasource 커스텀 설정시 적용해야 하는 설정값들
> 참고자료  
  [Spring Boot & HikariCP Datasource 연동하기, \[기억보단 기록을\]](https://jojoldu.tistory.com/296)

보통 **자동설정 시**에는 spring.datasource.url 값을 설정하는데,  
**커스텀 설정 시**에는 spring.datasource.jdbc-url 이나 spring.datasource.hikari.jdbc-url에 세팅하면 된다.  
spring.datasource.url을 수정하는 것은 오해의 소지도 있고 불편하기 때문에 spring.datasource.hikari를 사용하는 것.  

spring.datasource.hikari를 application.properties에서 사용하고 있다면  
설정 코드 내에서 아래의 코드를 추가해줘야 한다.  
```java
public class SomeDataConfig{
    
    // ...
    // ...
    
    @Bean(name="dataSource")
    @Primary
    @Configuration("spring.datasource.hikari")
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .build();
    }    
}
```  