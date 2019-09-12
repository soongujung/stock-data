create table economy_exchange_rate_dollar_day
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
    constraint economy_exchange_rate_dollar_day_pk
      primary key
);

alter table economy_exchange_rate_dollar_day
  owner to postgres;

create index economy_exchange_rate_dollar_day_time_index
  on economy_exchange_rate_dollar_day (time);

commit;



