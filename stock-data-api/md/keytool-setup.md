# keytool을 이용한 jks 파일 만들기


[keytool 명령어 옵션에 대한 자세한 설명](https://www.lesstif.com/pages/viewpage.action?pageId=20775436)

### 명령어 예제
- test.jks 라는 jks 파일을 생성
- store password 는 test1234 (java 코드에서는 asdf에 대해 asdf1234를 입력하면 된다.)
- {asdf : asdf1234} 인 key/value key paire 생성
```bash
$ keytool -genkeypair -alias asdf -keypass asdf1234 \ 
          -keystore test.jks -storetype jks -storepass test1234 \ 
          -keyalg RSA
...
Picked up JAVA_TOOL_OPTIONS: -Djava.net.preferIPv4Stack=true
이름과 성을 입력하십시오.
  [Unknown]:  silentcargo
조직 단위 이름을 입력하십시오.
  [Unknown]:  1
조직 이름을 입력하십시오.
  [Unknown]:  1
구/군/시 이름을 입력하십시오?
  [Unknown]:  GangNam
시/도 이름을 입력하십시오.
  [Unknown]:  Seoul
이 조직의 두 자리 국가 코드를 입력하십시오.
  [Unknown]:  82
CN=silentcargo, OU=1, O=1, L=GangNam, ST=Seoul, C=82이(가) 맞습니까?
  [아니오]:  y
  
Warning:
JKS 키 저장소는 고유 형식을 사용합니다. "keytool -importkeystore -srckeystore test.jks -destkeystore test.jks -deststoretype pkcs12"를 사용하는 산업 표준 형식인 PKCS12로 이전하는 것이 좋습니다.  
```
  
### 명령어 형식 설명  
```bash
keytool -genkeypair -alias [key값] -keypass [key에 대한 password]  
          -keystore [생성/수정할 jks 파일이름].jks -storetype [jks|pkcs12] -storepass [생성/수정한 jks 파일 비밀번호]  
          -keyalg RSA  
```

## 생성된 jks 파일 열어보기
jks파일을 열어보기 위해서는 커맨드라인에서 별도의 명령어를 실행하면 된다.  
  
> 명령어 형식
> - keytool -list -keystore \[jks파일명\].jks 
  
```bash
$ keytool -list -keystore test.jks

.... 
Picked up JAVA_TOOL_OPTIONS: -Djava.net.preferIPv4Stack=true
키 저장소 비밀번호 입력:
키 저장소 유형: jks
키 저장소 제공자: SUN

키 저장소에 1개의 항목이 포함되어 있습니다.

asdf, 2019. 8. 9, PrivateKeyEntry,
인증서 지문(SHA1): 83:77:B4:BC:2A:83:DD:3D:A3:F5:45:9B:E1:9B:AA:EE:8F:54:5F:B1

Warning:
JKS 키 저장소는 고유 형식을 사용합니다. "keytool -importkeystore -srckeystore test.jks -destkeystore test.jks -deststoretype pkcs12"를 사용하는 산업 표준 형식인 PKCS12로
이전하는 것이 좋습니다.
```          


## JAVA (spring) 에서 읽어들이기

- KeyStoreKeyFactory의 생성자는 Resource (springframework.core.io)를 인자로 받는다.  

- Resource 클래스의 상속관계를 살펴보면 Resource 클래스를 상속하는 클래스 중 FileSystemResource (springframework.core.io)를 찾아볼수 있다.  
  
- FileSystemResource 의 생성자들 중  
  : FileSystemResource(String path) 를 기본 Java의 절대 경로 얻는 코드로 작성가능하다.    
  
> 참고자료
> - Resource (Spring API)  
>  : [Resource](https://docs.spring.io/spring/docs/2.5.x/javadoc-api/org/springframework/core/io/Resource.html?is-external=true)
> - FileSystemResource
>  : [FileSystemResource](https://docs.spring.io/spring/docs/2.5.x/javadoc-api/org/springframework/core/io/FileSystemResource.html)
  
```java
class SomeClass{
    
    public void someMethod(){
        KeyPair keyPair =
                    new KeyStoreKeyFactory(
                        new FileSystemResource(this.getClass().getClassLoader().getResource("keystore/test.jks").getPath()),
                        "test1234".toCharArray()
                    )
                    .getKeyPair("asdf", "asdf1234".toCharArray());
    }
    
}
```

