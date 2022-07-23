
create table abnormal
(
    abnormal_id bigint auto_increment
        primary key,
    name        varchar(255) null
);

create table allergy
(
    allergy_id bigint auto_increment
        primary key,
    name       varchar(255) null
);

create table breed
(
    breed_id   bigint auto_increment
        primary key,
    created_by varchar(255) null,
    name       varchar(255) null,
    size       varchar(255) null
);

create table user
(
    user_id               bigint auto_increment
        primary key,
    account_type          varchar(255) null,
    email                 varchar(255) null,
    password              varchar(255) null,
    role                  varchar(255) null,
    username              varchar(255) null,
    representative_pet_id bigint       null
);

create table pet
(
    pet_id         bigint auto_increment
        primary key,
    activity_level int          null,
    age            int          null,
    birthday       date         null,
    body_weight    float        null,
    fat_level      int          null,
    is_pregnant    bit          null,
    is_spayed      bit          null,
    name           varchar(255) null,
    sex            varchar(255) null,
    thumbnail_path varchar(255) null,
    breed_id       bigint       null,
    user_id        bigint       null,
    constraint FKf8amq6u6jv5qjd7djrxe5osb1
        foreign key (user_id) references user (user_id),
    constraint FKiqbjbaml7gtwulqptktmsi5dc
        foreign key (breed_id) references breed (breed_id)
);

alter table user add foreign key (representative_pet_id) references pet(pet_id);

create table daily
(
    daily_id        bigint auto_increment
        primary key,
    date            date   null,
    feed_amount     int    null,
    note            text   null,
    walk_distance   float  null,
    walk_gps        text   null,
    walk_total_time int    null,
    pet_id          bigint null,
    constraint FK6mrm7nd7u8hg046v7bkjp18kt
        foreign key (pet_id) references pet (pet_id)
);

create table daily_abnormal
(
    abnormal_id bigint not null,
    daily_id    bigint not null,
    primary key (abnormal_id, daily_id),
    constraint FKfni3o25d5ujom50yusatftli3
        foreign key (abnormal_id) references abnormal (abnormal_id),
    constraint FKocbbl0caevknog36306itymte
        foreign key (daily_id) references daily (daily_id)
);

create table daily_image
(
    daily_image_id bigint auto_increment
        primary key,
    path           varchar(255) null,
    daily_id       bigint       null,
    constraint FKt31h3aqpv9ev5bg7f9lhivga0
        foreign key (daily_id) references daily (daily_id)
);

create table daily_pattern
(
    pattern_id     bigint auto_increment
        primary key,
    feed_amount    int    null,
    walk_distance  float  null,
    walk_frequency int    null,
    walk_time      int    null,
    pet_id         bigint null,
    constraint FKq1drh9yqcud131t38kpn9n807
        foreign key (pet_id) references pet (pet_id)
);

create table product
(
    product_id  bigint auto_increment
        primary key,
    age         varchar(255)  null,
    code        varchar(255)  null,
    description text          null,
    name        varchar(255)  null,
    price       int           null,
    thumbnail   varchar(1000) null
);

create table `like`
(
    product_id bigint not null,
    user_id    bigint not null,
    primary key (product_id, user_id),
    constraint FK828ubtgdpm08usoxl0l3m6j7r
        foreign key (user_id) references user (user_id),
    constraint FKiqgh5ch0u92d1ys98byj9xoqj
        foreign key (product_id) references product (product_id)
);

create table nutrition
(
    nutrition_id bigint auto_increment
        primary key,
    calcium      float  null,
    fat          float  null,
    fiber        float  null,
    mineral      float  null,
    moisture     float  null,
    phosphorus   float  null,
    protein      float  null,
    product_id   bigint null,
    constraint FK16dy9uaf2qnmw472t9ex6gcqt
        foreign key (product_id) references product (product_id)
);

create table pet_allergy
(
    allergy_id bigint not null,
    pet_id     bigint not null,
    primary key (allergy_id, pet_id),
    constraint FKcb1exkr4yfu21qt6cuyx9fp78
        foreign key (allergy_id) references allergy (allergy_id),
    constraint FKkal8e9nth6d42bubednisxd7l
        foreign key (pet_id) references pet (pet_id)
);

create table pet_image
(
    image_id bigint auto_increment
        primary key,
    path     varchar(1000) null,
    pet_id   bigint        null,
    constraint FKlt05a9ym435170i60mwc36egl
        foreign key (pet_id) references pet (pet_id)
);

create table problem
(
    problem_id bigint auto_increment
        primary key,
    name       varchar(255) null
);

create table pet_problem
(
    pet_id     bigint not null,
    problem_id bigint not null,
    primary key (pet_id, problem_id),
    constraint FKdmpbg35hc194t7xut6w31a7vl
        foreign key (pet_id) references pet (pet_id),
    constraint FKk83kp2n2ir6opjp2qq0rkqyq9
        foreign key (problem_id) references problem (problem_id)
);

create table product_description_image
(
    image_id   bigint auto_increment
        primary key,
    path       varchar(1000) null,
    product_id bigint        null,
    constraint FKcs6qs8crscj0g9tndvld7mk8q
        foreign key (product_id) references product (product_id)
);

create table product_image
(
    image_id   bigint auto_increment
        primary key,
    path       varchar(1000) null,
    product_id bigint        null,
    constraint FK6oo0cvcdtb6qmwsga468uuukk
        foreign key (product_id) references product (product_id)
);

create table product_problem
(
    problem_id bigint not null,
    product_id bigint not null,
    primary key (problem_id, product_id),
    constraint FK81d9l3f5708wttllka5xjfj3
        foreign key (product_id) references product (product_id),
    constraint FKk9ax1vmrb08wlv35iu09t11wb
        foreign key (problem_id) references problem (problem_id)
);

create table review
(
    review_id  bigint auto_increment
        primary key,
    content    varchar(255) null,
    title      varchar(255) null,
    product_id bigint       null,
    user_id    bigint       null,
    constraint FKiyof1sindb9qiqr9o8npj8klt
        foreign key (product_id) references product (product_id),
    constraint FKj8m0asijw8lfl4jxhcps8tf4y
        foreign key (user_id) references user (user_id)
);

create table review_image
(
    image_id  bigint auto_increment
        primary key,
    path      varchar(1000) null,
    review_id bigint        null,
    constraint FK16wp089tx9nm0obc217gvdd6l
        foreign key (review_id) references review (review_id)
);

create table token
(
    user_id       bigint       not null
        primary key,
    refresh_token varchar(500) null,
    constraint FKl10xjn274m2rkxo54knt2xqvy
        foreign key (user_id) references user (user_id)
);


