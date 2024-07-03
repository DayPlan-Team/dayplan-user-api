create table location_citys
(
    id          bigint auto_increment
        primary key,
    code        bigint       not null,
    coordinates varchar(255) not null,
    name        varchar(255) not null
);

create table location_districts
(
    id          bigint auto_increment
        primary key,
    code        bigint       not null,
    coordinates varchar(255) not null,
    name        varchar(255) not null
);

create table places
(
    id                         bigint auto_increment
        primary key,
    created_at                 datetime(6)  not null,
    modified_at                datetime(6)  not null,
    address                    varchar(255) not null,
    administrative_category_id varchar(64)  not null,
    description                varchar(255) null,
    latitude                   double       not null,
    link                       text         null,
    longitude                  double       not null,
    place_category             varchar(64)  not null,
    place_name                 varchar(255) not null,
    road_address               varchar(255) not null,
    telephone                  varchar(100) null,
    user_registration_count    bigint       not null
);

create table terms
(
    id           bigint auto_increment
        primary key,
    created_at   datetime(6)  not null,
    modified_at  datetime(6)  not null,
    content      varchar(255) not null,
    is_mandatory bit          not null,
    sequence     int          not null
);

create table terms_agreements
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6) not null,
    modified_at datetime(6) not null,
    is_agreed   bit         not null,
    terms_id    bigint      not null,
    user_id     bigint      not null
);

create table user_current_locations
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6) not null,
    modified_at datetime(6) not null,
    latitude    double      not null,
    longitude   double      not null,
    user_id     bigint      not null
);

create table user_location_histories
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6) not null,
    modified_at datetime(6) not null,
    latitude    double      not null,
    longitude   double      not null,
    user_id     bigint      not null
);

create table users
(
    id                        bigint auto_increment
        primary key,
    created_at                datetime(6)  not null,
    modified_at               datetime(6)  not null,
    email                     varchar(255) not null,
    is_verified               bit          not null,
    is_mandatory_terms_agreed bit          not null,
    nick_name                 varchar(255) null,
    user_account_status       varchar(32)  not null
);


create index idx__places_place_name
    on places (address);

create index idx__places_administrative_category_id
    on places (administrative_category_id);

create index idx__places_address
    on places (address);

create index idx__user_location_histories_user_id
    on user_location_histories (user_id);

create index idx__current_locations_user_id
    on user_current_locations (user_id);

create index idx__terms_agreements_user_id
    on terms_agreements (user_id);

create index idx__users_email
    on users (email);