# Spring Data JPA 기본 개념
## Spring Data JPA
### Spring Data JPA , hibernate, JPA ?
자바 진영에서 자주 쓰이는 ORM 기술로는 JPA, Hibernate가 있다.  
Spring Data JPA와 hibernate, JPA 를 별개의 기술로 설명하는 듯하다.  
Spring Data JPA는 Hibernate, JPA 에 비해 상대적으로 많이 알려져 있지 않다고 한다.
Spring Data JPA를 사용한다면, 적용이 수월하고, 추상화가 잘되어 있어서 데이터베이스를 Redis, MongoDB와 같은 NoSQL로 변경하더라도
Repository 인터페이스에 정의된 동일한 메서드 시그니처를 사용할 수 있으므로 ORM 도구 도입을 검토중이라면 상대적으로 적은 비용으로 사용해볼수 있는 도구다.
  
Spring Data JPA 프로젝트가 Spring Data의 하위 프로젝트인 것처럼 JPA와 Spring Data JPA는 모두 데이터 저장소 처리를 위해 만들었지만, 실제로는 목표로 하고 있는 대상이 서로 다르다.  

### JPA
JPA 스펙을 만들었을 당시 NoSQL이 존재하지 않았고, ORM이라는 단어의 뜻 처럼 최초에 스펙을 만들게 된 주요 요구사항은 
객체와 관계형 데이터페이스의 매핑이다. 따라서 관계형 데이터베이스에 한정되어 있다.   
 
### Spring Data
JPA가 관계형 데이터베이스에 한정되어 있는 것에 대해 Spring 개발팀은 Spring One(스프링 프로젝트 개발자 컨퍼런스)에서
NoSQL유형 중 하나인 NEO4j(그래프형 데이터베이스)를 스프링에 포함시키기 위한 시도를 하면서 NoSQL을 포함한 새로운 스펙을 제정하게 되었다.
  
그 스펙이 바로 Spring Data다.  
  
Spring Data는 NoSQL 또는 RDBMS 어느 한쪽만을 목표로 하지 않으므로 Spring Data의 추상화된 인터페이스를 통해서 
MySQL ElasticSearch, Redis 등 다양한 저장소를 활용할 수 있도록 해준다.  

> 참고자료  
 스프링부트로 배우는 자바 웹 개발, 윤석진, jpub
 
## 어노테이션
### @Entity
데이터베이스의 스키마의 내용을 자바 클래스로 표현할 수 있는 대상을 Entity 클래스라고 한다.  
Entity 클래스는 해당 클래스에 @Entity 어노테이션을 선언하는 것으로 엔티티 매니저가 관리해야 할 대상임을 인식시킬수 있다.
즉, 생성한 클래스를 ORM 매핑으로 Spring Data JPA와 연동시키기 위해서는 @Entity 어노테이션을 사용한다.  

### @Table
@Entity 어노테이션을 사용할 때 실제 테이블명과 클래스명이 다를 경우 @Table 어노테이션으로 실제 클래스명을 직접 지정할 수 있다.
```java
@Entity
@Table(name = "conn_test_jpa")
public class ConnTestEntity {
    
}
```
 