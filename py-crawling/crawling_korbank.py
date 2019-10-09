#-*- coding:utf-8 -*-

import pandas.io.sql as pandas_sql
from pandas import DataFrame
import urllib3
import json

# 사용자 정의 패키지
from credentials.restapi.api_keys import KEY_MAPPINGS
from credentials.connection.database import postgresql_dev
from db_connector.alchemy.connection_manager import ConnectionManager
from mapper.column_mapping.korbank import get_column_list, get_column_types
from korbank.util.urls import UrlManager

# Connection 생성
alchemy_conn = ConnectionManager.create_connection(postgresql_dev)

# Column Mapping
arr_columns = get_column_list()
dict_columns_type = get_column_types()

# API KEY
api_key = KEY_MAPPINGS['korbank']


def get_korbank_result(_url):
    http = urllib3.PoolManager()
    ret = http.request("GET", _url, headers={'Content-Type': 'application/json'})

    str_response = ret.data.decode('utf-8')
    dict_data = json.loads(str_response)
    rest_data = dict_data['StatisticSearch']['row']
    return rest_data


def df_to_sql(rest_data, table_name):
    # [{}, {}, {} ] -> [ [] [] [] [] ...]  변환 작업
    data_for_insert = [[dict_data[column_nm] for column_nm in arr_columns] for dict_data in rest_data]

    df_insert = DataFrame(data_for_insert, columns=arr_columns)
    df_insert = df_insert.astype(dtype=dict_columns_type)

    # -- database insert
    df_insert.to_sql(name=table_name,
                     con=alchemy_conn,
                     index=False,
                     if_exists='replace',   # {'fail', 'replace', 'append'}, default : fail
                     schema='public')

    # -- select database insert result
    df_select = pandas_sql.read_sql_query("select * from {}".format(table_name), alchemy_conn)
    print("database result ::: {}".format(table_name))
    print(df_select)


def korbank_kospi_url(f):
    url_manager = UrlManager()
    url_manager \
        .add_api_key(api_key) \
        .add_from(1) \
        .add_to(5000) \
        .add_item_code1('064Y001') \
        .add_search_type('DD') \
        .add_start_date('201400101') \
        .add_end_date('20191231') \
        .add_item_code2('0001000')

    url = url_manager.build_url()

    def inner(*args, **kwargs):
        f(url)

    return inner


# 1) kospi
@korbank_kospi_url
def kospi_day_insert(url):
    df_to_sql(get_korbank_result(url), 'stock_kospi_day')


# 2) 기업대출
def corporate_insert():
    url_manager = UrlManager()
    url_manager \
        .add_api_key(api_key) \
        .add_from(1) \
        .add_to(5000) \
        .add_item_code1('005Y003') \
        .add_search_type('MM') \
        .add_start_date('201400101') \
        .add_end_date('20191231') \
        .add_item_code2('BECBLA02')

    arr_data = get_korbank_result(url_manager.build_url())

    df_to_sql(arr_data, 'economy_corporate_loan_month')


# 3) 가계 대출
def household_loan_month():
    url_manager = UrlManager()
    url_manager \
        .add_api_key(api_key) \
        .add_from(1) \
        .add_to(5000) \
        .add_item_code1('005Y003') \
        .add_search_type('MM') \
        .add_start_date('201400101') \
        .add_end_date('20191231') \
        .add_item_code2('BECBLA03')

    arr_data = get_korbank_result(url_manager.build_url())

    df_to_sql(arr_data, 'economy_household_loan_month')


# 4) 환율
def exchange_rate_dollar_day():
    url_manager = UrlManager()
    url_manager \
        .add_api_key(api_key) \
        .add_from(1) \
        .add_to(5000) \
        .add_item_code1('036Y001') \
        .add_search_type('DD') \
        .add_start_date('201400101') \
        .add_end_date('20191231') \
        .add_item_code2('0000001')

    arr_data = get_korbank_result(url_manager.build_url())

    df_to_sql(arr_data, 'economy_exchange_rate_dollar_day')


if __name__ == '__main__':
    kospi_day_insert()
    corporate_insert()
    household_loan_month()
    exchange_rate_dollar_day()
