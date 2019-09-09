
-- ROWNUM 구하기
SELECT ff1.* FROM (
SELECT 
       row_number() OVER (ORDER BY f1.location_code desc) AS ROWNUM ,
       f1.location_code,
       f1.information_property_code,
       f1.desc_kr,
       f1.desc_en,
       f2.location_code,
       f2.parent_location_code,
       f2.desc_kr,
       f2.desc_en,
       f2.sort_order,
       f2.code_level,
       f2.use_yn,
       f3.information_property_code,
       f3.desc_kr,
       f3.desc_en,
       f3.unit,
       f3.use_yn
FROM   fems_location_information_list f1
       INNER JOIN fems_location f2
               ON f1.location_code = f2.location_code
       INNER JOIN fems_information_property f3
               ON f1.information_property_code = f3.information_property_code
WHERE  1 = 1
       --AND f1.location_code = 'N'
       --AND f1.information_property_code = 'N'
) AS ff1       
ORDER BY ROWNUM DESC

-- 테이블 칼럼 보기
select * from information_schema.columns
where table_name = 'fems_coefficient'

-- 날짜 포맷
select to_char(now(), 'YYYY-MM-DD 00:00:16')

-- 일자만 수정
select to_char(stamp, 'DD') from fems_data_qt

-- 하루만 더해서 fems_data_qt 데이터 입력
INSERT INTO fems_data_qt
    (stamp, tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code)
SELECT stamp + interval '2 day', tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code
FROM fems_data_qt
WHERE 1 = 1
AND stamp BETWEEN '2017-10-21 00:00:00'::timestamp AND '2017-11-19 23:59:59'::timestamp
--AND aggregate_code = 'AT002'
--AND tag_property_code = 'T0001'
AND tag_id = 'ESS.POWER.KWH'
order by stamp


delete from fems_data_qt
WHERE stamp BETWEEN '2017-10-19 00:00:00'::timestamp AND '2017-10-20 23:59:59'::timestamp


AND tag_id like 'TOTAL.IN.L'

INSERT INTO fems_data_hr 
    (stamp, tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code)
SELECT stamp + interval '0 day', tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code
FROM fems_data_hr
WHERE 1 = 1
AND aggregate_code in ('AT006') -- AT006 합산 --- 선택필요
AND tag_property_code in ('T0009', 'T0010') -- T0009 전기요금 T0010 가스요금
AND stamp BETWEEN date_trunc('month', current_date)::date AND date_trunc('month', current_date + interval '1 months')::date - 1 -- 금월 1일 말일
order by stamp



update fems_data_hr set tag_id = 'TEST.TAG.03'
WHERE stamp BETWEEN '2017-09-13 00:00:00'::timestamp AND now()::timestamp
AND tag_id like 'TEST.%'
AND tag_property_code = 'T0010'

INSERT INTO fems_data_dy 
    (stamp, tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code)
SELECT stamp - interval '10 day', tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code
FROM fems_data_dy
WHERE 1 = 1
AND aggregate_code in ('AT006') -- AT006 합산 --- 선택필요
AND tag_property_code in ('T0009', 'T0010') -- T0009 전기요금 T0010 가스요금
AND stamp BETWEEN '2017-10-10 00:00:00'::timestamp AND '2017-10-28 00:00:00'::timestamp
order by stamp
		       

INSERT INTO fems_data_mt 
    (stamp, tag_id, dev_val, aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code)
SELECT stamp - interval '270 day', tag_id, '950.00', aggregate_code, avg_dev_val, min_dev_val, max_dev_val, tag_property_code
FROM fems_data_mt
WHERE 1 = 1
--AND stamp BETWEEN '2017-10-16 00:00:00'::timestamp AND now()::timestamp
AND aggregate_code in ('AT002') -- AT002 평균
AND tag_property_code in ('T0007') -- T0007 전기순시
--AND tag_id like 'TOTAL.%'
--AND tag_property_code = 'T0002'



select * from fems_data_mt
where tag_id like 'TOTAL.%'
order by stamp

INSERT INTO ess_weather_history 
    (issue_date, plant_id, temperature, humidity, wind_speed, wind_direction, rain, snow, create_by, create_date, modify_by, modify_date)
