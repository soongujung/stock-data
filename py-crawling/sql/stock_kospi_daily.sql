create table stock_kospi_day
(
  stat_name  varchar(40),
  stat_code  varchar(20),
  item_code1 varchar(20),
  item_name1 varchar(20),
  item_code2 varchar(20),
  item_name2 varchar(20),
  item_code3 varchar(20),
  item_name3 varchar(20),
  data_value varchar(20),
  time       varchar(14) not null
    constraint stock_kospi_day_pk
      primary key
);

alter table stock_kospi_day
  owner to postgres;

create index stock_kospi_day_time_index
  on stock_kospi_day (time);

