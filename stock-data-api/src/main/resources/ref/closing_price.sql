WITH SEARCH_DATE AS(
    SELECT  '2018-01-01'::timestamp as start_date,
        '2018-01-30'::timestamp   as end_date
),
     SEARCH_SERIES AS(
         SELECT generate_series(
                        (SELECT d1.start_date FROM SEARCH_DATE AS d1)::timestamp,
                        (SELECT d1.end_date FROM SEARCH_DATE AS d1)::timestamp,
                        interval '1 day'
                    )   AS UNIT
     )
SELECT  TO_CHAR(y.UNIT, 'YYYYMMDD') AS UNIT,
        k."DATA_VALUE",
        k."ITEM_NAME1"
FROM SEARCH_SERIES y
         LEFT OUTER JOIN stock_kospi_day k
                         ON TO_CHAR(y.unit::DATE, 'YYYYMMDD') = k."TIME"