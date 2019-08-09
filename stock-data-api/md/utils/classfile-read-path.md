# JAVA / SPRING 에서 파일 경로
배포에 안전하도록 파일을 읽어들이려면   
- 클래스 패스의 특정 파일을 찾아낸 후   
- 절대경로로 변환해 얻어내는 것이 유용할 수 있다.  

SPRING에서만 유용한 자료가 아닌 java.net 패키지 내의 URL클래스를 사용하는 방식을 정리해본다. 


## FileSystemResource 클래스 이용방식
Spring에 한정된 클래스인 Resource 클래스를 이용하는 방식이 있다. ..... 좀더 풀어쓰자 ㅠㅠ

### jks 파일 읽어 들이기 (resources/keystore) 

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