# CORS

## SOP와 CORS
- SOP : 하나의 ORIGIN만을 허용, 브라우저마다 기본적으로 적용되어 있다.

> ORIGIN
> - URL 스키마 (http, https)
> - hostname (ex. www.google.com)
> - 포트 (8080, 18080)

예전에는 ajax 초창기에 cors를 우회하는 기법들이 초창기에 소개되었었다.

Cors에 대한 여러가지 스프링 설정을 해야하는 점이 있는데, 스프링 부트에서는 자동설정을 지원하기 때문에 
아무런 빈 설정없이도 @CrossOrigin으로 그대로 사용할 수 있다.

예제
18080을 포트로 사용하는 웹서버의 welcome 페이지내에 ajax를 아래와 같이 입력한다.

```javascript
    $.ajax('http://localhost:8080/hello')
        .done(function(msg){
            alert(msg);
        })
        .fail(function(){
            alert('fail');
        });
```

8080 을 사용하는 서버에는 RestController를 선언했고 간단히 아래와 같은 코드가 있다.
```java
class HelloController{
    @GetMapping('/hello')
    public String hello(){
        return "hello";
    }
}

```

이렇게 해서 javascript 결과를 확인해보면 
> failed to load http://localhost:8080/hello : No Access-Control-Allow-Origin header is present on the requested resource. ...
  
이란 문구가 뜬다.

### 방법1) @CrossOrigin 어노테이션 사용 
컨트롤러 내의 해당 메서드나, 컨트롤러 전체에 @CrossOrigin을 걸수 있다.
```java
@RestController
public class HelloController{
    
    @CrossOrigin(origins="http://localhost:18080")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    
}
```

### 방법2) 전역적으로 하는 방법
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**")
                .addResourceLocations("classpath:/m/")
                .setCachePeriod(20);
    }
}
```