-- inserting new employees
insert into employee (id, description, username)
values (1, 'test_description1', 'test_username1');

insert into employee (id, description, username)
values (2, 'test_description2', 'test_username2');

insert into employee (id, description, username)
values (3, 'test_description3', 'test_username3');


--inserting new employee availabilities
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (1, 'MONDAY', '18:00:00', '09:00:00', 1);

insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (2, 'TUESDAY', '18:00:00', '09:00:00', 1);

insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (3, 'THURSDAY', '18:00:00', '09:00:00', 1);


insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (4, 'MONDAY', '14:00:00', '09:00:00', 2);

insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (5, 'MONDAY', '20:00:00', '15:00:00', 2);


insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (6, 'MONDAY', '18:00:00', '09:00:00', 3);

insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id)
values (7, 'TUESDAY', '18:00:00', '09:00:00', 3);


-- inserting new service points
insert into service_point (id, email, location, name, phone)
values (1, 'test_email1@gmail.com', 'test_location1', 'test_name1', 'test_phone1');

insert into service_point (id, email, location, name, phone)
values (2, 'test_email2@gmail.com', 'test_location2', 'test_name2', 'test_phone2');


-- inserting new service point employees
insert into service_point_employee (id, employee_id, service_point_id)
values (1, 1, 1);

insert into service_point_employee (id, employee_id, service_point_id)
values (2, 2, 1);

insert into service_point_employee (id, employee_id, service_point_id)
values (3, 3, 2);

insert into service_point_employee (id, employee_id, service_point_id)
values (4, 3, 2);


-- inserting new treatments
insert into treatment (id, description, duration, name, price)
values (1, 'test_description1', 1, 'test_name1', 1.0);

insert into treatment (id, description, duration, name, price)
values (2, 'test_description2', 2, 'test_name2', 2.0);

insert into treatment (id, description, duration, name, price)
values (3, 'test_description3', 3, 'test_name3', 3.0);


-- inserting new treatment employees
insert into treatment_employees (treatments_id, employees_id)
values (1, 1);

insert into treatment_employees (treatments_id, employees_id)
values (2, 1);

insert into treatment_employees (treatments_id, employees_id)
values (2, 2);

insert into treatment_employees (treatments_id, employees_id)
values (3, 3);


-- inserting new user with role USER
insert into user (id, email, name, password, surname)
values (1,
        'test_email1@gmail.com',
        'test_name1',
        '$2a$10$Az0Jh30TmMOG05MaTDI3f.9lmeJtbtz9Ao7fagZOJLftZ0nliltlS',
        'test_surname1'
       );

insert into user_roles (users_id, roles_id)
VALUES (1, 1);


insert into user (id, email, name, password, surname)
values (2,
        'test_email2@gmail.com',
        'test_name2',
        '$2a$10$Az0Jh30TmMOG05MaTDI3f.9lmeJtbtz9Ao7fagZOJLftZ0nliltlS',
        'test_surname2'
       );

insert into user_roles (users_id, roles_id)
VALUES (2, 1);


insert into user (id, email, name, password, surname)
values (3,
        'test_email3@gmail.com',
        'test_name3',
        '$2a$10$Az0Jh30TmMOG05MaTDI3f.9lmeJtbtz9Ao7fagZOJLftZ0nliltlS',
        'test_surname3'
       );

insert into user_roles (users_id, roles_id)
VALUES (3, 2);


-- inserting new bookings
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id, status)
values (1, '2022-01-01 00:00:00', 1, 1, 1, 1, 'COMPLETED');

insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id, status)
values (2, '2022-01-02 00:00:00', 2, 1, 2, 1, 'COMPLETED');

insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id, status)
values (3, '2025-01-06 15:00:00', 1, 1, 1, 1, 'COMPLETED');


--inserting new reviews
insert into review (id, rating, date, text, booking_id)
values (1,5, '2023-12-25','test_text1', 1);

insert into review (id, rating, date, text, booking_id)
values (2,4, '2023-12-26', 'test_text2', 2 );