SELECT issue_date + interval '2 day', plant_id, temperature, humidity, wind_speed, wind_direction, rain, snow, create_by, create_date, modify_by, modify_date 
FROM ess_weather_history
WHERE issue_date BETWEEN '2017-10-18 00:00:00'::timestamp AND '2017-10-18 23:59:59'::timestamp
order by issue_date

INSERT INTO ess_power_forecast 
    (issue_date, exec_date, power_load, create_by, create_date, modify_by, modify_date)
SELECT issue_date + interval '0 day', exec_date, power_load, create_by, create_date, modify_by, modify_date 
FROM ess_power_forecast
WHERE 1 = 1
AND issue_date BETWEEN '2017-09-30 00:00:01'::timestamp AND '2017-11-01 00:00:01'::timestamp
order by issue_date



INSERT INTO ess_schedule
    (schedule_date, exec_date, power_usage, power_kepco, charge, discharge, soc, unit_price, load_type, create_by, create_date, modify_by, modify_date)
SELECT schedule_date + interval '113 day', exec_date, power_usage, power_kepco, charge, discharge, soc, unit_price, load_type, create_by, create_date, modify_by, modify_date 
FROM ess_schedule
WHERE 1 = 1
AND schedule_date BETWEEN '2017-06-01 00:00:01'::timestamp AND '2017-07-01 00:00:01'::timestamp
order by schedule_date



-- 트리 구조
WITH RECURSIVE t AS (
   SELECT facility_code
         ,parent_facility_code
         ,desc_kr
         ,0 AS LEVEL
   FROM   fems_facility
   WHERE  parent_facility_code = '1' -- 선택한 최상위 코드를 설정한다
   UNION ALL
   SELECT a.facility_code
         ,a.parent_facility_code
         ,a.desc_kr
         ,LEVEL + 1 AS LEVEL
   FROM   fems_facility a
   JOIN   t ON a.parent_facility_code = t.facility_code
   )
SELECT facility_code
      ,parent_facility_code
      ,desc_kr
      ,LEVEL
FROM   t
ORDER BY LEVEL





WITH RECURSIVE t AS (
   SELECT demand_code
         ,parent_demand_code
         ,desc_kr
         ,0 AS LEVEL
         ,sort_ordermo
         ,code_level
   FROM   fems_demand
   WHERE  parent_demand_code = '1' -- 선택한 최상위 코드를 설정한다
   UNION ALL
   SELECT a.demand_code
         ,a.parent_demand_code
         ,a.desc_kr
         ,LEVEL + 1 AS LEVEL
         ,a.sort_order
         ,a.code_level
   FROM   fems_demand a
   JOIN   t ON a.parent_demand_code = t.demand_code
   )
SELECT demand_code
      ,parent_demand_code
      ,desc_kr
      ,LEVEL
      ,code_level
FROM   t
ORDER BY LEVEL, parent_demand_code, demand_code, sort_order

-- 조회 날짜 검색 fems_data_qt   -- 15분 시작    30초 끝
select * from fems_data_qt where 1 = 1 AND stamp BETWEEN '2017-09-20 00:15:00'::timestamp AND '2017-09-28 07:00:30'::timestamp -- 기간 설정

-- 전주 월요일 금요일
select date_trunc('week', current_date-7)::date, date_trunc('week', current_date-7)::date+6

-- 금월 1일 말일
select date_trunc('month', current_date)::date, date_trunc('month', current_date + interval '1 months')::date - 1


select date_trunc('month', current_date)::date, date_trunc('month', current_date + interval '1 months')::date - 1



-- 전월 1일 말일
select date_trunc('month', current_date - interval '1 months')::date, date_trunc('month', current_date)::date - 1

-- 전년 동월 1일 말일 
select date_trunc('month', current_date - interval '12 months')::date, date_trunc('month', current_date - interval '11 months')::date - 1

-- 과거 1년
select (current_date - interval '12 months')::date, now()::date

-- 전주 동요일
select date_trunc('day', current_date-7)::date, date_trunc('day', current_date-7)::date

-- 분모가 0인 경우와 NVL
(CASE WHEN SUM(fff1.last_week_electric_value) = 0 THEN NULL ELSE SUM(fff1.last_week_electric_value) END)
COALESCE(ROUND(SUM(fff1.today_electric_value)/(CASE WHEN SUM(fff1.last_week_electric_value) = 0 THEN NULL ELSE SUM(fff1.last_week_electric_value) END), 2), 0) AS rate_last_week_electric_value

