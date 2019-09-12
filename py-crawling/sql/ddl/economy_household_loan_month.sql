create table economy_household_loan_month
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
    constraint economy_household_loan_month_pk
      primary key
);

alter table economy_household_loan_month
  owner to postgres;

create index economy_household_loan_month_time_index
  on economy_household_loan_month (time);

commit;



