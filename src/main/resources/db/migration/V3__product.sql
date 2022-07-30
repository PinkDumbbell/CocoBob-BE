alter table product drop age;
alter table product add category varchar(100);

create table ingredient
(
    ingredient_id bigint auto_increment
       primary key,
    beef        boolean   null,
    mutton      boolean   null,
    chicken     boolean   null,
    duck        boolean   null,
    turkey      boolean   null,
    meat        boolean   null,
    salmon      boolean   null,
    hydrolytic_beef     boolean   null,
    hydrolytic_mutton   boolean   null,
    hydrolytic_chicken  boolean   null,
    hydrolytic_duck     boolean   null,
    hydrolytic_turkey   boolean   null,
    hydrolytic_meat     boolean   null,
    hydrolytic_salmon   boolean   null,
    product_id          bigint    not null,
    foreign key (product_id) REFERENCES product (product_id)
);

alter table nutrition add amount_of_protein_per_mcal double;
alter table nutrition add amount_of_fat_per_mcal double;
alter table nutrition add amount_of_fiber_per_mcal double;
alter table nutrition add amount_of_mineral_per_mcal double;
alter table nutrition add amount_of_calcium_per_mcal double;
alter table nutrition add amount_of_phosphorus_per_mcal double;
alter table nutrition add kcal_per_kg double;
alter table nutrition add is_aafco_satisfied boolean;

create table pet_property
(
    pet_property_id bigint auto_increment
        primary key,
    property    varchar(255)    null,
    product_id  bigint      not null,
    foreign key (product_id) REFERENCES product (product_id)
);