-- fems_data_qt -> fems_data_hr, 15분 집계를 1시간 집계로 
INSERT INTO fems_data_hr
            (
                        stamp,
                        tag_id,
                        dev_val,
                        aggregate_code,
                        avg_dev_val,
                        min_dev_val,
                        max_dev_val,
                        tag_property_code
            )
SELECT   Min(ff1.stamp + INTERVAL '1 minute'),
         Max(ff1.tag_id),
         Sum(ff1.dev_val),
         Max(ff1.aggregate_code),
         Sum(ff1.avg_dev_val),
         Sum(ff1.min_dev_val),
         Sum(ff1.max_dev_val),
         Max(ff1.tag_property_code)
FROM     (
                SELECT f1.stamp,
                       f1.tag_id,
                       f1.dev_val,
                       f1.aggregate_code,
                       f1.avg_dev_val,
                       f1.min_dev_val,
                       f1.max_dev_val,
                       f1.tag_property_code
                FROM   (
                              SELECT stamp - INTERVAL '1 minute' as stamp, -- 01:15 ~ 02:00을 시로 그룹핑 하기 위해 1분씩 차감
                                     tag_id,
                                     dev_val,
                                     aggregate_code,
                                     avg_dev_val,
                                     min_dev_val,
                                     max_dev_val,
                                     tag_property_code
                              FROM   fems_data_qt
                        ) f1
                WHERE  1 = 1
		AND stamp BETWEEN '2017-10-23 00:00:00'::timestamp AND '2017-11-19 23:59:59'::timestamp
		AND tag_id = 'ESS.POWER.KWH'
         ) ff1
WHERE    1 = 1
GROUP BY to_char(ff1.stamp, 'yyyymmddHH24'),
         ff1.tag_id,
         ff1.aggregate_code,
         ff1.tag_property_code
         
-- fems_data_hr -> fems_data_dy, 1시 집계를 1일 집계로 
INSERT INTO fems_data_dy
            (
                        stamp,
                        tag_id,
                        dev_val,
                        aggregate_code,
                        avg_dev_val,
                        min_dev_val,
                        max_dev_val,
                        tag_property_code
            )
SELECT   Min(ff1.stamp),
         Max(ff1.tag_id),
         Sum(ff1.dev_val),
         Max(ff1.aggregate_code),
         Sum(ff1.avg_dev_val),
         Sum(ff1.min_dev_val),
         Sum(ff1.max_dev_val),
         Max(ff1.tag_property_code)
FROM     (
                SELECT f1.stamp,
                       f1.tag_id,
                       f1.dev_val,
                       f1.aggregate_code,
                       f1.avg_dev_val,
                       f1.min_dev_val,
                       f1.max_dev_val,
                       f1.tag_property_code
                FROM   (
                              SELECT stamp, -- 01:15 ~ 02:00을 시로 그룹핑 하기 위해 1분씩 차감
                                     tag_id,
                                     dev_val,
                                     aggregate_code,
                                     avg_dev_val,
                                     min_dev_val,
                                     max_dev_val,
                                     tag_property_code
                              FROM   fems_data_hr
                        ) f1
                WHERE  1 = 1
		AND stamp BETWEEN '2017-10-23 00:00:00'::timestamp AND '2017-11-19 23:59:59'::timestamp
		AND tag_id = 'ESS.POWER.KWH'
         ) ff1
WHERE    1 = 1
GROUP BY to_char(ff1.stamp, 'yyyymmdd'),
         ff1.tag_id,
         ff1.aggregate_code,
         ff1.tag_property_code
         
-- fems_data_dy -> fems_data_mt, 1일 집계를 1달 집계로 
INSERT INTO fems_data_mt
            (
                        stamp,
                        tag_id,
                        dev_val,
                        aggregate_code,
                        avg_dev_val,
                        min_dev_val,
                        max_dev_val,
                        tag_property_code
            )
SELECT   Min(ff1.stamp),
         Max(ff1.tag_id),
         Sum(ff1.dev_val),
         Max(ff1.aggregate_code),
         Sum(ff1.avg_dev_val),
         Sum(ff1.min_dev_val),
         Sum(ff1.max_dev_val),
         Max(ff1.tag_property_code)
