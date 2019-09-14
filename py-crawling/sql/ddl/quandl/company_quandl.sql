create table company_quandl
(
  compnumber bigint not null
    constraint company_quandl_pk
      unique,
  longname   varchar(80),
  shortname  varchar(30),
  status     varchar(10),
  contrycode varchar(3),
  region     varchar(60),
  cik        bigint,
  mic        varchar(20),
  ticker     varchar(20),
  exchange   varchar(3),
  country    varchar(3)
);

comment on table company_quandl is 'quandl 제공 주가지표 코드 매핑 목록';

comment on column company_quandl.compnumber is '회사 코드';

comment on column company_quandl.longname is '회사명 (긴 이름)';

comment on column company_quandl.shortname is '회사명 (짧은 이름)';

comment on column company_quandl.status is '상태 (데이터 제공 상태 - Active/InActive)';

comment on column company_quandl.contrycode is '국가코드 (ex. KOR, USA, JPN ...)';

comment on column company_quandl.region is '지역명(영문)';

comment on column company_quandl.cik is '뭔지 모르지만 코드가 길게 있을때도 있다.
';

comment on column company_quandl.mic is 'quandl 내에서 분류하는 기준인듯함';

alter table company_quandl
  owner to postgres;

create index company_quandl_compnumber_index
  on company_quandl (compnumber);

