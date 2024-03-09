-- You can use this file to load seed data into the database using SQL statements
insert into role (id, name) values (1, 'Document Manager');
insert into role (id, name) values (2, 'Standard User');
insert into role (id, name) values (3, 'Trainee');

insert into department (name,  description, parent_id) values ('Coding/Charge Posting',  'Coding/Charge Posting', null);
insert into department (name,  description, parent_id) values ('Payment',  'Payment', null);
insert into department (name,  description, parent_id) values ('AR',  'AR', null);
insert into department (name,  description, parent_id) values ('Accounting',  'Accounting', null);
insert into department (name,  description, parent_id) values ('Credentialing',  'Credentialing', null);

insert into user (id, first_name,last_name,password, email, contact,role_id) values (0, 'John Smith','admin','password', 'john.smith@mailinator.com', '2125551212',1);

insert into user_dept_rel(user_id,dept_id) values(0,1);

insert into insurance (id, name, description, is_deleted) values (0, 'insurance1', 'test description for insurance1',0);
