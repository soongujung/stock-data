use ec2_web_stockdata;

show variables like 'c%';

ALTER DATABASE ec2_web_stockdata
CHARACTER SET = 'utf8mb4'
COLLATE = 'utf8mb4_general_ci';

select @@time_zone, now();


create table user
(
    id            bigint       not null auto_increment,
    created_date  datetime,
    modified_date datetime,
    email         varchar(255) not null,
    name          varchar(255) not null,
    picture       varchar(255),
    role          varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table posts
(
    id            bigint       not null auto_increment,
    created_date  datetime,
    modified_date datetime,
    author        varchar(255),
    content       TEXT         not null,
    title         varchar(500) not null,
    primary key (id)
) engine = InnoDB;

# 스프링 세션 테이블
# Shift 두번 또는 Command+Shfit+O 를 누르면 파일 검색을 할 수 있고
# 그 중 schema-mysql.sql 을 검색해 적용하자.
CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