FROM     (
                SELECT f1.stamp,
                       f1.tag_id,
                       f1.dev_val,
                       f1.aggregate_code,
                       f1.avg_dev_val,
                       f1.min_dev_val,
                       f1.max_dev_val,
                       f1.tag_property_code
                FROM   (
                              SELECT stamp, -- 01:15 ~ 02:00을 시로 그룹핑 하기 위해 1분씩 차감
                                     tag_id,
                                     dev_val,
                                     aggregate_code,
                                     avg_dev_val,
                                     min_dev_val,
                                     max_dev_val,
                                     tag_property_code
                              FROM   fems_data_dy
                        ) f1
                WHERE  1 = 1
                AND stamp BETWEEN '2017-10-21 00:00:00'::timestamp AND '2017-11-19 23:59:59'::timestamp
         ) ff1
WHERE    1 = 1
GROUP BY to_char(ff1.stamp, 'yyyymm'),
         ff1.tag_id,
         ff1.aggregate_code,
         ff1.tag_property_code

         

-- 하루만 더해서 ess_weather_history 데이터 입력
INSERT INTO ess_weather_history 
    (issue_date, plant_id, temperature, humidity, wind_speed, wind_direction, rain, snow, create_by, create_date, modify_by, modify_date)
SELECT issue_date + interval '1 day', plant_id, temperature, humidity, wind_speed, wind_direction, rain, snow, create_by, create_date, modify_by, modify_date
FROM ess_weather_history
WHERE 1 = 1
AND issue_date BETWEEN '2017-10-20 00:00:00'::timestamp AND '2017-11-19 23:59:59'::timestamp
order by issue_date

-- 문자를 숫자로
select float8('0123')

-- 날짜 순서대로 표시하기 년월일
WITH date_series AS (
        SELECT date(generate_series(date '2017-10-01', date '2017-10-31','1 day')) AS date 
)

-- 지정기간 내 가동일 구하기
SELECT COUNT(*) 
FROM date_series f1
WHERE EXTRACT(DOW FROM f1.date) NOT IN (0, 6)
AND to_char(f1.date, 'mmdd') NOT IN (SELECT to_char(day, 'mmdd') FROM fems_special_day WHERE repeat_code = 'Y')
AND to_char(f1.date, 'yyyymmdd') NOT IN (SELECT to_char(day, 'yyyymmdd') FROM fems_special_day WHERE repeat_code = 'N')

 
SELECT date,
       Extract(day FROM date)     AS day,
       Extract(month FROM date)   AS month,
       Extract(quarter FROM date) AS quarter,
       Extract(year FROM date)    AS year,
       EXTRACT(DOW FROM date)
FROM   date_series 



-- 날짜 순서대로 표시하기 시간
WITH date_series AS (
        SELECT (generate_series('2008-03-01 00:00'::timestamp, '2008-12-04 12:00', '1 hours')) AS date
)
  
SELECT date,
       Extract(hour FROM date)    AS hour,
       Extract(day FROM date)     AS day,
       Extract(month FROM date)   AS month,
       Extract(year FROM date) AS quarter,
       Extract(month FROM date)   AS month
FROM   date_series 


-- 기간내의 모든 날짜 표시
SELECT date(generate_series(date_trunc('month', current_date)::date, date_trunc('month', current_date + interval '1 months')::date - 1, '1 day')) AS date_val


-- fems_data_hr 에서 주말 및 특수일 제외
SELECT f1.* 
FROM   fems_data_hr f1 
WHERE  1 = 1 
       AND Extract(dow FROM f1.stamp) NOT IN (0,6) -- 0:일, 6:토 
       AND f1.stamp::DATE NOT IN (SELECT day::DATE FROM fems_special_day) 
ORDER  BY f1.stamp 


-- 알람 입력
INSERT INTO fems_alarm 
    (alarm_seq, main_tag_id, sub_tag_id01, sub_tag_id02, reg_date, condition_query, desc_kr, alarm_type, alarm_level, user_code, use_yn, reg_countermeasure, repeat_yn, alarm_cycle, receiver_code)
