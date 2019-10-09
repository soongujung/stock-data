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

# TODO: 패키지로 정리할 것.
kospi_kwargs = {
    'from': 1,
    'to': 5000,
    'search_type': 'DD',
    'start_date': '201400101',
    'end_date': '20191231',
    'item_code1': '064Y001',
    'item_code2': '0001000'
}

corporate_loan_kwargs = {
    'from': 1,
    'to': 5000,
    'search_type': 'MM',
    'start_date': '201400101',
    'end_date': '20191231',
    'item_code1': '005Y003',
    'item_code2': 'BECBLA02'
}

household_loan_kwargs = {
    'from': 1,
    'to': 5000,
    'search_type': 'MM',
    'start_date': '201400101',
    'end_date': '20191231',
    'item_code1': '005Y003',
    'item_code2': 'BECBLA03'
}

exchange_rate_dollar_kwargs = {
    'from': 1,
    'to': 5000,
    'search_type': 'DD',
    'start_date': '201400101',
    'end_date': '20191231',
    'item_code1': '036Y001',
    'item_code2': '0000001'
}


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


# https://stackoverflow.com/questions/8855183/strangeness-with-a-decorator
def korbank_url(insert_function):
    url_manager = UrlManager()

    def wrapper(*args, **kwargs):
        url_manager \
            .add_api_key(api_key) \
            .add_from(kwargs['from']) \
            .add_to(kwargs['to']) \
            .add_item_code1(kwargs['item_code1']) \
            .add_search_type(kwargs['search_type']) \
            .add_start_date(kwargs['start_date']) \
            .add_end_date(kwargs['end_date']) \
            .add_item_code2(kwargs['item_code2'])

        url = url_manager.build_url()
        insert_function(url)

    return wrapper


# 1) kospi
@korbank_url
def kospi_day_insert(url):
    df_to_sql(get_korbank_result(url), 'stock_kospi_day')


# 2) 기업대출
@korbank_url
def corporate_insert(url):
    df_to_sql(get_korbank_result(url), 'economy_corporate_loan_month')


# 3) 가계 대출
@korbank_url
def household_loan_month(url):
    df_to_sql(get_korbank_result(url), 'economy_household_loan_month')


# 4) 환율
@korbank_url
def exchange_rate_dollar_day(url):
    df_to_sql(get_korbank_result(url), 'economy_exchange_rate_dollar_day')


if __name__ == '__main__':
    kospi_day_insert(**kospi_kwargs)
    corporate_insert(**corporate_loan_kwargs)
    household_loan_month(**household_loan_kwargs)
    exchange_rate_dollar_day(**exchange_rate_dollar_kwargs)
