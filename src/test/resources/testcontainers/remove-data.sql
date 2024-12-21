delete from treatment_employees ;
delete from service_point_employee ;
delete from review;
delete from booking ;
delete from user_roles;
delete from user;
delete from service_point;
delete from treatment;
delete from employee_availability;
delete from employee;

-- Resetting AUTO_INCREMENT counters
ALTER TABLE treatment_employees AUTO_INCREMENT = 1;
ALTER TABLE review AUTO_INCREMENT = 1;
ALTER TABLE booking AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;
ALTER TABLE user_roles AUTO_INCREMENT = 1;
ALTER TABLE service_point_employee AUTO_INCREMENT = 1;
ALTER TABLE service_point AUTO_INCREMENT = 1;
ALTER TABLE treatment AUTO_INCREMENT = 1;
ALTER TABLE employee_availability AUTO_INCREMENT = 1;
ALTER TABLE employee AUTO_INCREMENT = 1;
