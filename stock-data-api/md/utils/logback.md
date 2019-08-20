# logback, log4jdbc
> - 참고자료   
> [스프링 부트 시작하기, 김인우 저](http://www.yes24.com/Product/Goods/70893395?scode=032&OzSrank=1)

## logback
log4j의 성공에 힘입어 Log4j 개발자는 로거에 대해 좀더 깊은 프로젝트를 시작했고, 그 결과 Log4j를 토대로 Logback을 만들게 되었다.    
Logback은 로깅 구현체중 하나로 slf4j(Simple Logging Facade for Java)를 사용한다.    
slf4j는 자바의 다양한 로그 모듈들의 추상체이다.(엄밀히 말하면 java의 interface와 비슷한 역할을 한다.)  
slf4j의 API를 이용할 경우 실제 로깅을 담당하는 로깅 구현체의 종류와 상관없이 일관된 로그 코드를 작성할 수 있다.  
로그 출력 등 로깅 코드는 slf4j를 이용하면 내부적으로는 Logback이나 log4j2와 같은 로깅 구현체가 그 기능을 구현한다.

### Logback의 장점
- 오랫동안 널리 사용되고 검증된 Log4j를 기반으로 하고 있다.
- Log4j에 비해 성능은 약 10배 정도 빠르고 메모리 사용량도 적다.
- Log4j부터 진행한 테스트 경험을 토대로 더 광범위한 테스트를 통해 검증되었다.
- 로그 설정이 변경될 경우 서버를 재시작하지 않더라도 바로 반영된다. Log4j와 같은 로그 라이브러리는 로그 설정을 변경하면 서버를 재시작해야 반영되었다.   
  하지만 Logback은 로그 설정이 변경되면 내부에 설정변화를 감지하는 별도의 스레드가 존재하기 때문에 서버의 재시작없이 바로 반영된다. 
  이 스레드는 서버에 초당 100만번이 넘는 요청을 하더라도 어플리케이션의 성능에 큰 영향을 끼치지 않는다.


### logback-spring.xml 
**(src/main/resources/logback-spring.xml)**  
logback-spring이라고 파일명을 지어야 하는지 이에 대해서 검색해볼것!!
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <Pattern>%d %5p [%c] %m%n</Pattern>
        </encoder>
    </appender>

    <appender name="console-infolog" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <Pattern>%d %5p %m%n</Pattern>
        </encoder>
    </appender>

    <!-- 로거 -->
    <logger name="api" level="DEBUG" appender-ref="console"/>

    <!-- 루트 로거 -->
    <root level="off">
        <appender-ref ref="console"></appender-ref>
    </root>

</configuration>
```

- appender  
    로그를 어디에 출력할지(콘솔, 파일기록, DB 저장등)을 지정
- encoder  
    appender에 포함되어 출력할 로그를 지정한 형식으로 변환하는 역할을 수행
- logger  
    level 속성으로 출력할 로그의 레벨을 조전ㄹ해 appender에 전달한다.
    
    
### log 레벨
- trace  
    모든 로그를 출력  
- debug  
    개발할 때 디버그 용도로 사용  
- info  
    상태 변경 등과 같은 정보성 메시지를 나타낸다.  
- warn  
    프로그램의 실행에는 문제가 없지만 추후 시스템 에러의 원인이 될 수 있다는 경고성 메시지를 리턴  
- error  
    요청을 처리하던 중 문제가 발생한 것을 의미  

### 기본 사용법
```java
public class SomeClass{
    private Logger logger = LoggerFactory.getLogger(SomeClass.class);
    
    public void doSomething(){
        logger.debug("do Something... ");
    }
}
```

## log4jdbc
쿼리를 로그에 정렬되어 출력하기 위해 아주 아주~ 필요한 요소다..
### pom.xml    
```xml
        <dependency>
            <groupId>org.bgee.log4jdbc-log4j2</groupId>
            <artifactId>log4jdbc-log4j2-jdbc4.1</artifactId>
            <version>1.16</version>
        </dependency>
```     

### log4jdbc.log4j2.properties
**(src/main/resources/log4jdbc.log4j2.properties)**  
```properties
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
log4jdbc.dump.sql.maxlinelength=0
```

### application.properties
**(src/main/resources/application.properties)**  
```properties
# 변경 전
#spring.datasource.hikari.driver-class-name=org.postgresql.Driver
# 변경 후
spring.datasource.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy

# 변경 전
#spring.datasource.hikari.jdbc-url=jdbc:postgresql://localhost:5432/stock_data
# 변경 후
spring.datasource.hikari.jdbc-url=jdbc:log4jdbc:postgresql://localhost:5432/stock_data
```

### logback-spring.xml
아래의 내용을 추가한다.
```xml
    <logger name="jdbc.sqlonly" level="INFO" appender-ref="console-infolog"/>
    <logger name="jdbc.resultsettable" level="INFO" appender-ref="console-infolog"/>
```
- jdbc.sqlonly  
    SQL을 보여준다. Prepared Statement의 경우 관련된 파라미터는 자동으로 변경되어 출력된다.  
    
- jdbc.sqltiming  
    SQL문과 SQL문의 실행시간을 밀리초(millisecond) 단위로 보여준다.  
    
- jdbc.audit  
    ResultSets를 제외한 모든 JDBC 호출정보를 보여준다. 매우 많은 로그가 발생되기 때문에 JDBC 관련 문제를 추적하기 위한 것이 아니라면 일반적으로 사용되지 않는다.  
    
- jdbc.resultset  
    ResultSets를 포함한 모든 JDBC 호출정보를 보여 주기 때문에 jdbc.audit보다 더 많은 로그가 생성된다.  
    
- jdbc.resulttable    
    SQL의 조회결과를 테이블로 보여준다.  
    
- jdbc.connection
    Connection의 연결과 종료에 관련된 로그를 보여준다. Connection 누수(leak)문제를 해결하는데 도움이 된다.  
    

