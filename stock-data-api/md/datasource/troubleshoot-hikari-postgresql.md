# Hikari Datasource 연동 Trouble Shoots
Hikari Datasource를 postgresql 과 연동할때 생기는 문제 해결과정 문서

## Datasource 커스텀 설정시 적용해야 하는 설정값들
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

## 참고자료들
### createClob() is not yet implemented
PgConnection이라는 jdbc Driver 관련 클래스에서 createClob() 메서드를 지원하지 않아서 생기는 문제이다
 - [temp.use_jdbc_metadata_defaults=true, \[국내 블로그\]](https://ryudaewan.wordpress.com/2018/02/08/pgsql_jpa/)
   spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=true 로 세팅해야 하는 이유와 과정에 대해 설명되어 있다.  
   이걸로 해결되지는 않았다. application.properties에서 이 설정을 주석처리해야 실행이 되긴 한다. 
   하지만 참고를 위해 남겨둔다.   
   non_contextual_creation이라는 설정이 무엇인지 알아봐야 할 것 같아서다.    
   
 - [non_cntextual_creation=true](https://medium.com/msolo021015/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8%EB%A1%9C-%EC%8B%9C%EC%9E%91%ED%95%98%EB%8A%94-%ED%85%8C%EC%8A%A4%ED%8A%B8-jpa-part-2-ef3c302cff52)  
   도움이 된 설정이다.  
   spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true 를 입력해준다.  

 - [temp.use_jdbc_metadata_defaults=true, \[github issues\]](https://github.com/pgjdbc/pgjdbc/issues/1102)  
   hibernate.jdbc.lob.non_contextual_creation  
   hibernate.temp.use_jdbc_metadata_defaults=true  
   에 대한 설명들을 읽을수 있게 된다.  
   
     
     
> 구글 검색어  
Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.


