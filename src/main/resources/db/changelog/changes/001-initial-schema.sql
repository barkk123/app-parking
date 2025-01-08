create table if not exists parking_location
(
    id              bigint primary key not null auto_increment,
    street          varchar(64)        not null,
    building_number varchar(8)         not null,
    city            varchar(32)        not null,
    zip_code        varchar(32)        not null
    );

create table if not exists parking
(
    id                      bigint primary key                                not null auto_increment,
    name                    varchar(64)                                       not null,
    type                    varchar(32) check (type in ('PUBLIC', 'PRIVATE')) not null,
    secured                 boolean default false,
    number_of_parking_spots integer                                           not null,
    location_id             bigint                                            not null references parking_location (id),
    hourly_rate             numeric(6, 2)                  default                   null
    );

create table if not exists parking_spot
(
    id         bigint primary key                                 not null auto_increment,
    number     integer                                            not null,
    status     varchar(32) check (status in ('FREE', 'OCCUPIED')) not null,
    parking_id bigint                                             not null references parking (id)
    );

create table if not exists vehicle
(
    license_number varchar(8) primary key not null,
    mark           varchar(32),
    model          varchar(32)
    );

create table if not exists parking_ticket
(
    id                     bigint unique primary key not null auto_increment,
    parking_spot_id        bigint             not null references parking_spot (id),
    vehicle_license_number varchar(8)         not null references vehicle (license_number),
    status                 varchar(32)        not null check (status in ('PAID', 'ACTIVE')),
    arrival_time           timestamp          not null,
    departure_time         timestamp,
    fee                    numeric(6, 2)
    );