create table apple_refresh_token
(
    user_id     bigint      not null
        primary key,
    value varchar(1000) null,
    foreign key (user_id)
    references user (user_id)
)