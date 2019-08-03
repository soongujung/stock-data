# Hikari Datasource 연동 Trouble Shoots
Hikari Datasource를 postgresql 과 연동할때 생기는 문제 해결과정 문서

## 참고자료들
### createClob() is not yet implemented
 - [temp.use_jdbc_metadata_defaults=true, \[국내 블로그\]](https://ryudaewan.wordpress.com/2018/02/08/pgsql_jpa/)    
   spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=true 로 세팅해야 하는 이유와 과정에 대해 설명되어 있다.  
   도움이 된 설정이다.  
   
 - [non_cntextual_creation=true](https://medium.com/msolo021015/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8%EB%A1%9C-%EC%8B%9C%EC%9E%91%ED%95%98%EB%8A%94-%ED%85%8C%EC%8A%A4%ED%8A%B8-jpa-part-2-ef3c302cff52)  
   이걸로 해결되지는 않았다. application.properties에서 이 설정을 주석처리해야 실행이 되긴 한다. 
   하지만 참고를 위해 남겨둔다.  
   non_contextual_creation이라는 설정이 무엇인지 알아봐야 할 것 같아서다.

 - [temp.use_jdbc_metadata_defaults=true, \[github issues\]](https://github.com/pgjdbc/pgjdbc/issues/1102)  
   hibernate.jdbc.lob.non_contextual_creation  
   hibernate.temp.use_jdbc_metadata_defaults=true  
   에 대한 설명들을 읽을수 있게 된다.  
   
> 구글 검색어  
Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.


