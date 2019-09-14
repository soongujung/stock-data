# 한국은행 경제 주요지표 api 사용법 정리

## api 구조
한국은행의 api의 URL 구성방식은 아래와 같다.  
> http://ecos.bok.or.kr/api/StatisticSearch/[api키]/[xml/json]/[kr]/[1- 이건 뭔지 설명이 없다]/[10 - 이건 뭔지 설명이없다.]/...

한국은행 제공 샘플 api - sample 이라는 문자를 인증키로 바꾸라고 하고 있다.
```text
http://ecos.bok.or.kr/api/KeyStatisticList/[sample]/xml/kr/1/10/
```

### 코스피 종가 가져와보기
```text
xml
http://ecos.bok.or.kr/api/StatisticSearch/[api키]/xml/kr/1/10/028Y015/MM/201901/201906/1070000/

json
http://ecos.bok.or.kr/api/StatisticSearch/[api키]/json/kr/1/10/028Y015/MM/201901/201906/1070000/
```

## 개발 언어선택
java 를 주 언어로 하는 개발자이고, spring batch, quartz를 도입하면 정교하게 컨트롤할수 있다는 장점이 있다.  
하지만 이건 조금 나중에 생각하도록 하고 우선 일단 python 기반으로 샘플데이터를 입력할 예정이다.  
만들어진 데이터를 기반으로 spring web에서 해당 DB의 내용을 참조하도록 할 생각이다.  

정말 단순한 이유는 
> 정말 빠르게 데이터를 만들어놓고, 빠르게 spring web mvc 에 db를 안착시키려는 게 목표이기 때문이다.
> web 에서도 해야할 게 무지 많다.

- 프레임워크에서 잘 동작하는 코드  
- 프레임워크 종속없이 어떠한 입력에도 항상 같은 결과를 내는 간단한 python 코드  

이렇게 각각 하나씩 만들 예정이다. 

그래서 정리 문서는 
- 스프링 버전  
- python 버전   

이렇게 두개로 만들 예정이다.  



## 환경구성

의존성 설치

```bash
$ mkdir py-crawling
$ cd py-crawling
$ virtualenv env_crawler
(env_crawler) $ env_crawler\Scripts\activate

(env_crawler) $ pip install psycopg2 pandas sqlalchemy
(env_crawler) $ pip freeze > requirements.txt
```



## 컴파일 Configuration 구성(in pycharm)

이부분,,, 캡쳐해놓은걸로 !!! 첨부그림해서 ㄱㄱ



## DB연동 - sqlAlchemy, pandas
dataframe을 이용해 select 해오는 것과 insert 하는 것에 대한 예제코드  
  
추후정리  
  
코드  
  
```python
#-*- coding:utf-8 -*-

import psycopg2
import pandas.io.sql as pandas_sql
from pandas import DataFrame
from sqlalchemy import create_engine


dict_account = {
    'host': 'localhost',
    'dbname': 'stock_data',
    'user': 'postgres',
    'password': '1234'
}

conn_str = "host='{}' dbname='{}' user={} password={}"\
    .format(
        dict_account['host'],
        dict_account['dbname'],
        dict_account['user'],
        dict_account['password']
    )

alchemy_conn = create_engine(
    "postgresql://{}:{}@localhost:5432/{}"
        .format(
            dict_account['user'],
            dict_account['password'],
            dict_account['dbname']
        )
)

if __name__ == '__main__':
    conn = psycopg2.connect(conn_str)
    cursor = conn.cursor()

    # cursor.execute("select * from stock_kospi_month")
    # result = cursor.fetchall()
    # print(result)

    # INSERT INTO
    # public.stock_kospi_month(
    #     stat_name, item_code1, stat_code, item_code2, item_code3, item_name1, item_name2, item_name3, data_value,
    #     "time")
    # VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

    df_kospi_month = pandas_sql.read_sql_query("select * from stock_kospi_month", conn)
    print(df_kospi_month)

    arr_columns = [
        'stat_name' ,'stat_code' ,'item_code1','item_code2','item_code3',
        'item_name1','item_name2','item_name3','data_value','time'
    ]

    sample_data = {
        'stat_name': [3,4,5,6],
        'stat_code': [3,4,5,6],
        'item_code1': [3,4,5,6],
        'item_code2': [3,4,5,6],
        'item_code3': [3,4,5,6],
        'item_name1': [3,4,5,6],
        'item_name2': [3,4,5,6],
        'item_name3': [3,4,5,6],
        'data_value': [3,4,5,6],
        'time': [3,4,5,6],
    }

    df_kospi_insert = DataFrame(sample_data, columns=arr_columns)
    df_kospi_insert.to_sql(name='stock_kospi_month',
                           con=alchemy_conn,
                           index=False,
                           if_exists='append', #{'fail', 'replace', 'append'}, default : fail
                           schema='public')

    df_kospi_month = pandas_sql.read_sql_query("select * from stock_kospi_month", alchemy_conn)
    print(df_kospi_month)
```



## urllib3를 이용해 api 크롤링 해보기

api의 데이터를 크롤링 하기 위해서라면 python에서는 단 몇줄의 코드만을 작성하면 된다.  

초기 개발시 프레임워크 없이 스크립트만으로 뭔가를 셋업해놓고자 할 때 굉장히 유용하다.  



### 의존성 추가

requirments.txt

```txt
urllib3==1.25.3
```

또는

```bash
$ pip install urllib3 
$ pip freeze > requirements.txt
```



### 코드

설명은 추후 추가  

```python
    url = "http://ecos.bok.or.kr/api/StatisticSearch/[api키]/json/kr/1/10/028Y015/MM/201901/201906/1070000/"
    http = urllib3.PoolManager()
    ret = http.request("GET", url, headers={'Content-Type': 'application/json'})
    str_response = ret.data.decode('utf-8')
    dict_data = json.loads(str_response)
```



