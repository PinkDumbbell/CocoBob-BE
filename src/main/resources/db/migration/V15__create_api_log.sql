
create table apilog
(
    log_id      bigint auto_increment
        primary key,
    user_email  varchar(255) null,
    method  varchar(10) null,
    time    TIMESTAMP null,
    request_url varchar(1000) null,
    handler_name    varchar(1000) null,
    query_string    varchar(1000) null,
    user_agent      varchar(1000) null
)