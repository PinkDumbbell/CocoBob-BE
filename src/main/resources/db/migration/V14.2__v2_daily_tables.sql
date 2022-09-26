create table daily
(
    daily_id        bigint auto_increment
        primary key,
    date            date   null,
    note            text   null,
    pet_id          bigint null,
    foreign key (pet_id) REFERENCES pet (pet_id)
);

create table daily_image
(
    daily_image_id bigint auto_increment
        primary key,
    path           varchar(255) null,
    daily_id       bigint       null,
    foreign key (daily_id) references daily (daily_id)
);

create table health_record
(
    health_record_id bigint auto_increment
        primary key,
    date            date    null,
    note            text    null,
    pet_id          bigint  null,
    foreign key (pet_id) REFERENCES pet (pet_id)
);

create table health_record_image
(
    health_record_image_id bigint auto_increment
        primary key,
    path                varchar(255) null,
    health_record_id    bigint       null,
    foreign key (health_record_id) REFERENCES health_record (health_record_id)
);

create table meal
(
    meal_id bigint auto_increment
        primary key,
    product_name        varchar(255) null,
    kcal                double       null,
    product_id          bigint       null,
    health_record_id    bigint       null,
    foreign key (product_id) REFERENCES product (product_id),
    foreign key (health_record_id) REFERENCES health_record (health_record_id)
);

create table health_record_abnormal
(
    health_record_id bigint auto_increment
        primary key ,
    abnormal_id         bigint  null,
    foreign key (health_record_id) REFERENCES health_record (health_record_id),
    foreign key (abnormal_id) REFERENCES abnormal (abnormal_id)
);

create table walk
(
    walk_id bigint auto_increment
        primary key,
    started_at      time    null,
    finished_at     time    null,
    photo_path      varchar(255)    null,
    distance        double  null,
    pet_id          bigint  null,
    date            date    null,
    spent_kcal      double  null,
    foreign key (pet_id) REFERENCES pet (pet_id)
);