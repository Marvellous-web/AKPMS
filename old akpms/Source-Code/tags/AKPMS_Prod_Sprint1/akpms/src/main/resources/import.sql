-- You can use this file to load seed data into the database using SQL statements

INSERT INTO permission (id,name) values ( 'P-1','Trainee Evaluation by Document manager');
INSERT INTO permission (id,name) values ('P-2','QA Worksheet');
--INSERT INTO permission (id,name) values ('P-3','Manager to Handle Esclation');
--INSERT INTO permission (id,name) values ('P-4','Approve/Reject/Esclate');
INSERT INTO permission (id,name) values ('P-5','Offset Manager');

INSERT INTO permission (id,name) values ('P-6','Process reject log after resolution');
INSERT INTO permission (id,name) values ('P-7','Process on-hold');
INSERT INTO permission (id,name) values ('P-8','Create/Revise Batch');
INSERT INTO permission (id,name) values ('P-9','Re-update payment batch tickets after productivity');
INSERT INTO permission (id,name) values ('P-10','Re-update charge batch tickets after productivity');

INSERT INTO permission (id,name) values ('P-11','Sub-Admin for charge batching system');
INSERT INTO permission (id,name) values ('P-12','Sub-Admin for payment batching system');
INSERT INTO permission (id,name) values ('P-13','Sub-Admin for cash log system');

INSERT INTO role (name) values ('Admin');
INSERT INTO role (name) values ('Document Manager');
INSERT INTO role (name) values ('Standard User');
INSERT INTO role (name) values ('Trainee');

INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'admin','admin','login@123', 'adminar@yopmail.com', '123456789',1, 1, now(), 1);
INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'document','manager','login@123', 'dmar@yopmail.com', '123456789',2, 1, now(), 1);
INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'standard','user','login@123', 'stdar@yopmail.com', '123456789',3, 1, now(), 1);
INSERT INTO user (first_name, last_name, password, email, contact, role_id, status, created_on, created_by) values ( 'trainee','user','login@123', 'traineear@yopmail.com', '123456789',4, 1, now(), 1);

INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES	('Hi , <br/><br/>The process manual PROCESS_MANUAL has been ADDED_MODIFIED<br/><br/><br/><br/>Regards, <br/>Argus Team <br/>', 'Process manual alerts', 1, 1, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES ('Hi , <br/><br/> The Trainee TRAINEE_NAME has been evaluated by EVALUATOR_NAME as: <br><br>ratings: TRAINEE_RATING/5 <br> comment: TRAINEE_COMMENT<br/><br/><br/><br/>Regards, <br/>Argus Team <br/>', 'Notification on trainee evaluation', 1, 1, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES ('Hi USER_FIRST_NAME, <br/><br/>Your account has been created on SITE_URL <br/><br/>UserName: USER_EMAIL <br/><br/>Password: USER_PASSWORD <br/><br/>Login by going to SITE_URL link. <br/><br/><br/><br/>Regards, <br/>Argus Team <br/>','Argus registration email', 1, 0, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES ('Hi USER_FIRST_NAME, <br/><br/> Your Password has been changed. Your new password is NEW_PASSWORD <br/><br/> Regards, <br />Argus Team','Argus Password Changed', 1, 0, now(), 1);
INSERT INTO `email_template` (`content`, `name`, `status`, `subscription_email`, created_on, created_by) VALUES	('Hi USER_FIRST_NAME, <br/><br/> As requested, please find your password below.<br/><br/> The password is USER_PASSWORD <br/><br/> Regards, <br />Argus Team', 'Argus password recovery', 1, 0, now(), 1);

INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Coding and Charge Posting',  'Coding/Charge Posting', null, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Payment',  'Payment', null, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('AR',  'AR', null, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Accounting',  'Accounting', null, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('Credentialing',  'Credentialing', null, now(), 1);

INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('c1',  'c1-coding', 1, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('c2',  'c2-coding', 1, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('c3',  'c3-coding', 1, now(), 1);

INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('p1',  'p1-payment', 2, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('p2',  'p2-payment', 2, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('p3',  'p3-payment', 2, now(), 1);

INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('HMO',  'HMO-AR', 3, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('PPO',  'PPO-AR', 3, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('MCL',  'MCL-AR', 3, now(), 1);
INSERT INTO department (name,  description, parent_id, created_on, created_by ) values ('MCR',  'MCR-AR', 3, now(), 1);

INSERT INTO admin_settings(procees_manaul_read_status_ratings,ids_argus_ratings,argus_ratings, created_on, created_by) values('20','40','40', now(), 1);

-- process manual dummy data [start]
INSERT INTO `process_manual` (`content`, `position`, `title`, `parent_id`, created_on, created_by) VALUES ( "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore",  1, 'Main Process Manual', NULL, now(), 1);
INSERT INTO `process_manual` (`content`,  `position`, `title`, `parent_id`, created_on, created_by) VALUES ( "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore", 2, 'Main Sub- Process Manual 1', 1, now(), 1);
INSERT INTO `process_manual` (`content`, `position`, `title`, `parent_id`, created_on, created_by) VALUES ("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore",  3, 'Main Sub- Process Manual 2', 1, now(), 1);

INSERT INTO `process_manual_position` (`section_id`, `parent_id`, `position`) VALUES (1, 1, 1);
INSERT INTO `process_manual_position` (`section_id`, `parent_id`, `position`) VALUES (2, 1, 2);
INSERT INTO `process_manual_position` (`section_id`, `parent_id`, `position`) VALUES (3, 1, 3);
-- process manual dummy data [end]

INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('Admin Income', 'Admin Income', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('Ancillary Income', 'Ancillary Income', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('CAP', 'CAP', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('EFT/FFS', 'EFT/FFS', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('FFS', 'FFS', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('IPA FFS', 'IPA FFS', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('NSF', 'NSF', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('Offset', 'Offset', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('OTC', 'OTC', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('Refund-AP', 'Refund-AP', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('System Refund', 'System Refund', 1, 'paytypes', 1);
INSERT INTO `payment_type` (`description`, `name`, `status`, `type`, `created_by`) VALUES ('N/A', 'N/A', 1, 'paytypes', 1);

INSERT INTO `money_source` (`name`) VALUES ('EFT');
INSERT INTO `money_source` (`name`) VALUES ('LockBox');
INSERT INTO `money_source` (`name`) VALUES ('Mail');
--INSERT INTO `money_source` (`name`) VALUES ('Vault Check');
--INSERT INTO `money_source` (`name`) VALUES ('Credit Card');
--INSERT INTO `money_source` (`name`) VALUES ('Vault Cash');
INSERT INTO `money_source` (`name`) VALUES ('Tele Card');

INSERT INTO `admin_income` (`name`) VALUES ('Admin Income');
INSERT INTO `admin_income` (`name`) VALUES ('IPA Bonus');
INSERT INTO `admin_income` (`name`) VALUES ('MCR Incentive');
INSERT INTO `admin_income` (`name`) VALUES ('Carvenue Admin Income');
INSERT INTO `admin_income` (`name`) VALUES ('Directorship');
INSERT INTO `admin_income` (`name`) VALUES ('Agency');

-- doctor data [start]
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES 	(1, NULL, NULL, 0, '0000', 'ProHealth', 1, 0, 0, 0, 0, NULL, NULL, NULL);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(2, NULL, NULL, 0, '1500', 'PMN', 1, 0, 0, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(3, NULL, NULL, 0, '3000', 'ABC', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(4, NULL, NULL, 0, '3050', 'AIM', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(5, NULL, NULL, 0, '3120', 'Bryant', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(6, NULL, NULL, 0, '3130', 'DeJong', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(7, NULL, NULL, 0, '3140', 'Koseff', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(8, NULL, NULL, 0, '3140', 'Koseff Diet Progarm', 1, 0.02, 0.02, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(9, NULL, NULL, 0, '3160', 'Dela Rosa', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(10, NULL, NULL, 0, '3180', 'Tyson', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(11, NULL, NULL, 0, '3190', 'Berkson', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(12, NULL, NULL, 0, '3200', 'CFMG', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(13, NULL, NULL, 0, '3210', 'CFMG-Finkelstein', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(14, NULL, NULL, 0, '3230', 'CFMG-Wong', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(15, NULL, NULL, 0, '3240', 'Chau', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(16, NULL, NULL, 0, '3260', 'Comer', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(17, NULL, NULL, 0, '3270', 'Davis B', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(18, NULL, NULL, 0, '3280', 'Davis K', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(19, NULL, NULL, 0, '3300', 'Govind', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(20, NULL, NULL, 0, '3320', 'Krishnamurthy', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(21, NULL, NULL, 0, '3330', 'Lake', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(22, NULL, NULL, 0, '3340', 'Lugliani', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(23, NULL, NULL, 0, '3350', 'Mackovic - Lwd', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(24, NULL, NULL, 0, '3360', 'McCloy', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(25, NULL, NULL, 0, '3390', 'Mehta', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(26, NULL, NULL, 0, '3400', 'Mortara', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(27, NULL, NULL, 0, '3410', 'Hirano', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(28, NULL, NULL, 0, '3450', 'SMMC- Med. Ed.', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(29, NULL, NULL, 0, '3460', 'Singer', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(30, NULL, NULL, 0, '3470', 'Wanyik', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(31, NULL, NULL, 0, '3480', 'Ferrera', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(32, NULL, NULL, 0, '3485', 'Vescovic', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(33, NULL, NULL, 0, '3490', 'Wyche', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(34, NULL, NULL, 0, '3550', 'Ibarra', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(35, NULL, NULL, 0, '3560', 'Dolan', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(36, NULL, NULL, 0, '3600', 'Norcross', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(37, NULL, NULL, 0, '3620', 'Harvey', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(38, NULL, NULL, 0, '3630', 'Gorlick', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(39, NULL, NULL, 0, '3640', 'Nash', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(40, NULL, NULL, 0, '3660', 'Dix', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(41, NULL, NULL, 0, '3670', 'Clark', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(42, NULL, NULL, 0, '3680', 'AIM- Del Amo', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(43, NULL, NULL, 0, '3720', 'Richey', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(44, NULL, NULL, 0, '3730', 'CFMG-Mleynek', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(45, NULL, NULL, 0, '3730', 'CFMG-Hythiam', 1, 0.05, 0.01, 0.02, 0.02, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(46, NULL, NULL, 0, '3800', 'Stikelmaier', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(47, NULL, NULL, 0, '3800', 'Lakewood PC', 1, 0.02, 0.01, 0.01, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(48, NULL, NULL, 0, '3810', 'Shah,M.', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(49, NULL, NULL, 0, '3820', 'Saeed', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(50, NULL, NULL, 0, '3830', 'Kern', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(51, NULL, NULL, 0, '3860', 'Ginzburg', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(52, NULL, NULL, 0, '3870', 'Garcia', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(53, NULL, NULL, 0, '3910', 'Gordon', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(54, NULL, NULL, 0, '3920', 'Chaudhary,V.', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(55, NULL, NULL, 0, '3930', 'Krishnaswamy', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(56, NULL, NULL, 0, '4010', 'Nanda', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(57, NULL, NULL, 0, '4020', 'Elledge', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(58, NULL, NULL, 0, '4040', 'Mac', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(59, NULL, NULL, 0, '4070', 'Clark-Laser MD', 1, 0.05, 0.01, 0.02, 0.02, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(60, NULL, NULL, 0, '4080', 'Choi', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(61, NULL, NULL, 0, '4200', 'Bell - PH', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(62, NULL, NULL, 0, '4210', 'Samawi', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(63, NULL, NULL, 0, '4600', 'Naples Medical Group', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(64, NULL, NULL, 0, '4700', 'Castro-Laser', 1, 0.05, 0.01, 0.02, 0.02, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(65, NULL, NULL, 0, '4710', 'Kuhn', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(66, NULL, NULL, 0, '4737', 'Schneider', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(67, NULL, NULL, 0, '4750', 'Lantz', 1, 0.01, 0.01, 0, 0, NULL, NULL, NULL);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(68, NULL, NULL, 0, '4760', 'Buckhalter', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(69, NULL, NULL, 0, '4800', 'Abassi', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(70, NULL, NULL, 0, '4800', 'CVA', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(71, NULL, NULL, 0, '4830', 'Jengo', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(72, NULL, NULL, 0, '4850', 'Armandi', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(73, NULL, NULL, 0, '4870', 'Thomas', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(74, NULL, NULL, 0, '4890', 'Gomez', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(75, NULL, NULL, 0, '4900', 'Maciog', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(76, NULL, NULL, 0, '4910', 'Ishimaru', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(77, NULL, NULL, 0, '4920', 'Chaudhary,A.', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(78, NULL, NULL, 0, '4970', 'Eppele', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(79, NULL, NULL, 0, '4980', 'Pole', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(80, NULL, NULL, 0, '4995', 'Liff', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(81, NULL, NULL, 0, '5050', 'Alcouloumre', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(82, NULL, NULL, 0, '5050', 'Montoya', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(83, NULL, NULL, 0, '5090', 'Devente', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(84, NULL, NULL, 0, '5100', 'SMMC- Seaview', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(85, NULL, NULL, 0, '5540', 'Issa', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(86, NULL, NULL, 0, '6580', 'So', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
INSERT INTO `doctor` (`id`, `created_on`, `modified_on`, `deleted`, `doctorCode`, `name`, `status`, `percentage`, `payments`, `accounting`, `operations`, `created_by`, `modified_by`, `parent_id`) VALUES	(87, NULL, NULL, 0, '9998', 'SFMC- Cash', 1, 0.01, 0.01, 0, 0, NULL, NULL, 1);
-- doctor data [end]

-- revenue type data [start]
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES 	(1, NULL, NULL, 0, NULL, 'Patient Collection', 1, '0000', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(2, NULL, NULL, 0, NULL, 'PMN- Risk Mgmt', 1, '1510', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(3, NULL, NULL, 0, NULL, 'PMN- CAQH Data Entry', 1, '1510', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(4, NULL, NULL, 0, NULL, 'PMN- Misc Credentialing Work', 1, '1510', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(5, NULL, NULL, 0, NULL, 'PMN- Initial Membership', 1, '1510', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(6, NULL, NULL, 0, NULL, 'PMN- Biocards', 1, '1510', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(7, NULL, NULL, 0, NULL, 'PMN- Annual Fee', 1, '1510', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(8, NULL, NULL, 0, NULL, 'Medicare Incentive', 1, '3110', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(9, NULL, NULL, 0, NULL, 'Healthcare Partners Bonus', 1, '3330', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(10, NULL, NULL, 0, NULL, 'CareMore Admin Income', 1, '3355', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(11, NULL, NULL, 0, NULL, 'IPA Bonus', 1, '3380', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(12, NULL, NULL, 0, NULL, 'Derm/Lsr/RX', 1, '3400', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(13, NULL, NULL, 0, NULL, 'Admin Income', 1, '3410', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(14, NULL, NULL, 0, NULL, 'Directorship', 1, '3410', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(15, NULL, NULL, 0, NULL, 'Hospitalist', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(16, NULL, NULL, 0, NULL, 'Other', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(17, NULL, NULL, 0, NULL, 'Agency', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(18, NULL, NULL, 0, NULL, 'Diet Program', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(19, NULL, NULL, 0, NULL, 'Nutritionals', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(20, NULL, NULL, 0, NULL, 'FAA', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(21, NULL, NULL, 0, NULL, 'Hythiam', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(22, NULL, NULL, 0, NULL, 'BioLife', 1, '3420', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(23, NULL, NULL, 0, NULL, 'Old AR', 1, '3490', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(24, NULL, NULL, 0, NULL, 'RX', 1, '3800', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(25, NULL, NULL, 0, NULL, 'Derm', 1, '4110', NULL, NULL);
INSERT INTO `revenue_type` (`id`, `created_on`, `modified_on`, `deleted`, `description`, `name`, `status`, `code`, `created_by`, `modified_by`) VALUES	(26, NULL, NULL, 0, NULL, 'Laser', 1, '4700', NULL, NULL);
-- revenue type data [end]

-- insurance dummy data [start]
INSERT INTO `insurance` (`description`, `name`, `created_by`) VALUES ('insurance 1', 'insurance 1', 1);
INSERT INTO `insurance` (`description`, `name`, `created_by`) VALUES ('insurance 2', 'insurance 2', 1);
INSERT INTO `insurance` (`description`, `name`, `created_by`) VALUES ('insurance 3', 'insurance 3', 1);
INSERT INTO `insurance` (`description`, `name`, `created_by`) VALUES ('insurance 4', 'insurance 4', 1);
INSERT INTO `insurance` (`description`, `name`, `created_by`) VALUES ('insurance 5', 'insurance 5', 1);

-- insurance dummy data [end]

--Task Name dummy data [start]
INSERT INTO `task_name` (`name`) VALUES ('task1');
INSERT INTO `task_name` (`name`) VALUES ('task2');
INSERT INTO `task_name` (`name`) VALUES ('task3');
INSERT INTO `task_name` (`name`) VALUES ('task4');
INSERT INTO `task_name` (`name`) VALUES ('task5');
--Task Name dummy data [end]
