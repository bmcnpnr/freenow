/**
 * CREATE Script for init of DB
 */

-- Create 6 cars
insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (1, now(), false, '06 ABC 123',
5, false, 5, 'DIESEL', 'Volkswagen');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (2, now(), false, '07 DEF 456',
2, true, 4, 'BIO_DIESEL', 'Audi');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (3, now(), false, '08 GHI 789',
10, true, 5, 'ELECTRIC', 'Tesla');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (4, now(), false, '09 JKL 123',
5, false, 5, 'HYBRID', 'Toyota');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (5, now(), false, '10 MNO 456',
3, true, 4, 'SUPER_UNLEADED', 'Citroen');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (6, now(), false, '11 PRS 789',
6, true, 5, 'UNLEADED', 'BMW');

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

--Create 3 drivers with pre-selected cars
insert into driver (id, date_created, deleted, online_status, password, username, car_id) values (9, now(), false, 'ONLINE',
'driver09pw', 'driver09', '1');

insert into driver (id, date_created, deleted, online_status, password, username, car_id) values (10, now(), false, 'ONLINE',
'driver10pw', 'driver10', '2');

insert into driver (id, date_created, deleted, online_status, password, username, car_id) values (11, now(), false, 'ONLINE',
'driver11pw', 'driver11', '3');

--create roles for drivers
insert into driverdo_roles(driverdo_id, roles) values (1, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (1, 'ADMIN');
insert into driverdo_roles(driverdo_id, roles) values (2, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (3, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (4, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (5, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (6, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (7, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (8, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (9, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (10, 'USER');
insert into driverdo_roles(driverdo_id, roles) values (11, 'USER');
