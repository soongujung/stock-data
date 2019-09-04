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





