-- inserting new user with role USER
insert into user (id, email, name, password, surname)
values (1,
        'test_email1@gmail.com',
        'test_name1',
        '$2a$10$fURaiWnioIAZeDtJ6k/0aOUBJ0dmo9uyJvFBKQaMNd5rXOwo5fYXO',
        'test_surname1'
);


insert into user_roles (users_id, roles_id)
VALUES (1, 1);

-- inserting new user with role ADMIN
insert into user (id, email, name, password, surname)
values (2,
        'test_email2@gmail.com',
        'test_name2',
        '$2a$10$fURaiWnioIAZeDtJ6k/0aOUBJ0dmo9uyJvFBKQaMNd5rXOwo5fYXO',
        'test_surname2'
);

insert into user_roles (users_id, roles_id)
values (2, 2);


-- inserting new user with roles:[USER, ADMIN]
insert into user (id, email, name, password, surname)
values (3,
        'test_email3@gmail.com',
        'test_name3',
        '$2a$10$fURaiWnioIAZeDtJ6k/0aOUBJ0dmo9uyJvFBKQaMNd5rXOwo5fYXO',
        'test_surname3'
);

insert into user_roles (users_id, roles_id)
values (3, 1), (3, 2);




