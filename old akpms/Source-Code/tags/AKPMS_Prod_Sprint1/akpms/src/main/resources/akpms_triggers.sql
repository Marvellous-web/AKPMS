-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.5.23 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2012-11-09 11:42:44
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping structure for trigger akpms.process_manual_audit_after_insert
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `process_manual_audit_after_insert` AFTER INSERT ON `process_manual` FOR EACH ROW BEGIN
	INSERT INTO `process_manual_audit` (
		process_manual_audit.process_manual_id,
		process_manual_audit.content,
		process_manual_audit.is_deleted,
		process_manual_audit.modification_summary,
		process_manual_audit.modified_on,
		process_manual_audit.modified_by,
		process_manual_audit.notification,
		process_manual_audit.status,
		process_manual_audit.title,
		process_manual_audit.parent_id,
		process_manual_audit.action
	) values (
		NEW.id,
		TRIM(NEW.content),
		NEW.is_deleted,
		TRIM(NEW.modification_summary),
		NEW.modified_on,
		NEW.modified_by,
		NEW.notification,
		NEW.status,
		NEW.title,
		new.parent_id,
		'insert'
	);

	insert into notifications (
			notifications.content,
			notifications.created_on,
			notifications.type
	) values (
		CONCAT("New Process manual has added. Title: ",SUBSTRING(TRIM(new.title),1,50),'... Content: ',SUBSTRING(TRIM(NEW.content), 1, 200),'...'),
		NOW(),
		'Process Manual'
	);

		-- update the position of other section when a new sub section is added

				update process_manual_position set process_manual_position.position = (process_manual_position.position+1) where process_manual_position.parent_id = new.parent_id and process_manual_position.position >= new.position;

				insert into process_manual_position(parent_id,section_id,position) values(new.parent_id,new.id,new.position);

END//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;


-- Dumping structure for trigger akpms.process_manual_audit_before_update
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `process_manual_audit_before_update` BEFORE UPDATE ON `process_manual` FOR EACH ROW BEGIN

	if(old.parent_id > 0)
	then
		if(new.position <> old.position)
		then
		     update process_manual_position set process_manual_position.position = (process_manual_position.position+1) where process_manual_position.position >= new.position;
	end if;
			 update process_manual_position set process_manual_position.position = new.position where process_manual_position.section_id = new.id;
		end if;


	-- mantis bug id: 0016881 resolved
	IF(OLD.is_deleted = 1 AND NEW.is_deleted = 0)
	THEN
		SET NEW.is_deleted = OLD.is_deleted;
	END IF;

	-- if a section deleted then this should appear in modification summary for that section
	IF(NEW.is_deleted = 1)
	THEN
		if(old.parent_id > 0)
		then
			INSERT INTO `process_manual_audit` (
				process_manual_audit.process_manual_id,
				process_manual_audit.content,
				process_manual_audit.is_deleted,
				process_manual_audit.modification_summary,
				process_manual_audit.modified_on,
				process_manual_audit.modified_by,
				process_manual_audit.notification,
				process_manual_audit.status,
				process_manual_audit.title,
				process_manual_audit.parent_id,
				process_manual_audit.action
			) values (
				old.parent_id,
				"",
				"",
				CONCAT("Sub-Section '",old.title, "' has been deleted in this process manual." ),
				NEW.modified_on,
				NEW.modified_by,
				0,
				old.status,
				"",
				null,
				'delete sub-section'
			);
		end if;

		INSERT INTO `process_manual_audit` (
			process_manual_audit.process_manual_id,
			process_manual_audit.content,
			process_manual_audit.is_deleted,
			process_manual_audit.modification_summary,
			process_manual_audit.modified_on,
			process_manual_audit.modified_by,
			process_manual_audit.notification,
			process_manual_audit.status,
			process_manual_audit.title,
			process_manual_audit.parent_id,
			process_manual_audit.action
		) values (
			old.id,
			TRIM(old.content),
			NEW.is_deleted,
			TRIM(NEW.modification_summary),
			NEW.modified_on,
			NEW.modified_by,
			old.notification,
			old.status,
			old.title,
			old.parent_id,
			'delete'
		);

		insert into notifications (
			notifications.content,
			notifications.created_on,
			notifications.type
		) values (
			CONCAT("Process manual has deleted. Title: ",SUBSTRING(TRIM(old.title),1,50),'... Content: ',SUBSTRING(TRIM(old.content), 1, 60),'...'),
			NOW(),
			'Process Manual'
		);
	END IF;

if(old.content <> new.content || old.title <> new.title || new.modification_summary IS NOT NULL)
THEN
	INSERT INTO `process_manual_audit` (
		process_manual_audit.process_manual_id,
		process_manual_audit.content,
		process_manual_audit.is_deleted,
		process_manual_audit.modification_summary,
		process_manual_audit.modified_on,
		process_manual_audit.modified_by,
		process_manual_audit.notification,
		process_manual_audit.status,
		process_manual_audit.title,
		process_manual_audit.parent_id,
		process_manual_audit.action
	) values (
		old.id,
		TRIM(old.content),
		old.is_deleted,
		TRIM(NEW.modification_summary),
		NEW.modified_on,
		NEW.modified_by,
		old.notification,
		old.status,
		old.title,
		old.parent_id,
		'update'
	);

	insert into notifications (
			notifications.content,
			notifications.created_on,
			notifications.type
	) values (
		CONCAT("Process manual has updated. Title: ",SUBSTRING(TRIM(old.title),1,50),'... Content: ',SUBSTRING(TRIM(NEW.modification_summary), 1, 60),'...'),
		NOW(),
		'Process Manual'
	);
END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;


-- Dumping structure for trigger akpms.trainee_evaluate_audit_after_insert
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `trainee_evaluate_audit_after_insert` AFTER INSERT ON `trainee_evaluate` FOR EACH ROW BEGIN

	set @evaluator = (select CONCAT(user.first_name, '  ', user.last_name) from user where user.id = new.modified_by);
	set @trainee = (select CONCAT(user.first_name, '  ', user.last_name) from user where user.id = new.trainee_id);

	insert into notifications (
		notifications.content,
		notifications.created_on,
		notifications.type
	)values (
		CONCAT(@trainee, ' has evaluated by ', @evaluator ),
		NOW(),
		'Trainee Evaluation'
	);
END//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;


-- Dumping structure for trigger akpms.trainee_evaluate_audit_before_update
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `trainee_evaluate_audit_before_update` BEFORE UPDATE ON `trainee_evaluate` FOR EACH ROW BEGIN

	set @evaluator = (select CONCAT(user.first_name, '  ', user.last_name) from user where user.id = old.modified_by);
	set @trainee = (select CONCAT(user.first_name, '  ', user.last_name) from user where user.id = old.trainee_id);

	insert into notifications (
		notifications.content,
		notifications.created_on,
		notifications.type
	)values (
		CONCAT(@trainee, ' has re-evaluated by ', @evaluator ),
		NOW(),
		'Trainee Evaluation'
	);
END//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
