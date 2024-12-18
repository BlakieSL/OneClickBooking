create table if not exists employee
(
    id          int auto_increment
        primary key,
    description varchar(255) null,
    username    varchar(255) not null
);

create table if not exists employee_availability
(
    id          int auto_increment
        primary key,
    dayOfWeek   enum ('FRIDAY', 'MONDAY', 'SATURDAY', 'SUNDAY', 'THURSDAY', 'TUESDAY', 'WEDNESDAY') not null,
    endTime     time(6)                                                                             not null,
    startTime   time(6)                                                                             not null,
    employee_id int                                                                                 null,
    constraint FKawpxqe7n22n3e3ikcwsehqmdx
        foreign key (employee_id) references employee (id)
);

create table if not exists role
(
    id   int auto_increment
        primary key,
    name enum ('ADMIN', 'USER') not null
);

create table if not exists service_point
(
    id       int auto_increment
        primary key,
    email    varchar(255) not null,
    location varchar(255) not null,
    name     varchar(255) not null,
    phone    varchar(255) not null
);

create table if not exists service_point_employee
(
    id               int auto_increment
        primary key,
    employee_id      int null,
    service_point_id int null,
    constraint FKcbcrfj9r1alufo83rbsptu75y
        foreign key (employee_id) references employee (id),
    constraint FKn9c6gaui7vt8s2ejwltsriaeu
        foreign key (service_point_id) references service_point (id)
);

create table if not exists treatment
(
    id          int auto_increment
        primary key,
    description varchar(255) not null,
    duration    int          not null,
    name        varchar(255) not null,
    price       double       not null
);

create table if not exists treatment_employees
(
    treatments_id int not null,
    employees_id  int not null,
    primary key (treatments_id, employees_id),
    constraint FK4ce2uswrvum7e3ievndv7nyra
        foreign key (treatments_id) references treatment (id),
    constraint FKnw72c8u851xhlq9v0icc4uiku
        foreign key (employees_id) references employee (id)
);

create table if not exists user
(
    id       int auto_increment
        primary key,
    email    varchar(70) not null,
    name     varchar(50) not null,
    password varchar(60) not null,
    surname  varchar(50) not null
);

create table if not exists booking
(
    id               int auto_increment
        primary key,
    date             datetime(6) not null,
    employee_id      int         null,
    service_point_id int         not null,
    treatment_id     int         null,
    user_id          int         not null,
    constraint FK1dnnhqt4wl3v6a72hxiarf7lg
        foreign key (employee_id) references employee (id),
    constraint FKkgseyy7t56x7lkjgu3wah5s3t
        foreign key (user_id) references user (id),
    constraint FKsjasmdiqd01d8tyqypnjihayt
        foreign key (treatment_id) references treatment (id),
    constraint FKsrfiysh4owvdg92206d3dwnom
        foreign key (service_point_id) references service_point (id)
);

create table if not exists review
(
    id         int auto_increment
        primary key,
    rating     int          not null,
    text       varchar(255) null,
    booking_id int          not null,
    constraint UKm685o801uf70i84jf94qq3d0b
        unique (booking_id),
    constraint FKk4xawqohtguy5yx5nnpba6yf3
        foreign key (booking_id) references booking (id)
);

create table if not exists user_roles
(
    users_id int not null,
    roles_id int not null,
    primary key (users_id, roles_id),
    constraint FK7ecyobaa59vxkxckg6t355l86
        foreign key (users_id) references user (id),
    constraint FKj9553ass9uctjrmh0gkqsmv0d
        foreign key (roles_id) references role (id)
);

insert into role (id, name)
VALUES (1, 'USER'), (2, 'ADMIN');
