create table pet_property
(
    pet_property_id bigint auto_increment
        primary key,
    property    varchar(255)    null,
    product_id  bigint      not null,
    foreign key (product_id) REFERENCES product (product_id)
);