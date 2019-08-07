# 용어 정리
## 참고자료
> - [Authentication(인증) 관련 클래스와 처리](https://flyburi.com/584)  
>
> - [Spring Security 파헤치기](https://sjh836.tistory.com/165)  
>

## 인증 동작에 대한 정리 
- principal (접근주체)  
  보호된 대상에 접근하는 유저  
    
- Authenticate (인증)  
  어플리케이션의 작업을 수행할 수 있는 주체임을 증명
      
- Authorize (인가)  
  인증을 거치고 나면 허가가 떨어지는데 이렇게 허가를 해주는 동작을 인가 라고 한다.
  현재 유저가 어떤 서비스, 페이지에 접근할 수 있는 권한이 있는지 검사  
  
- 권한  
  인증된 주체가 어플리케이션의 동작을 수행할 수 있도록 허락되었는지를 결정  
    
   - 권한 승인  
     권한승인이 필요한 부분으로 접근하려면 인증 과정을 통해 주체가 증명되어야 한다.  
   
   - 권한 부여  
     두가지 영역이 존재, 웹 요청 권한, 메소드 호출/도메인 인스턴스에 대한 권한 부여

## 인증 방식에 대한 정리
- credential  
  사용자명/비밀번호를 이용한 방식  
  Spring Security 는 credential 기반 인증을 사용 
  
- two factor(이중인증)  
  모바일 OTP, 카카오, SNS 인증, SMS 인증 등 온라인 외에 다른 기기로 다른 통신방식(channel)으로 인증을 받는 방식  
  
- 하드웨어 인증
  자동차 키와 유사한 방식
    

## 인증방식
spring security 는 세션-쿠키 방식으로 인증한다.  
1. 로그인 시도 (http request)  
2. AuthenticationFilter 에서 user DB까지 파고 들어간다.  
3. user가 DB에 존재할 경우 UserDetails로 꺼내서 user의 session을 생성한다.    
4. SecurityContextholder에 SecurityContext를 저장   
   (SecurityContext 내에는 principal, credential 등의 정보를 가지고 있고, SecurityContext를 잡고 있는것은 SecurityContextHolder다.)  
5. 유저에게 session ID와 함께 응답을 내려준다.    
6. 인증이 끝난후의 요청들에 대해서는 쿠키에서 JSESSIONID를 열어보고 검증 후 유효하면 Authentication을 쥐어준다.  
