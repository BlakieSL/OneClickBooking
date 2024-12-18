DELETE FROM user_roles
WHERE users_id IN (1, 2, 3);

DELETE FROM user
WHERE email IN ('test_email1@gmail.com',
                'test_email2@gmail.com',
                'test_email3@gmail.com'
    );

ALTER TABLE user AUTO_INCREMENT = 1;

ALTER TABLE user_roles AUTO_INCREMENT = 1;
