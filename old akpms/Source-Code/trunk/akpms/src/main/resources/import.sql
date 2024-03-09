-- You can use this file to load seed data into the database using SQL statements

INSERT INTO permission (id,name,description) values ( 'P-1','Trainee Evaluation','This permission is related with &quot;Trainee Evaluation&quot; module. Assign this permission to user so s/he can evaluate the trainees and get the trainee evaluation report.');
INSERT INTO permission (id,name,description) values ('P-2','QA Worksheet','This permission is related with &quot;QA&quot; module. Standard users who have this permission can access QA module.');
INSERT INTO permission (id,name,description) values ('P-3','Manager to Handle Esclation','This permission is related with &quot;AR&quot; module. Standard users who have this permission can manage esclations.');
--INSERT INTO permission (id,name) values ('P-4','Approve/Reject/Esclate');
INSERT INTO permission (id,name,description) values ('P-5','Offset Manager','This permission is related with &quot;Payment batch offset posting&quot; module. Standard users who have this permission can create (Offset) payment batches to offset posting purpose.');

INSERT INTO permission (id,name,description) values ('P-6','Process reject log after resolution','This permission is related with &quot;Charge batch productivity&quot; module. Standard users who have this permission can edit rejection records even after resolution. Because it is possible that some records can be with dummy CTP the users who have this permission can access these records after resolution.');
INSERT INTO permission (id,name,description) values ('P-7','Process on-hold','This permission is related with &quot;Charge batch productivity&quot; module. Users who have this permission can access on hold records from charge batch productivity section.');
INSERT INTO permission (id,name,description) values ('P-8','Create/Revise Payment Batch','This permission is related with &quot;Payment batch system&quot; module. Users who have permission can create or revise payment batches in PBS.');
INSERT INTO permission (id,name,description) values ('P-9','Re-update payment batch tickets after productivity','This permission is related with &quot;Payment batch system&quot; module. A standard user can re-update the payment batch if he has permission to update the payment batch after productivity. He will have permission to change &quot;Posted by&quot;, &quot;Posted Date&quot;, &quot;CT posted total&quot; etc fields.');
INSERT INTO permission (id,name,description) values ('P-10','Re-update charge batch tickets after productivity','This permission is related with &quot;Charge batch processing&quot; module. A standard user can re-update the charge batch if he has permission to update the charge batch after productivity. He will have permission to change &quot;Posted by&quot; and &quot;Posted Date&quot;.');

INSERT INTO permission (id,name,description) values ('P-11','Sub-Admin for charge batching system','This permission is related with &quot;Charge batch processing&quot; module. If standard user from charge department has this permission then s/he can access &quot;Manage doctor&quot; and &quot;Manage insurance&quot; modules.');
INSERT INTO permission (id,name,description) values ('P-12','Sub-Admin for payment batching system','This permission is related with &quot;Payment batch system&quot; module. If standard user from payment department has this permission then s/he can access &quot;Manage doctor&quot;, &quot;Manage payment type&quot; and &quot;Manage insurance&quot; modules.');
INSERT INTO permission (id,name,description) values ('P-13','Sub-Admin for cash log (Accounting) system','This permission is related with &quot;Cashlog reports&quot; module. If standard user has this permission then s/he can access cash log reports.');
INSERT INTO permission (id,name,description) values ('P-14','Document Manager','This permission is related Process Manual management. Document manager can add/edit/activate/deactivate or delete process manuals.');
INSERT INTO permission (id,name,description) values ('P-15','Create/Revise Charge Batch','This permission is related with &quot;Charge batch system&quot; module. Users who have permission can create charge batches in CBS.');

INSERT INTO role (id, name) values (1,'Admin');
--INSERT INTO role (id, name) values (2,'Document Manager');
INSERT INTO role (id, name) values (3,'Standard User');
INSERT INTO role (id, name) values (4,'Trainee');
INSERT INTO role (id, name) values (5,'Other');

INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'Peachy','Paulino','2381fd1582db6f37cb3f21b9c9a5c3e6', 'ppaulino@argusmso.com', '123456789',1, 1, now(), 1);
--INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'document','manager','2381fd1582db6f37cb3f21b9c9a5c3e6', 'dmar@yopmail.com', '123456789',3, 1, now(), 1);
--INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'standard','user','2381fd1582db6f37cb3f21b9c9a5c3e6', 'stdar@yopmail.com', '123456789',3, 1, now(), 1);
--INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'trainee','user','2381fd1582db6f37cb3f21b9c9a5c3e6', 'traineear@yopmail.com', '123456789',4, 1, now(), 1);

INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES	('<p>Hello ArgusCore Users, <br /><br />The process manual PROCESS_MANUAL has been ADDED_MODIFIED.&nbsp;If this is a topic that you deal with on&nbsp;a regular basis, please take the time to read the updates/changes the soonest possible.&nbsp; It is always good to know!<br /> &nbsp;</p><br/><br/><p><em>Please do not reply to this email.&nbsp;Thank you.</em><br />&nbsp;</p><p>ArgusCore Administrator<br />&nbsp;</p>', 'ArgusCore Process Manual Update Alerts', 1, 1, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES ('<p>Hello ArgusCore User, <br /><br />The Trainee TRAINEE_NAME has been evaluated by EVALUATOR_NAME as: <br /><br />ratings: TRAINEE_RATING/5 <br />comment: TRAINEE_COMMENT</p><p>This is an information for you.&nbsp; If this requires an action from you, please login to ArgusCore the soonest possible.<br /><br /><em>Please do not reply to this email.&nbsp;Thank you.</em></p><p>ArgusCore Administrator<br />&nbsp;</p>', 'ArgusCore Notification on Trainee Evaluations', 1, 1, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES ('<p>Hi USER_FIRST_NAME, <br /><br />Welcome to ArgusCore!&nbsp; Your account has been created on SITE_URL. Please take the time to login the soonest you can.&nbsp;&nbsp;By this time you&nbsp;must have gone through an overview training on ArgusCore.&nbsp; If you have not, please contact your immediate Team Leader and ask for an overview training.<br /><br />UserName: USER_EMAIL <br />Password: USER_PASSWORD <br />Login by going to SITE_URL link. <br />&nbsp;</p><p><em>Please do not reply to this email.&nbsp; Thank you.</em></p><p>ArgusCore Administrator<br />&nbsp;</p>','ArgusCore Registration Email', 1, 0, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES ('<p>Hi USER_FIRST_NAME, <br /><br />Your Password has been changed. Your new password is NEW_PASSWORD.&nbsp; Please protect your password at all times.<br />&nbsp;</p><p><em>Please do not reply to this email. Thank you.</em><br />&nbsp;</p><p>ArgusCore Administrator</p>','ArgusCore Password Changed', 1, 0, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES	('<p>Hi USER_FIRST_NAME, <br /><br />We believe that you have lost or forgotten your password.&nbsp; We have assigned you a new password upon your request.&nbsp; Please protect you password at all times.<br /><br />The password is USER_PASSWORD.</p><p>&nbsp;</p><p><em>Please do not reply to this email. Th</em><em>ank you.</em><br /><br />ArgusCore Administrator</p>', 'ArgusCore Password Recovery', 1, 0, now(), 1);

--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Coding and Charge Posting',  'Coding/Charge Posting', null, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Payment',  'Payment', null, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('AR',  'AR', null, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Accounting',  'Accounting', null, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Credentialing',  'Credentialing', null, now(), 1);

INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (1, '2013-05-08 18:04:50', NULL, 0, 'Parent Department of Coding, Charge Entry and Demo and Verification', 'Coding and Charge Entry Department', 1, 1, NULL, NULL);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (2, '2013-05-08 18:04:50', NULL, 0, 'Parent department of payment posting, balancing, offsets, etc.', 'Payments Department', 1, 1, NULL, NULL);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (3, '2013-05-08 18:04:50', NULL, 0, 'Parent department of HMO, MCL/CHDP, MCR, PPO, Self Pay/CEP, WC.', 'Accounts Receivable Department', 1, 1, NULL, NULL);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (4, '2013-05-08 18:04:50', NULL, 0, 'This is the parent department for Accounts Payable process and Accounting Reports.', 'Accounting Department', 1, 1, NULL, NULL);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (5, '2013-05-08 18:04:50', NULL, 0, 'Credentialing', 'Credentialing', 0, 1, NULL, NULL);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (6, '2013-05-08 18:04:50', NULL, 0, 'Team that handles all the charge entry of primary care and specialist charges.', 'Charge Entry', 1, 1, NULL, 1);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (7, '2013-05-08 18:04:50', NULL, 0, 'Coding team that handles Primary Care providers.', 'Coding Primary Care', 1, 1, NULL, 1);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (8, '2013-05-08 18:04:50', NULL, 0, 'Coding team that handles Specialist providers.', 'Coding Specialist', 1, 1, NULL, 1);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (9, '2013-05-08 18:04:50', NULL, 0, 'Team that handles all demo, eligibility and verification.', 'Demo and Verification', 1, 1, NULL, 1);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (10, '2013-05-08 18:04:50', NULL, 0, 'Payments Posting', 'Payments Posting', 1, 1, NULL, 2);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (11, '2013-05-08 18:04:50', NULL, 0, 'Treasury', 'Treasury', 1, 1, NULL, 2);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (12, '2013-05-08 18:04:50', NULL, 1, 'p3', 'p3-payment', 1, 1, NULL, 2);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (13, '2013-05-08 18:04:50', NULL, 0, 'Team that handles all accounts receivable management for HMO insurances.', 'HMO', 1, 1, NULL, 3);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (14, '2013-05-08 18:04:50', NULL, 0, 'MCL and CHDP', 'MCL and CHDP', 1, 1, NULL, 3);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (15, '2013-05-08 18:04:50', NULL, 0, 'Team that handles all accounts receivable management for MCR insurance.', 'MCR', 1, 1, NULL, 3);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (16, '2013-05-08 18:04:50', NULL, 0, 'Team that handles all accounts receivable management for PPO insurances.', 'PPO', 1, 1, NULL, 3);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (17, '2013-05-08 18:04:50', NULL, 0, 'Self Pay and CEP', 'Self Pay and CEP', 1, 1, NULL, 3);
INSERT INTO `department` (`id`, `created_on`, `modified_on`, `is_deleted`, `description`, `name`, `status`, `created_by`, `modified_by`, `parent_id`) VALUES (18, '2013-05-08 18:04:50', NULL, 0, 'Team that handles all accounts receivable management for Work Comp insurances.', 'Work Comp', 1, 1, NULL, 3);

--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('c1',  'c1-coding', 1, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('c2',  'c2-coding', 1, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('c3',  'c3-coding', 1, now(), 1);

--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('p1',  'p1-payment', 2, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('p2',  'p2-payment', 2, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('p3',  'p3-payment', 2, now(), 1);

--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('HMO',  'HMO-AR', 3, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('PPO',  'PPO-AR', 3, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('MCL',  'MCL-AR', 3, now(), 1);
--INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('MCR',  'MCR-AR', 3, now(), 1);

INSERT INTO admin_settings(procees_manaul_read_status_ratings,ids_argus_ratings,argus_ratings, created_on, created_by) values('20','40','40', now(), 1);

-- process manual dummy data [start]
INSERT INTO `process_manual` (`content`, `position`, `title`, `parent_id`, created_on, created_by) VALUES ( "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore",  1, 'Main Process Manual', NULL, now(), 1);
INSERT INTO `process_manual` (`content`,  `position`, `title`, `parent_id`, created_on, created_by) VALUES ( "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore", 2, 'Main Sub- Process Manual 1', 1, now(), 1);
INSERT INTO `process_manual` (`content`, `position`, `title`, `parent_id`, created_on, created_by) VALUES ("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore",  3, 'Main Sub- Process Manual 2', 1, now(), 1);

INSERT INTO `process_manual_position` (`section_id`, `parent_id`, `position`) VALUES (1, 1, 1);
INSERT INTO `process_manual_position` (`section_id`, `parent_id`, `position`) VALUES (2, 1, 2);
INSERT INTO `process_manual_position` (`section_id`, `parent_id`, `position`) VALUES (3, 1, 3);
-- process manual dummy data [end]

INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('Admin Income', 'Admin Income', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('Ancillary Income', 'Ancillary Income', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('CAP', 'CAP', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('EFT/FFS', 'EFT/FFS', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('FFS', 'FFS', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('IPA FFS', 'IPA FFS', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('NSF', 'NSF', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('Offset', 'Offset', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('OTC', 'OTC', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('Patient Payment', 'Patient Payment', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('Refund-AP', 'Refund-AP', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('System Refund', 'System Refund', 1,  1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `created_by`) VALUES ('N/A', 'N/A', 1,  1);

INSERT INTO `money_source` (`name`) VALUES ('EFT');
INSERT INTO `money_source` (`name`) VALUES ('LockBox');
INSERT INTO `money_source` (`name`) VALUES ('Mail');
--INSERT INTO `money_source` (`name`) VALUES ('Vault Check');
--INSERT INTO `money_source` (`name`) VALUES ('Credit Card');
--INSERT INTO `money_source` (`name`) VALUES ('Vault Cash');
INSERT INTO `money_source` (`name`) VALUES ('Pt. Service/Mail');
INSERT INTO `money_source` (`name`) VALUES ('Office');

INSERT INTO `admin_income` (`name`) VALUES ('Admin Income');
INSERT INTO `admin_income` (`name`) VALUES ('IPA Bonus');
INSERT INTO `admin_income` (`name`) VALUES ('MCR Incentive');
INSERT INTO `admin_income` (`name`) VALUES ('Carvenue Admin Income');
INSERT INTO `admin_income` (`name`) VALUES ('Directorship');
INSERT INTO `admin_income` (`name`) VALUES ('Agency');

-- doctor data [start]
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("ProHealth","0000",0.01,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("ABC","3000",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("AIM","3050",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("AIM Psych","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Berkson, Richard","3190",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Borsada, Minal","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Bryant, Diana","3120",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Care Clinic","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Chau, Peter","3240",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Chaudhary, Archna","4920",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Chaudhary, Vinita","3920",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Clark, Dennis","3670",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Comer, Robert","3260",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("CVA","4800",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Dejong, Clayton","3130",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("DeLaRosa, Pedro","3160",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Dix, Karen","3660",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Dolan, Geoffrey","3560",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Elledge, Kay","4020",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Eppele, Claudia","4970",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Ferrera, Wanyik, Vesk","3480",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Finkelstein, Stuart","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Goldman, Babak","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Gorlick, Laurence","3630",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Govind, Shobha","3300",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Graham, Gary","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Green, Stephanie","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Hirano, Lance","3410",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Ibarra, Rodolfo","3550",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Ishimaru, Denise","4910",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Issa, John","5540",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Koseff, Perry","3140",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Kuhn, Rebecca","4710",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Lake, Steve","3330",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Lakewood Primary Care","3800",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Liff, Michael","4995",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Lugliani, Robert","3340",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Majeed, Azhar","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Marroco, Christopher","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Mccloy, Thomas","3360",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Mehta, Arvind","3390",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Meier, Martina","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Mortara, Laurie","3400",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Nash, Roger","3640",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Norcross, James","3600",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Pardo, Manuel","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Patel, Sapna","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Richey, Donna","3720",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Richter, Tiffany","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Samawi, Roger","4210",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Schneider, Stefan","4737",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Singer, David","3460",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Singh, Sonia","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("St Francis Primary Care","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Thomas, John","4870",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Tyson, Yvonne","3180",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Viswanathan, Kanchana","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Waraich, Saleem","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("West GI","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Wong, Petra","",0.01,0,0,1,now(),1);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Wyche, Kerry","3490",0.01,0,0,1,now(),1);

INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Advanced Derm","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("American Pacific Med Grp","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Apollo, Sarah","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Applecare Hosp","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Applied Med Technologies","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Beverly Family Care Cntr","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Bridges, Duane","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Chambers, Gregory","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Chen, Hsiao","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Daswani, Moti","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Facey Medical Group Inc","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Faitlowicz, Ana","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Faynsod, Moshe","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Gastrointestinal Associates","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("General Surgeons of LB","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Goldstein, Myron","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Gomez, Olivia","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Gupta, Rishi","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Heilbron Jr, Mauricio","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Hong, Andy","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Ismail, Mohammad","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Jackson, Rozalie","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Kapoor, Amarjeet","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Kapoor, Nikhil","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Kidney Care Intitute","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Kilani, Marwa","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Komatsu, Glen","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Konecny, Jiri","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Lee, Brian","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("LoGiudice, Philip","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Long Beach Memorial Med","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Lui, Stephen","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Medecine & Nephrology (Rubin)","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Mendoza, Astrid","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Miller Children's Hosp","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Miller, Stuart","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Moe, Kyaw","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Murray, James","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Nifty After Fifty Med Grp","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Polanco, Juan","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Pullens, Renita","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Rayhanabad, Jessica","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("RBDE,Professional","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Riker, Jeffrey","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Shah, Anoop","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("So, Vannarith","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Strauss, Michael","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("TMS Wellness Group","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Tarlow, Gardner","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Thaker, Kartik","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("United Hospitalist Inc","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Valerius Med Grp","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Vein Clinic CA","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Waraich, Saleem","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Wu, Lihong (Rubin)","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Yamarik, Rebecca","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Young, Harding","",0,0,0,1,now(),null);
INSERT INTO `doctor` (`name`, `doctorCode`, `payments`, `accounting`, `operations`, `created_by`, `created_on`,`parent_id`) VALUES ("Zinar, Daniel","",0,0,0,1,now(),null);
-- doctor data [end]

-- revenue type data [start]
--INSERT INTO `revenue_type` (`name`, `status`, `code`) VALUES 	('Patient Collection', 1, '0000');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('PMN- Risk Mgmt', 1, '1510');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('PMN- CAQH Data Entry', 1, '1510');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('PMN- Misc Credentialing Work', 1, '1510');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('PMN- Initial Membership', 1, '1510');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('PMN- Biocards', 1, '1510');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('PMN- Annual Fee', 1, '1510');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Medicare Incentive', 1, '3110');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Healthcare Partners Bonus', 1, '3330');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('CareMore Admin Income', 1, '3355');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('IPA Bonus', 1, '3380');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Derm/Lsr/RX', 1, '3400');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Admin Income', 1, '3410');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Directorship', 1, '3410');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Hospitalist', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Other', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Agency', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Diet Program', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Nutritionals', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('FAA', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Hythiam', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('BioLife', 1, '3420');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Old AR', 1, '3490');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('RX', 1, '3800');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Derm', 1, '4110');
INSERT INTO `revenue_type` ( `name`, `status`, `code`) VALUES ('Laser', 1, '4700');
-- revenue type data [end]

-- insurance data [start]
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("AARP",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Access",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Acclaim",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Accountable Health",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Aetna",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Affiliated Doctors of OC",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("AIDS Healthcare Fdn",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Alamitos",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Alliance",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Allied Healthcare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Allied Physicians IPA",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Angeles",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Apple",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("AppleCare Medical Grp",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Arta Health",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Arta NWOC",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Asian Community",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Assoc Hispanic",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Avante Best Care",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("BC - Blue Cross",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("BCBS-B.Cross/B.Shield",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Bright Health Physicians",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Brookshire",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("BS - Blue Shield",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Cal + Care",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Cal Optima",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("California Health Plan",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("CAP Net",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Care First",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Caremore Med Grp",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Centinela Valley IPA",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("CEP/County of LA",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("CHDP",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Choc Health Alliance",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Cigna",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("CPPM",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Dept of Labor",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Downey Select",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Emp Health Systems",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Exceptional Care",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Facey Insurance",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Family Health Alliance",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Genesis Healthcare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Global Care",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Good Samaritan",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Greater Newport Physicians",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Health Care LA",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Health Net",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Health plan of San Mateo",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Healthcare Partners",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Healthmed Services",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Joy Med Assoc",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Kaiser Permanente",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Karing Physician",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("LA Care",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Lakeside Medical",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Lakewood Health Plan",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("MCL - MediCal",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("MCR - Medicare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Med Rec Reprod",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Memorial Healthcare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Mid-Cities",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Molina",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Monarch Healthcare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("MSI",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("N/A",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Nuestra Familia/Prospect",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("NWOC",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Omnicare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Optum CoreSource",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Other",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Pacific Healthcare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Pacific Independent",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("PacifiCare Life",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Patient Pmt",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("PHCOR",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Physician Assoc",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Physician Healthways",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Pioneer Provider",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Pomona Medical Grp",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Premier Physician Network",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("PREMIERCARE IPA",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Presbyterian Health Physicians",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("PRIMARY CARE ASSOCIATES OF CALIFORNIA",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Prospect",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Regal Med Grp",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Salud",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Sedgwick",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("St. Mary",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("StarCare Medical Grp/Prospect",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Talbert",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Torrance Hosp",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Tricare",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("TSI",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("UC - Universal Care",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("UHC - United HC",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("University Health Alliance",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Upland Medical Grp",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("US Treasury",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Various Insurances",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Verdugo Hills Medical",1,now(),1);
INSERT INTO `insurance` (`name`, `created_by`, `created_on`,`status`) VALUES ("Windstone",1,now(),1);
-- insurance data [end]

--Task Name dummy data [start]
INSERT INTO `task_name` (`name`) VALUES ('task1');
INSERT INTO `task_name` (`name`) VALUES ('task2');
INSERT INTO `task_name` (`name`) VALUES ('task3');
INSERT INTO `task_name` (`name`) VALUES ('task4');
INSERT INTO `task_name` (`name`) VALUES ('task5');
--Task Name dummy data [end]

-- ar database data [start]
insert into ar_database (name) values("Prohealth Partners A Medical Group Inc");
insert into ar_database (name) values("Advanced Dermatology Care Center");
insert into ar_database (name) values("American Pacific Medical Group Inc");
insert into ar_database (name) values("Apollo, Sarah DO Inc");
insert into ar_database (name) values("AppleCare Hospitalist Medical Group Inc");
insert into ar_database (name) values("Applied Medical Technologies, Inc");
insert into ar_database (name) values("Beverly Family Care Center");
insert into ar_database (name) values("Bridges, Duane  MD");
insert into ar_database (name) values("California Institute of Cardiovascular Health, Inc");
insert into ar_database (name) values("Chambers, Gregory MD Inc");
insert into ar_database (name) values("Chen, Hsiao Fen MD Inc");
insert into ar_database (name) values("Daswani, Moti S MD Inc");
insert into ar_database (name) values("Facey Medical Group Inc");
insert into ar_database (name) values("Faitlowicz, Ana R MD");
insert into ar_database (name) values("Faynsod, Moshe MD Inc");
insert into ar_database (name) values("Gastrointestinal Associates A Medical Group");
insert into ar_database (name) values("General Surgeons of Long Beach");
insert into ar_database (name) values("Goldstein, S. Myron MD FACS Inc");
insert into ar_database (name) values("Gomez, Olivia C MD");
insert into ar_database (name) values("Gupta, Rishi MD");
insert into ar_database (name) values("Heilbron, Mauricio Jr, MD");
insert into ar_database (name) values("Hoshino, Christopher MD Inc");
insert into ar_database (name) values("Ismail, Mohammad MD Inc");
insert into ar_database (name) values("Jackson, Rozalie V MD");
insert into ar_database (name) values("Kapoor, Amarjeet S MD");
insert into ar_database (name) values("Kapoor, Nikhil MD");
insert into ar_database (name) values("Kidney Care Institute, Inc");
insert into ar_database (name) values("Kilani, Marwa MD");
insert into ar_database (name) values("Komatsu, Glen I MD A Prof Corporation");
insert into ar_database (name) values("Konecny, Jiri DO");
insert into ar_database (name) values("LoGiudice, Philip MD Inc");
insert into ar_database (name) values("Long Beach Memorial Medical Center");
insert into ar_database (name) values("Mendoza, Astrid DO");
insert into ar_database (name) values("Miller Children`s Hospital");
insert into ar_database (name) values("Miller, Stuart MD");
insert into ar_database (name) values("Moe, Kyaw M.D. A Professional Corporation");
insert into ar_database (name) values("Murray, James  MD Inc");
insert into ar_database (name) values("Nifty after Fifty");
insert into ar_database (name) values("Polanco, Juan M. MD");
insert into ar_database (name) values("Pullens, Renita DO., A Medical Corporation");
insert into ar_database (name) values("Rayhanabad, Jessica MD, Inc");
insert into ar_database (name) values("RBDE, Professional Medical Corp");
insert into ar_database (name) values("Renal Specialists Medical Group");
insert into ar_database (name) values("Rubin, Jack MD Inc");
insert into ar_database (name) values("Shah, Anoop K, MD");
insert into ar_database (name) values("So, Vannarith MD");
insert into ar_database (name) values("Strauss, Michael B MD, Inc");
insert into ar_database (name) values("Tarlow, Gardner D MD Inc");
insert into ar_database (name) values("Thaker, Kartik B MD Inc");
insert into ar_database (name) values("United Hospitalist Inc");
insert into ar_database (name) values("Valerius Medical Group and Research Center of Long");
insert into ar_database (name) values("Waraich, Saleem MD, a Professional Medical Corpor");
insert into ar_database (name) values("Yamarik, Rebecca L MD");
insert into ar_database (name) values("Young, Harding MD Inc");
insert into ar_database (name) values("Zinar, Daniel M., MD");

-- ar database data [end]


insert into location (id, name, status) values (1,"Alamitos Belmont Rehab",1);
insert into location (id, name, status) values (2,"Alamitos West Convalescent",1);
insert into location (id, name, status) values (3,"Anaheim General hosp",1);
insert into location (id, name, status) values (4,"Anaheim Terrace Care Center LLC",1);
insert into location (id, name, status) values (5,"Artesia Home Training",1);
insert into location (id, name, status) values (6,"Atlantic Memorial HCC",1);
insert into location (id, name, status) values (7,"Barlow Respiratory Hospital",1);
insert into location (id, name, status) values (8,"Bel vista Healthcare Center",1);
insert into location (id, name, status) values (9,"Bellflower Medical Center ",1);
insert into location (id, name, status) values (10,"Broadway by the Sea",1);
insert into location (id, name, status) values (11,"Buena Vista Care Center ",1);
insert into location (id, name, status) values (12,"California Hospital Medical Center",1);
insert into location (id, name, status) values (13,"Coast Plaza  Hospital",1);
insert into location (id, name, status) values (14,"College Medical Center - SNF",1);
insert into location (id, name, status) values (15,"College Medical Center \ Pacific",1);
insert into location (id, name, status) values (16,"Colonial Care Center",1);
insert into location (id, name, status) values (17,"Community Hospital, Long Beach",1);
insert into location (id, name, status) values (18,"Coumadin Clinic",1);
insert into location (id, name, status) values (19,"Country Villa Belmont Heights Healthcare",1);
insert into location (id, name, status) values (20,"Country Villa East Healthcare Center",1);
insert into location (id, name, status) values (21,"country Villa Seal Beach",1);
insert into location (id, name, status) values (22,"Courtyard Care Center",1);
insert into location (id, name, status) values (23,"Crofton Manor",1);
insert into location (id, name, status) values (24,"Davita Bellflower Dialysis",1);
insert into location (id, name, status) values (25,"Davita Bixby Knolls Dialysis",1);
insert into location (id, name, status) values (26,"Davita Cerritos Dialysis",1);
insert into location (id, name, status) values (27,"Davita Imperial Dialysis",1);
insert into location (id, name, status) values (28,"Davita Kenneth Dialysis",1);
insert into location (id, name, status) values (29,"Davita Kidney Dialysis Care Unit",1);
insert into location (id, name, status) values (30,"Davita Lakewood Dialysis",1);
insert into location (id, name, status) values (31,"DaVita Los Angeles",1);
insert into location (id, name, status) values (32,"Davita Paramount Dialysis",1);
insert into location (id, name, status) values (33,"Davita Premier Dialysis",1);
insert into location (id, name, status) values (34,"Davita Satellite Dialysis",1);
insert into location (id, name, status) values (35,"Davita Silverlake",1);
insert into location (id, name, status) values (36,"Davita Sunrise Dialysis",1);
insert into location (id, name, status) values (37,"Davita United Dialysis",1);
insert into location (id, name, status) values (38,"Derm Clinic",1);
insert into location (id, name, status) values (39,"Downey Regional Medical Center",1);
insert into location (id, name, status) values (40,"Edgewater Convalescent",1);
insert into location (id, name, status) values (41,"Edgewater Skilled Nursing Center",1);
insert into location (id, name, status) values (42,"Fountain Valley Regional Hospital",1);
insert into location (id, name, status) values (43,"Good Samaritan Hospital",1);
insert into location (id, name, status) values (44,"Harbor View Adolescent Center",1);
insert into location (id, name, status) values (45,"Home",1);
insert into location (id, name, status) values (46,"kindred hosp south Bay",1);
insert into location (id, name, status) values (47,"Kindred Hospital La Mirada",1);
insert into location (id, name, status) values (48,"Kindred Hospital South Bay Tri-City",1);
insert into location (id, name, status) values (49,"Kindred Hospital Westminster",1);
insert into location (id, name, status) values (50,"Knott Ave Care Ctr",1);
insert into location (id, name, status) values (51,"Knott Avenue Care Center",1);
insert into location (id, name, status) values (52,"La Palma Intercommunity Hospital",1);
insert into location (id, name, status) values (53,"Lakewood Park Health Ctr",1);
insert into location (id, name, status) values (54,"Lakewood Regional Medical Center",1);
insert into location (id, name, status) values (55,"Little co of Mary - San Pedro Hosp ER",1);
insert into location (id, name, status) values (56,"Little co of Mary - San Pedro Hosp SNF",1);
insert into location (id, name, status) values (57,"Little Co of Mary Hosp IP",1);
insert into location (id, name, status) values (58,"Little Co of Mary Hosp OP",1);
insert into location (id, name, status) values (59,"Little Company of Mary \ San Pedro",1);
insert into location (id, name, status) values (60,"Little Company of Mary \ Torrance",1);
insert into location (id, name, status) values (61,"Long beach Comm hosp ",1);
insert into location (id, name, status) values (62,"Long beach Comm Hosp Rehab",1);
insert into location (id, name, status) values (63,"Long beach Community Hospital ER",1);
insert into location (id, name, status) values (64,"Long beach Mem medical Ctr Rehab",1);
insert into location (id, name, status) values (65,"Long Beach Memorial Medical Center",1);
insert into location (id, name, status) values (66,"Los Alamitos Medical Center",1);
insert into location (id, name, status) values (67,"Los Palos Convalescent Hosp",1);
insert into location (id, name, status) values (68,"Marlora Manor Convalescent",1);
insert into location (id, name, status) values (69,"Mission Palms Health Care Center",1);
insert into location (id, name, status) values (70,"NAF Katy",1);
insert into location (id, name, status) values (71,"NAF Memorial",1);
insert into location (id, name, status) values (72,"NAF North Houston (Airtex)",1);
insert into location (id, name, status) values (73,"NAF Pasadena",1);
insert into location (id, name, status) values (74,"NAF PT Anaheim/GARDEN Grov ",1);
insert into location (id, name, status) values (75,"NAF PT Garden Grove/Seal B",1);
insert into location (id, name, status) values (76,"NAF PT Laguna Woods",1);
insert into location (id, name, status) values (77,"NAF PT Lakewood ",1);
insert into location (id, name, status) values (78,"NAF PT Whittier",1);
insert into location (id, name, status) values (79,"NAF Sugarland ",1);
insert into location (id, name, status) values (80,"Norwalk Community Hospital",1);
insert into location (id, name, status) values (81,"Office ",1);
insert into location (id, name, status) values (82,"Office-Downey",1);
insert into location (id, name, status) values (83,"Pacific Palms HealthCare",1);
insert into location (id, name, status) values (84,"Paramount Meadows Nursing Center",1);
insert into location (id, name, status) values (85,"Park Anaheim Health Care",1);
insert into location (id, name, status) values (86,"Presbyterian Intercommunity Hospital",1);
insert into location (id, name, status) values (87,"Promise (Suburban) Medical Center",1);
insert into location (id, name, status) values (88,"Promise Hospital",1);
insert into location (id, name, status) values (89,"Providence Holy Cross Medical Ctr",1);
insert into location (id, name, status) values (90,"Royal Care SNF ",1);
insert into location (id, name, status) values (91,"San Antonio Community Hospital",1);
insert into location (id, name, status) values (92,"Santa Fe Convalescent Hosp",1);
insert into location (id, name, status) values (93,"Shoreline Healthcare-SNF",1);
insert into location (id, name, status) values (94,"Silver Lake Medical Center",1);
insert into location (id, name, status) values (95,"St. Francis Medical Center",1);
insert into location (id, name, status) values (96,"St. Mary Medical Center",1);
insert into location (id, name, status) values (97,"St. Mary Medical Ctr Rehab",1);
insert into location (id, name, status) values (98,"St. Mary Medical Ctr SNF",1);
insert into location (id, name, status) values (99,"St. Vincent Medical Center",1);
insert into location (id, name, status) values (100,"Sunnyview Convalescent Hosp",1);
insert into location (id, name, status) values (101,"Tarzana Health & Rehabilitation Center",1);
insert into location (id, name, status) values (102,"Torrance Memorial Medical Center",1);
insert into location (id, name, status) values (103,"Tri-City Regional Medical Center",1);
insert into location (id, name, status) values (104,"Twin Palm",1);
insert into location (id, name, status) values (105,"Villa Maria Care Center",1);
insert into location (id, name, status) values (106,"West Anaheim Med IP",1);
insert into location (id, name, status) values (107,"Whittier Hospital Medical Center",1);
insert into location (id, name, status) values (108,"Windsor Convalescent",1);
insert into location (id, name, status) values (109,"Windsor Garden Anaheim",1);
insert into location (id, name, status) values (110,"Windsor Gardens",1);
insert into location (id, name, status) values (111,"Wound Care Clinic",1);

insert into location (name) values ("Bixby Knolls Nursing Home");
insert into location (name) values ("Brier Oak on Sunset LLC");
insert into location (name) values ("Long beach Comm hosp OP");
insert into location (name) values ("Long Beach Memorial wound healing Center");
insert into location (name) values ("Los Alamitos Wound Care Center");
insert into location (name) values ("Seal Beach Health and Rehabilitation Centre");
insert into location (name) values ("NAF - EL Toro");
insert into location (name) values ("Kidney Care Intitute Inc (KCI)");
insert into location (name) values ("Long beach memorial & miller childrens hospital");
insert into location (name) values ("NAF - Valley View");
insert into location (name) values ("NAF-Brookhurst");
insert into location (name) values ("Marathon Medical Group");
insert into location (name) values ("Beverly Famility care centre");
insert into location (name) values ("Davita firestone");
insert into location (name) values ("Davita hawaiian Gardens");
insert into location (name) values ("Davita Karlton care");
insert into location (name) values ("Community Hospital of LB OP");
insert into location (name) values ("May Family Medical Clinic Compton");
insert into location (name) values ("South Gate Ambulatory surgical center");
insert into location (name) values ("Healthcare Partner Medical Group");
insert into location (name) values ("Kidney Dialysis care unit");
insert into location (name) values ("Chamber LA Office");
insert into location (name) values ("Del Amo Hosp Amo");


INSERT INTO `qc_point` (`id`, `created_on`, `modified_on`, `deleted`, `department_id`, `name`, `status`, `created_by`, `modified_by`, `parent_id`, `description`) VALUES
	(1, '2014-05-14 11:02:25', '2014-05-14 12:32:37', 0, 2, 'Level 1 Error: Directly or Indirectly Affecting Doctors and Patients', 1, 1, 1, NULL, NULL),
	(2, '2014-05-14 11:03:22', '2014-05-14 12:32:37', 0, 2, 'Batch NOT Balance / Incorrect Amount Posted / Pymt Not Posted', 1, 1, 1, 1, NULL),
	(3, '2014-05-14 11:04:01', '2014-05-14 12:32:37', 0, 2, 'Wrong Office No', 1, 1, 1, 1, NULL),
	(4, '2014-05-14 11:04:37', '2014-05-14 12:32:37', 0, 2, 'Wrong Doctor No', 1, 1, 1, 1, NULL),
	(5, '2014-05-14 11:04:59', '2014-05-14 12:32:37', 0, 2, 'Wrong DOS', 1, 1, 1, 1, NULL),
	(6, '2014-05-14 11:05:25', '2014-05-14 12:32:37', 0, 2, 'Posted to Wrong Procedure', 1, 1, 1, 1, NULL),
	(7, '2014-05-14 11:05:48', '2014-05-14 12:32:37', 0, 2, 'Incorrect Assignment (pt, guarantor or 2nd Ins)', 1, 1, 1, 1, NULL),
	(8, '2014-05-14 11:06:02', '2014-05-14 12:32:37', 0, 2, 'Wrong Patient Account No', 1, 1, 1, 1, NULL),
	(9, '2014-05-14 11:06:15', '2014-05-14 12:32:37', 0, 2, 'Low Reimbursement', 1, 1, 1, 1, NULL),
	(10, '2014-05-14 11:06:32', '2014-05-14 12:32:37', 0, 2, 'No Adjustment taken/Incorrect Amt Adjustd / Profile Not Checked', 1, 1, 1, 1, NULL),
	(11, '2014-05-14 11:06:58', NULL, 0, 2, 'Level 2 Error: Failure to follow set Posting Procedure', 1, 1, NULL, NULL, NULL),
	(12, '2014-05-14 11:07:19', NULL, 0, 2, 'Wrong Batch Name/Check # on PCN/CT/MM', 1, 1, NULL, 11, NULL),
	(13, '2014-05-14 11:07:35', NULL, 0, 2, 'Denial not added to comments / AR comment Not in Daily Rep', 1, 1, NULL, 11, NULL),
	(14, '2014-05-14 11:07:48', NULL, 0, 2, 'Incorrect Adj Code', 1, 1, NULL, 11, NULL),
	(15, '2014-05-14 11:08:04', NULL, 0, 2, 'Over posting to line', 1, 1, NULL, 11, NULL),
	(16, '2014-05-14 11:08:18', NULL, 0, 2, 'Incorrect Payment Source', 1, 1, NULL, 11, NULL),
	(17, '2014-05-14 11:08:29', NULL, 0, 2, 'Incorrect Ref Date', 1, 1, NULL, 11, NULL),
	(18, '2014-05-14 11:08:45', NULL, 0, 2, 'Incorrect check# (pt/ins)', 1, 1, NULL, 11, NULL),
	(19, '2014-05-14 11:08:58', NULL, 0, 2, 'Incorrect Payment Type (check, cash, etc.)', 1, 1, NULL, 11, NULL),
	(20, '2014-05-14 11:09:20', NULL, 0, 2, 'OFFSET NT WORK ALL THE INFO GIVEN', 1, 1, NULL, 11, NULL),
	(21, '2014-05-14 11:09:33', NULL, 0, 2, 'Denial Code not added', 1, 1, NULL, 11, NULL),
	(22, '2014-05-14 11:09:44', NULL, 0, 2, 'Comment not added', 1, 1, NULL, 11, NULL),
	(23, '2014-05-14 11:09:58', NULL, 0, 2, 'Adj reversed in error/Balancing Error', 1, 1, NULL, 11, NULL),
	(24, '2014-05-14 11:10:27', '2014-05-14 11:16:03', 0, 1, 'Demographics-1 (If Applicable)', 1, 1, 1, NULL, NULL),
	(25, '2014-05-14 11:10:46', NULL, 0, 1, 'Pt Name Missing / Incorrect', 1, 1, NULL, 24, NULL),
	(26, '2014-05-14 11:11:04', NULL, 0, 1, 'Address Incorrect / Missing', 1, 1, NULL, 24, NULL),
	(27, '2014-05-14 11:11:18', NULL, 0, 1, 'DOB Missing / Incorrect', 1, 1, NULL, 24, NULL),
	(28, '2014-05-14 11:11:33', NULL, 0, 1, 'Gender correct / Missing', 1, 1, NULL, 24, NULL),
	(29, '2014-05-14 11:11:50', NULL, 0, 1, 'SSN Entered / Missing', 1, 1, NULL, 24, NULL),
	(30, '2014-05-14 11:12:37', NULL, 0, 1, 'Notes Field Updated', 1, 1, NULL, 24, NULL),
	(31, '2014-05-14 11:12:51', NULL, 0, 1, '1st Ins Incorrect / Missing', 1, 1, NULL, 24, NULL),
	(32, '2014-05-14 11:13:07', NULL, 0, 1, '2nd Ins  Incorrect / Missing', 1, 1, NULL, 24, NULL),
	(33, '2014-05-14 11:13:25', NULL, 0, 1, 'Eligibility Verified', 1, 1, NULL, 24, NULL),
	(34, '2014-05-14 11:13:39', NULL, 0, 1, 'Checked "Verified no Insurance"', 1, 1, NULL, 24, NULL),
	(35, '2014-05-14 11:16:34', NULL, 0, 1, 'Demographics-2 (If Applicable)', 1, 1, NULL, NULL, NULL),
	(36, '2014-05-14 11:16:53', NULL, 0, 1, 'Location Incorrect', 1, 1, NULL, 35, NULL),
	(37, '2014-05-14 11:17:16', NULL, 0, 1, 'POS Incorrect', 1, 1, NULL, 35, NULL),
	(38, '2014-05-14 11:17:32', NULL, 0, 1, 'Ref Phys Incorrect Missing', 1, 1, NULL, 35, NULL),
	(39, '2014-05-14 11:17:48', NULL, 0, 1, 'Date of Admit Incorrect / Missing', 1, 1, NULL, 35, NULL),
	(40, '2014-05-14 11:18:01', NULL, 0, 1, 'Authorization Incorrect / Missing', 1, 1, NULL, 35, NULL),
	(41, '2014-05-14 11:18:16', NULL, 0, 1, 'Local Use Incorrect / Missing', 1, 1, NULL, 35, NULL),
	(42, '2014-05-14 11:21:47', NULL, 0, 1, 'Charge Entry (Coder)', 1, 1, NULL, NULL, NULL),
	(43, '2014-05-14 11:22:04', '2014-05-14 11:22:24', 0, 1, 'CPT Code Incorrect / Missing', 1, 1, 1, 42, NULL),
	(44, '2014-05-14 11:22:40', NULL, 0, 1, 'Modifier Missing / Incorrect', 1, 1, NULL, 42, NULL),
	(45, '2014-05-14 11:22:54', NULL, 0, 1, 'DX Incorrect / Pointer', 1, 1, NULL, 42, NULL),
	(46, '2014-05-14 11:23:13', NULL, 0, 1, 'Copay Not Applied', 1, 1, NULL, 42, NULL),
	(47, '2014-05-14 11:23:29', NULL, 0, 1, 'Superbill Not Entered', 1, 1, NULL, 42, NULL),
	(48, '2014-05-14 11:24:33', NULL, 0, 3, 'Demographic information: Failed to deactivate the incorrect plan.', 1, 1, NULL, NULL, NULL),
	(49, '2014-05-14 11:25:00', NULL, 0, 3, 'Added dx without verifying fee ticket or failed to check superbill to verify dx', 1, 1, NULL, NULL, NULL),
	(50, '2014-05-14 11:25:22', NULL, 0, 3, 'Incorrect modifier used/ or failed to add modifier', 1, 1, NULL, NULL, NULL),
	(51, '2014-05-14 11:25:42', NULL, 0, 3, 'Failed to transfer charges to correct Payer', 1, 1, NULL, NULL, NULL),
	(52, '2014-05-14 11:26:00', NULL, 0, 3, 'Second claim rebilled electronically (E Instd l)', 1, 1, NULL, NULL, NULL),
	(53, '2014-05-14 11:26:20', NULL, 0, 3, 'Failed to check E-bridge scanning system', 1, 1, NULL, NULL, NULL),
	(54, '2014-05-14 11:26:49', NULL, 0, 3, 'Appeal Done Incorrectly', 1, 1, NULL, NULL, NULL),
	(55, '2014-05-14 11:27:18', NULL, 0, 3, 'Notes in the CT system are not accurate (Example: did not send e-mail but notes stay they did. Notes say that they called the hospital or the office by they did not)', 1, 1, NULL, NULL, NULL),
	(56, '2014-05-14 11:27:41', NULL, 0, 3, 'Duplicate work', 1, 1, NULL, NULL, NULL),
	(57, '2014-05-14 11:28:03', NULL, 0, 3, 'Worked invalid acct (example: Worked on a claim under the minimum balance, worked on an EKG worked acct too soon)', 1, 1, NULL, NULL, NULL),
	(58, '2014-05-14 11:28:19', NULL, 0, 3, 'Sending request to the office instead of adding to coding log or vice versa', 1, 1, NULL, NULL, NULL),
	(59, '2014-05-14 11:28:38', NULL, 0, 3, 'Failed to contract the hospital or facility to get inf.', 1, 1, NULL, NULL, NULL),
	(60, '2014-05-14 11:28:53', NULL, 0, 3, 'Log & Note dates not matching', 1, 1, NULL, NULL, NULL),
	(61, '2014-05-14 11:29:18', NULL, 0, 3, 'Failed to update log (2nd req, Coding, Payment and adj log)', 1, 1, NULL, NULL, NULL),
	(62, '2014-05-14 11:29:32', NULL, 0, 3, 'Failed to work complete account', 1, 1, NULL, NULL, NULL),
	(63, '2014-05-14 11:29:42', NULL, 0, 3, 'Untimely follow up (PPO claims must me followed up within 61 days from the DOS and 30 days thereafter', 1, 1, NULL, NULL, NULL),
	(64, '2014-05-14 11:29:54', NULL, 0, 3, 'No Productivity Count', 1, 1, NULL, NULL, NULL);
