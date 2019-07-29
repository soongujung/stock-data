# 계획 및 방향, TODO 잡다구리
- Slack 로그인시 User를 우리 DB에도 저장해야 한다. sign in with slack 버튼 클릭 -> accpet 클릭후 ok나 accept 같은 응답이 올것 같다.  

- 이 응답이 ok일때 이때 우리 DB에 없으면 INSERT를 우리 DB에 있으면 그냥 go
- 이 응답이 not ok일때는 error 
- 이걸 판단하는 기준이 뭔지 공식문서를 참고할 것  
https://api.slack.com/docs/sign-in-with-slack  
  

# 2019-07-29
- 현재 User Resource Server, Authorization Server와 데이터 교환하는 것까지는 마무리 됬다.
- User Resource Server 에서는 Authroization Grant에 맞도록 code 값을 전송해주고
- Authorization Server 에서는 Access Token을 발급받는다. 


### 앞으로 할일
- UserAuthService 내에서 사용하고 있는 loadUserByUsername을 연동해야 한다.

구상하는 call-flow는
- sign in with slack을 클릭한다.
- /auth/slack/proxy/redirect에서 loadUserByUsername을 처리한다.  
  (*Authorization Grant를 받을때 error를 받거나 하는 경우에는 /로 redirect 한다.)
  
  이때,  
    DB에 Slack 계정이 없으면 INSERT  
    DB에 Slack 계정이 있으면 그대로 GO  
  
    
  

