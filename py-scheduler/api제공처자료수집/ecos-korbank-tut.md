# 한국은행 경제 주요지표 api 사용법 정리

## api 구조
한국은행의 api의 URL 구성방식은 아래와 같다.  
> http://ecos.bok.or.kr/api/StatisticSearch/[api키]/[xml/json]/[kr]/[1- 이건 뭔지 설명이 없다]/[10 - 이건 뭔지 설명이없다.]/...

한국은행 제공 샘플 api - sample 이라는 문자를 인증키로 바꾸라고 하고 있다.
```text
http://ecos.bok.or.kr/api/KeyStatisticList/[sample]/xml/kr/1/10/
```

코스피 종가 가져와보기
```text
xml
http://ecos.bok.or.kr/api/StatisticSearch/[api키]/xml/kr/1/10/028Y015/MM/201901/201906/1070000/

json
http://ecos.bok.or.kr/api/StatisticSearch/[api키]/json/kr/1/10/028Y015/MM/201901/201906/1070000/
```