SELECT alarm_seq, main_tag_id, sub_tag_id01, sub_tag_id02, reg_date, condition_query, desc_kr, alarm_type, alarm_level, user_code, use_yn, reg_countermeasure, repeat_yn, alarm_cycle, receiver_code 
FROM fems_alarm;

-- 알람 상세 입력
INSERT INTO fems_alarm_detail 
    (alarm_seq, tag_id, tag_group, desc_kr, occur_time, occur_dev_val, view_yn, check_sms_yn, check_email_yn, user_countmeasure, check_user_code, check_time)
SELECT alarm_seq, tag_id, tag_group, desc_kr, occur_time, occur_dev_val, view_yn, check_sms_yn, check_email_yn, user_countmeasure, check_user_code, check_time 
FROM fems_alarm_detail;



SELECT date(generate_series(date_trunc('month', current_date - interval '1 months')::date, date_trunc('month', current_date)::date - 1, '1 day')) AS date_val

-- 지난 시간 구하기
SELECT 
	Date_part('day', Now() - create_date) AS days -- 전체일수 
	,Age(Now(), create_date) AS times-- 전체시분초 
	,LPAD(Floor(year)::text, 2, '0') AS year -- 년 
	,LPAD(((year - Floor(year))*365)::text, 3, '0') AS day -- 일 
	,LPAD(hour::text, 2, '0') AS hour -- 시 
FROM (
	SELECT create_date
		,Date_part('day', Now() - create_date) AS days -- 전체 일수 
	        ,Age(Now(), create_date) AS times -- 전체 시분초 
	        ,Extract(day FROM (Now() - create_date))/365 AS year -- 년
	        ,Extract(hour FROM Age(Now(), create_date)) AS hour -- 시
	FROM   ess_info 
	ORDER BY create_date
	--LIMIT 1
) f1


-- 전력 예측
INSERT INTO ess_power_forecast 
    (issue_date, exec_date, power_load, create_by, create_date, modify_by, modify_date)
SELECT MIN(stamp) AS stamp, now(), SUM(dev_val), 'kik', now(), 'kik', now()
FROM fems_data_qt
WHERE stamp BETWEEN '2017-09-01 00:00:00'::timestamp AND '2017-11-19 23:59:59'::timestamp
AND tag_id = 'TOTAL.POWER.KWH'
GROUP BY stamp
ORDER BY stamp


-- 주말선택
SELECT EXTRACT(DOW FROM now()), EXTRACT(DOW FROM '2017-10-21 20:38:40'::TIMESTAMP), EXTRACT(DOW FROM '2017-10-22 20:38:40'::TIMESTAMP); -- 6 토  0 일


-- 주말 , 특수일 제외
SELECT *
FROM   (
	      --SELECT date(generate_series(Date_trunc('month', CURRENT_DATE)::date, CURRENT_DATE,'1 day')) AS date ) f1 -- 이번달 1월 1일 ~ 오늘
	      --SELECT date(generate_series(Date_trunc('month', CURRENT_DATE)::date, date_trunc('month', CURRENT_DATE + interval '1 months')::date - 1,'1 day')) AS date ) f1 -- 이번달 1일 ~ 이번달 말일
	      SELECT date(generate_series(date '2017-10-01', date '2017-11-30','1 day')) AS date  ) f1 -- 시작일 ~ 완료일
	      
WHERE  extract(dow FROM f1.date) NOT IN (0, 6)
AND    to_char(f1.date, 'mmdd') NOT IN
       (
	      SELECT to_char(day, 'mmdd')
	      FROM   fems_special_day
	      WHERE  repeat_code = 'Y')
AND    to_char(f1.date, 'yyyymmdd') NOT IN
       (
	      SELECT to_char(day, 'yyyymmdd')
	      FROM   fems_special_day
	      WHERE  repeat_code = 'N' -- N인 경우 건수가 많아서 아래 기간조건 추가
	      --AND day BETWEEN Date_trunc('month', CURRENT_DATE)::date AND CURRENT_DATE -- 이번달 1월 1일 ~ 오늘
	      --AND day BETWEEN Date_trunc('month', CURRENT_DATE)::date AND date_trunc('month', CURRENT_DATE + interval '1 months')::date - 1 -- 이번달 1일 ~ 이번달 말일
	      AND day BETWEEN '2017-10-01'::date AND '2017-11-30'::date -- 시작일 ~ 완료일
	)
