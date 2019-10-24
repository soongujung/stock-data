# VARCHAR 타입의 날짜 컬럼을 LocalDateTime 으로 매핑하기
일을 하다보면 내가 만든 테이블이 아니지만 해당 테이블을 그대로 사용해야 하는 경우가 있다.  
그리고 날짜 컬럼의 데이터 타입이 DATE가 아닌 VARCHAR인 경우도 있다.  
이런 경우에 대해서 

>  **MYBATIS로 DB에 select 조회시(insert, update, delete 제외) LocalDateTime으로 변환을 어떻게 했는지** 정리를 해보려 한다.  



## 참고자료들

- [mybatis 공식문서](https://mybatis.org/mybatis-3/ko/configuration.html#typeHandlers)  
- [SpringBoot에서 mybatis의 TypeHandler와 Enum관리하기](https://www.holaxprogramming.com/2015/11/12/spring-boot-mybatis-typehandler/)  
  를 참고했었다.  

[SpringBoot에서 mybatis의 TypeHandler와 Enum관리하기](https://www.holaxprogramming.com/2015/11/12/spring-boot-mybatis-typehandler/)  
의 경우 굉장히 유용한 자료이고 추후 사이드 프로젝트에 도입해볼 의향이 있었다. 하지만, 내가 회사 업무에서 하려고 했던 내용에는 맞지 않는 내용이었다.



## PROBLEM

### 테이블 명세 

COLLECT_RACK_HISTORY라는 테이블이 있다고 가정하자. 이 테이블에는 

- V_TIME : VARCHAR(14)
- V_MCH_ID : VARCHAR
- ...

등의 컬럼이 있다. 



여기에 대한 Java Model은 아래와 같다.

### CollectRateModel

```java
package com.nuri.ess.ems.ess.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CollectRateModel {
    private String vMchId;
    private String vTime;

    public String getvMchId() {
        return vMchId;
    }

    public void setvMchId(String vMchId) {
        this.vMchId = vMchId;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
    }
	...
}

```



다음은 resultMap과 typeAlias 이다.  

### resultMap    

```xml
<resultMap id="collectMap" type="collectModel">
    ...
    <result column="V_MCH_ID" jdbcType="VARCHAR" property="vMchId"/>
    <result column="V_TIME" jdbcType="VARCHAR" property="vTime"/>
</resultMap>
```

  

### typeAlias

```xml
<typeAliases>
	...
    <typeAlias alias="collectModel" type="com.nuri.ess.ems.ess.models.CollectRateModel"/>
</typeAliases>
```

  

Model과 xml 설정들을 유심히 살펴보면 V_TIME 컬럼을 mybatis가 DB에서 jdbc로 select 해올 때는 오직 String으로 들고올 수 밖에 없다는 사실을 알 수 있다.  TypeHandler를 구현해 인식하도록 만들 수도 있다. 하지만 뭔가 더 쉬운방법이 있을거다. 라고 생각했다.  



## SOLUTION



해결하려는 문제의 목적(SELECT시 VARCHAR 날짜를 JAVA에서 DATE로 가져오기)에 가장 근접하면서 명확한 해결책이 있었다. 트릭이라고 볼수도 있지만, 간단하고 명확한 방법인 듯하다.

테이블 명세를 바꾸지 않고, TypeHandler로 다른 특정 컬럼에도 제한이 걸리지도 않게 해결했으면 누이좋고 매부 좋은거 아닌감?  

- 일단, LocalDateTime 타입의 locVTime 필드를 추가했다.  
- vTime이라는 필드의 setter는 mybatis가 select 구문을 조회후 CollectRateModel 타입의 인스턴스 내의 vTime 필드에 값을 바인딩할때 사용된다.  
- vTime 의 setter인 setvTime(String vTime) 내에서 vTime으로 얻은 값을 LocalDateTime으로 변환후 locVTime 필드에 저장하도록 했다.    



```java
package com.nuri.ess.ems.ess.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CollectRateModel {

    private final static String FORMAT_STRING = "yyyyMMddHHmmss";
    private String vMchId;
    private String vTime;
    // 새로 추가한 필드
    private LocalDateTime locVTime;
    
    public String getvMchId() {
        return vDeviceId;
    }

    public void setvMchId(String vDeviceId) {
        this.vDeviceId = vDeviceId;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
        // 새롭게 추가한 부분
        this.locVTime = LocalDateTime.parse(vTime, DateTimeFormatter.ofPattern(FORMAT_STRING));
    }

    
    // getter/setter로 추가된 부분이다. 신경쓰지 않아도 된다.
    public LocalDateTime getLocVTime() {
        return locVTime;
    }

    public void setLocVTime(LocalDateTime locVTime) {
        this.locVTime = locVTime;
    }
    
	...
}
```



이렇게 해서 테스트 코드로 해보니 잘 된다. 결과가 잘 동작하는지 캡처사진을 올리려 했으나, 회사일인지라 컬럼명이 외부에 보이면 안될것 같아 스크린샷 첨부는 생략.  

