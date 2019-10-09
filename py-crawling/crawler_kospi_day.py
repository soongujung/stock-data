#-*- coding:utf-8 -*-

import psycopg2
import pandas.io.sql as pandas_sql
from pandas import DataFrame
from sqlalchemy import create_engine
import urllib3
import json
from credentials import api_keys

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

arr_columns = [
        'STAT_NAME',  'STAT_CODE',  'ITEM_CODE1', 'ITEM_CODE2', 'ITEM_CODE3',
        'ITEM_NAME1', 'ITEM_NAME2', 'ITEM_NAME3', 'DATA_VALUE', 'TIME'
    ]

dict_columns_type = {
    'STAT_NAME': 'str',
    'STAT_CODE': 'str',
    'ITEM_CODE1': 'str',
    'ITEM_CODE2': 'str',
    'ITEM_CODE3': 'str',
    'ITEM_NAME1': 'str',
    'ITEM_NAME2': 'str',
    'ITEM_NAME3': 'str',
    'DATA_VALUE': 'float',
    'TIME': 'str'
}
api_key = api_keys.kor_bank_api['api_key']


if __name__ == '__main__':
    conn = psycopg2.connect(conn_str)
    cursor = conn.cursor()

    # --  api 크롤링
    url = "http://ecos.bok.or.kr/api/StatisticSearch/{}/json/kr/1/5000/064Y001/DD/20140101/20191231/0001000".format(api_key)

    http = urllib3.PoolManager()
    ret = http.request("GET", url, headers={'Content-Type': 'application/json'})

    print("REST API RESULT ===== ")
    str_response = ret.data.decode('utf-8')
    dict_data = json.loads(str_response)
    arr_data = dict_data['StatisticSearch']['row']

    data_for_insert = [[ dict_data[column_nm] for column_nm in arr_columns] for dict_data in arr_data]

    df_kospi_insert = DataFrame(data_for_insert, columns=arr_columns)
    df_kospi_insert = df_kospi_insert.astype(dtype=dict_columns_type)
    # print('df_kospi_insert : ' + df_kospi_insert)

    # -- database insert/select
    df_kospi_insert.to_sql(name='stock_kospi_day',
                           con=alchemy_conn,
                           index=False,
                           if_exists='replace', #{'fail', 'replace', 'append'}, default : fail
                           schema='public')

    df_kospi_select = pandas_sql.read_sql_query("select * from stock_kospi_day", alchemy_conn)
    print("database result ::: stock_kospi_month")
    print(df_kospi_select)

