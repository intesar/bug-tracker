-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51a-3ubuntu5.4


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema pm
--

CREATE DATABASE IF NOT EXISTS pm;
USE pm;

--
-- Definition of table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `username` varchar(45) NOT NULL,
  `authority` varchar(45) NOT NULL,
  `id` int(10) unsigned NOT NULL auto_increment,
  `create_user` int(10) unsigned default NULL,
  `create_date` datetime default NULL,
  `vid` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_2` (`username`,`authority`),
  KEY `Index_3` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `authorities`
--

/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` (`username`,`authority`,`id`,`create_user`,`create_date`,`vid`) VALUES 
 ('fatima.shanoor@gmail.com','ROLE_USER',6,NULL,NULL,1),
 ('fatima.shanoor@gmail.com','ROLE_ADMIN',30,NULL,NULL,1),
 ('intesar.mohammed@bizintelapps.com','ROLE_ADMIN',31,NULL,NULL,1),
 ('intesar.mohammed@bizintelapps.com','ROLE_USER',32,NULL,NULL,1),
 ('fatimashanoor1@gmail.com','ROLE_USER',33,NULL,NULL,1),
 ('salman@urooj.net','ROLE_ADMIN',34,NULL,NULL,1),
 ('salman@urooj.net','ROLE_USER',35,NULL,NULL,1),
 ('raadia@urooj.net','ROLE_ADMIN',36,NULL,NULL,1),
 ('raadia@urooj.net','ROLE_USER',37,NULL,NULL,1),
 ('anuj.s@urooj.net','ROLE_ADMIN',38,NULL,NULL,1),
 ('anuj.s@urooj.net','ROLE_USER',39,NULL,NULL,1),
 ('shiraaz@urooj.net','ROLE_USER',40,NULL,NULL,1),
 ('shiraaz@urooj.net','ROLE_ADMIN',41,NULL,NULL,1);
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;


--
-- Definition of table `organization`
--

DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `website_url` varchar(145) default NULL,
  `create_user` int(10) unsigned default NULL,
  `create_ip` varchar(45) default NULL,
  `create_date` datetime NOT NULL,
  `vid` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_2` (`name`),
  KEY `Index_3` (`website_url`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `organization`
--

/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` (`id`,`name`,`website_url`,`create_user`,`create_ip`,`create_date`,`vid`) VALUES 
 (8,'Grow-Fast Demo',NULL,NULL,NULL,'2008-12-11 19:44:35',1),
 (9,'bizintelapps',NULL,NULL,NULL,'2009-01-20 03:37:24',1),
 (10,'Urooj Corporation',NULL,NULL,NULL,'2009-01-20 04:36:21',1),
 (11,'urooj',NULL,NULL,NULL,'2009-01-20 05:06:33',1),
 (12,'Urooj Corp',NULL,NULL,NULL,'2009-01-20 05:18:55',1),
 (13,'Urooj LLC',NULL,NULL,NULL,'2009-01-20 20:07:44',1);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;


--
-- Definition of table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `description` varchar(1000) default NULL,
  `_status` varchar(45) NOT NULL default '',
  `create_user` varchar(45) NOT NULL,
  `create_date` datetime NOT NULL,
  `visibility` varchar(45) default NULL,
  `parent_project` int(10) unsigned default NULL,
  `organization` int(10) unsigned NOT NULL,
  `last_update_user` int(10) unsigned default NULL,
  `last_update_date` datetime default NULL,
  `estimated_hours` double default '0',
  `hourly_rate` double default '0',
  `estimated_cost` double default '0',
  `notification_emails` varchar(255) default NULL,
  `vid` int(10) unsigned default NULL,
  `enabled` int(10) unsigned NOT NULL default '1',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_3` (`name`,`organization`),
  KEY `FK_project_organization` (`organization`),
  KEY `Index_4` (`name`),
  CONSTRAINT `FK_project_1` FOREIGN KEY (`organization`) REFERENCES `organization` (`id`),
  CONSTRAINT `FK_project_organization` FOREIGN KEY (`organization`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project`
--

/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`,`name`,`description`,`_status`,`create_user`,`create_date`,`visibility`,`parent_project`,`organization`,`last_update_user`,`last_update_date`,`estimated_hours`,`hourly_rate`,`estimated_cost`,`notification_emails`,`vid`,`enabled`) VALUES 
 (14,'test-project1',NULL,'In Progress','15','2008-12-13 19:34:14',NULL,NULL,8,15,'2008-12-13 19:34:14',0,0,0,NULL,1,1),
 (18,'hello-brother',NULL,'In Progress','15','2008-12-25 19:07:38',NULL,NULL,8,15,'2008-12-25 19:07:38',0,0,0,'0.0',1,1),
 (19,'Grow-Fast',NULL,'In Progress','8','2009-01-20 03:44:03',NULL,NULL,9,8,'2009-01-20 03:44:03',0,0,0,'0.0',1,1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


--
-- Definition of table `project_report`
--

DROP TABLE IF EXISTS `project_report`;
CREATE TABLE `project_report` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `project` int(10) unsigned default NULL,
  `task_created` int(10) unsigned NOT NULL default '0',
  `task_finished` int(10) unsigned default '0',
  `estimated_time` double unsigned default '0',
  `time_spend` double unsigned default '0',
  `month` int(10) unsigned NOT NULL,
  `year` int(10) unsigned NOT NULL,
  `organization` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_2` (`project`,`month`,`year`),
  KEY `Index_3` (`task_finished`),
  KEY `Index_4` (`task_created`),
  KEY `Index_5` (`time_spend`),
  KEY `Index_6` (`estimated_time`),
  KEY `FK_project_report_2` (`organization`),
  CONSTRAINT `FK_project_report_1` FOREIGN KEY (`project`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_project_report_2` FOREIGN KEY (`organization`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_report`
--

/*!40000 ALTER TABLE `project_report` DISABLE KEYS */;
INSERT INTO `project_report` (`id`,`project`,`task_created`,`task_finished`,`estimated_time`,`time_spend`,`month`,`year`,`organization`) VALUES 
 (10,14,40,39,40,60,10,2008,8),
 (11,14,70,70,210,230,11,2008,8),
 (12,NULL,47,24,53,131,0,2009,8),
 (13,14,2,0,0,0,0,2009,NULL),
 (17,18,8,5,0,59,0,2009,NULL),
 (20,19,35,0,2,0,0,2009,NULL),
 (22,NULL,3,2,0,0,0,2009,9);
/*!40000 ALTER TABLE `project_report` ENABLE KEYS */;


--
-- Definition of table `project_users`
--

DROP TABLE IF EXISTS `project_users`;
CREATE TABLE `project_users` (
  `id` int(11) NOT NULL auto_increment,
  `is_enabled` tinyint(1) default '0',
  `expiration_date` datetime default NULL,
  `is_manager` tinyint(1) unsigned NOT NULL default '0',
  `create_date` datetime default NULL,
  `project` int(11) NOT NULL,
  `create_user` int(11) default NULL,
  `users` int(11) NOT NULL,
  `vid` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`),
  KEY `Index_2` (`project`,`users`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_users`
--

/*!40000 ALTER TABLE `project_users` DISABLE KEYS */;
INSERT INTO `project_users` (`id`,`is_enabled`,`expiration_date`,`is_manager`,`create_date`,`project`,`create_user`,`users`,`vid`) VALUES 
 (1,1,NULL,1,'2009-01-17 16:06:21',18,7,7,1),
 (2,1,NULL,0,'2009-01-18 05:31:35',18,7,5,1),
 (3,1,NULL,0,'2009-01-18 05:31:54',14,7,5,1),
 (4,1,NULL,1,'2009-01-20 03:44:24',19,8,8,1);
/*!40000 ALTER TABLE `project_users` ENABLE KEYS */;


--
-- Definition of table `task`
--

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL auto_increment,
  `parent_task` int(11) default NULL,
  `sequence_number` double default NULL,
  `title` varchar(105) NOT NULL default 'na',
  `context` varchar(45) default NULL,
  `deadline` datetime default NULL,
  `completed_date` datetime default NULL,
  `visibility` varchar(45) default NULL,
  `last_status_changed_date` datetime default NULL,
  `_status` varchar(45) NOT NULL default 'New',
  `create_date` datetime NOT NULL,
  `percentage_completed` int(11) default NULL,
  `description` varchar(255) default NULL,
  `priority` varchar(45) NOT NULL default 'Medium',
  `project` int(11) default NULL,
  `owner` int(11) NOT NULL,
  `assigned_to` int(11) default NULL,
  `estimated_hours` double default NULL,
  `spend_hours` double default '1',
  `notification_emails` varchar(255) default NULL,
  `assigned_date` date default NULL,
  `vid` int(10) unsigned default NULL,
  `assigned_by` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`),
  KEY `Index_2` (`title`),
  KEY `Index_3` (`deadline`),
  KEY `Index_4` (`completed_date`),
  KEY `Index_5` (`_status`),
  KEY `Index_6` (`create_date`),
  KEY `Index_7` (`priority`),
  KEY `Index_8` (`notification_emails`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task`
--

/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`,`parent_task`,`sequence_number`,`title`,`context`,`deadline`,`completed_date`,`visibility`,`last_status_changed_date`,`_status`,`create_date`,`percentage_completed`,`description`,`priority`,`project`,`owner`,`assigned_to`,`estimated_hours`,`spend_hours`,`notification_emails`,`assigned_date`,`vid`,`assigned_by`) VALUES 
 (29,NULL,NULL,'task1',NULL,NULL,NULL,NULL,'2009-01-04 16:22:44','Completed','2009-01-04 13:04:39',0,'','Medium',NULL,7,NULL,2,2,'',NULL,3,NULL),
 (30,NULL,NULL,'task2',NULL,NULL,NULL,NULL,'2009-01-04 16:19:51','Completed','2009-01-04 13:05:03',0,'','Medium',NULL,7,NULL,2,2,'',NULL,2,NULL),
 (31,NULL,NULL,'task 3',NULL,NULL,NULL,NULL,'2009-01-04 16:20:45','Completed','2009-01-04 13:05:42',0,'','Medium',NULL,7,NULL,2,0,'',NULL,2,NULL),
 (32,NULL,NULL,'task 4',NULL,'2009-01-16 22:43:54','2009-01-16 22:43:54',NULL,'2009-01-17 11:26:44','Completed','2009-01-04 13:06:04',0,'','Medium',18,7,7,2,2,'','2009-01-16',6,7),
 (33,NULL,NULL,'task 5',NULL,NULL,NULL,NULL,'2009-01-04 13:06:40','New','2009-01-04 13:06:40',0,'','High',NULL,7,5,2,0,'','2009-01-04',3,7),
 (34,NULL,NULL,'task 6',NULL,NULL,NULL,NULL,'2009-01-04 16:25:08','Completed','2009-01-04 13:07:25',0,'','Medium',NULL,7,7,1,0,'','2009-01-04',4,7),
 (35,NULL,NULL,'task 7',NULL,NULL,NULL,NULL,'2009-01-04 16:27:11','Completed','2009-01-04 13:09:34',0,'','Medium',NULL,7,7,4,10,'','2009-01-04',3,7),
 (36,NULL,NULL,'',NULL,NULL,NULL,NULL,'2009-01-04 13:13:17','Completed','2009-01-04 13:10:42',0,'','Medium',NULL,7,7,2,2,'','2009-01-04',3,7),
 (37,NULL,NULL,'task 8',NULL,NULL,NULL,NULL,'2009-01-04 13:11:49','Completed','2009-01-04 13:11:49',0,'','Medium',NULL,7,7,2,2,'','2009-01-04',1,7),
 (38,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,'2009-01-10 23:43:21',NULL,'2009-01-08 23:25:11','Completed','2009-01-04 13:40:28',0,'.......','High',NULL,7,7,1,50,'','2009-01-04',6,7),
 (39,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,'2009-01-10 23:42:07',NULL,'2009-01-04 18:57:01','Completed','2009-01-04 13:40:34',0,'','Medium',NULL,7,7,10,10,'','2009-01-04',5,7),
 (40,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,NULL,NULL,'2009-01-04 19:36:24','Completed','2009-01-04 13:40:39',0,'','Medium',NULL,7,7,10,100,'','2009-01-04',5,7),
 (41,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,'2009-01-17 11:33:25',NULL,'2009-01-08 23:26:07','Completed','2009-01-04 13:40:45',0,'','High',NULL,7,7,1,1,'','2009-01-17',6,7),
 (42,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,NULL,NULL,'2009-01-11 00:20:30','Completed','2009-01-04 13:40:52',0,'','Medium',NULL,7,7,0,0,'','2009-01-10',5,7),
 (43,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,NULL,NULL,'2009-01-11 00:21:32','Completed','2009-01-04 13:40:58',0,'','High',NULL,7,7,1,1,'','2009-01-10',5,7),
 (44,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,'2009-01-17 11:44:21',NULL,'2009-01-17 11:44:21','Completed','2009-01-04 13:41:02',0,'','High',NULL,7,7,1,1,'','2009-01-17',7,7),
 (49,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,NULL,NULL,'2009-01-11 00:24:00','Completed','2009-01-04 13:41:36',0,'','Medium',NULL,7,7,1,1,'','2009-01-10',3,7),
 (50,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,'2009-01-11 00:25:13',NULL,'2009-01-04 13:41:41','Completed','2009-01-04 13:41:41',0,'','High',NULL,7,7,0,0,'','2009-01-11',5,7),
 (51,NULL,NULL,'demo',NULL,NULL,'2009-01-16 22:21:16',NULL,'2009-01-04 18:57:55','Completed','2009-01-04 18:57:55',0,'','High',NULL,7,7,10,10,'','2009-01-04',4,7),
 (53,NULL,NULL,'check email',NULL,NULL,NULL,NULL,'2009-01-16 22:26:02','Completed','2009-01-07 21:01:29',0,'check email','High',NULL,7,7,1,0,'','2009-01-16',4,7),
 (54,NULL,NULL,'check email',NULL,NULL,NULL,NULL,'2009-01-07 21:02:16','New','2009-01-07 21:02:16',0,'check email','Medium',NULL,21,NULL,2,0,'',NULL,1,NULL),
 (56,NULL,NULL,'second demo',NULL,NULL,NULL,NULL,'2009-01-07 21:07:34','New','2009-01-07 21:07:34',0,'demo','Medium',NULL,21,NULL,2,0,'',NULL,1,NULL),
 (57,NULL,NULL,'testin',NULL,NULL,NULL,NULL,'2009-01-07 21:11:12','New','2009-01-07 21:11:12',0,'lk','Medium',NULL,21,NULL,2,0,'',NULL,1,NULL),
 (58,NULL,NULL,'demo',NULL,NULL,NULL,NULL,'2009-01-07 21:13:23','New','2009-01-07 21:13:23',0,'demo','Medium',NULL,21,NULL,3,0,'',NULL,1,NULL),
 (59,NULL,NULL,'dm',NULL,NULL,NULL,NULL,'2009-01-07 21:18:04','New','2009-01-07 21:18:04',0,'lk','Medium',NULL,21,NULL,2,0,'',NULL,1,NULL),
 (60,NULL,NULL,'demo',NULL,NULL,NULL,NULL,'2009-01-07 21:25:17','New','2009-01-07 21:25:17',0,'','Medium',NULL,21,NULL,2,0,'',NULL,1,NULL),
 (61,NULL,NULL,'demo',NULL,NULL,NULL,NULL,'2009-01-07 21:30:03','New','2009-01-07 21:30:03',0,'','Medium',NULL,21,NULL,2,0,'',NULL,1,NULL),
 (63,NULL,NULL,'first task',NULL,'2009-01-17 10:59:03','2009-01-17 10:59:03',NULL,'2009-01-17 11:27:30','Completed','2009-01-08 22:55:43',0,'','Medium',18,7,7,10,10,'','2009-01-17',4,7),
 (67,NULL,NULL,'testing date',NULL,NULL,NULL,NULL,'2009-01-16 22:24:46','Completed','2009-01-10 16:42:07',0,'','Medium',NULL,7,7,1,1,'','2009-01-11',3,7),
 (68,NULL,NULL,'testing date 2',NULL,NULL,NULL,NULL,'2009-01-16 22:40:04','Completed','2009-01-10 16:46:57',0,'','Medium',NULL,7,7,0,0,'','2009-01-16',3,7),
 (80,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,'2009-01-16 22:43:02',NULL,'2009-01-16 22:43:02','Completed','2009-01-10 23:49:05',0,'','High',NULL,7,7,0,0,'','2009-01-16',4,7),
 (81,NULL,NULL,'hello testing and playing with creating task and assign to o',NULL,NULL,NULL,NULL,'2009-01-16 22:39:08','Completed','2009-01-10 23:52:18',0,'','High',NULL,7,7,1,1,'','2009-01-16',3,7),
 (82,NULL,NULL,'test',NULL,NULL,NULL,NULL,'2009-01-16 22:28:35','Completed','2009-01-16 22:28:35',0,'','Medium',NULL,7,NULL,1,1,'',NULL,1,NULL),
 (83,NULL,NULL,'completed task',NULL,NULL,NULL,NULL,'2009-01-16 22:34:03','Completed','2009-01-16 22:34:03',0,'','Medium',NULL,7,NULL,2,2,'',NULL,1,NULL),
 (84,NULL,NULL,'new with completed status',NULL,NULL,'2009-01-16 22:38:33',NULL,'2009-01-16 22:38:33','Completed','2009-01-16 22:38:33',0,'','Medium',NULL,7,NULL,1,1,'',NULL,1,NULL),
 (85,NULL,NULL,'first task',NULL,'2009-01-16 00:00:00','2009-01-17 11:27:40',NULL,'2009-01-17 11:27:40','Completed','2009-01-17 11:27:40',0,'','Medium',18,7,NULL,10,11,'',NULL,1,NULL),
 (86,NULL,NULL,'first task',NULL,'2009-01-16 00:00:00','2009-01-17 11:27:54',NULL,'2009-01-17 11:27:54','Completed','2009-01-17 11:27:54',0,'','Medium',18,7,NULL,10,12,'',NULL,1,NULL),
 (87,NULL,NULL,'first task',NULL,'2009-01-16 00:00:00','2009-01-17 11:28:22',NULL,'2009-01-17 11:28:22','Completed','2009-01-17 11:28:22',0,'','Medium',18,7,NULL,10,12,'',NULL,1,NULL),
 (88,NULL,NULL,'first task',NULL,'2009-01-16 00:00:00','2009-01-17 11:28:42',NULL,'2009-01-17 11:28:42','Completed','2009-01-17 11:28:42',0,'','Medium',18,7,NULL,10,12,'',NULL,1,NULL),
 (89,NULL,NULL,'first task',NULL,'2009-01-16 00:00:00','2009-01-17 11:28:43',NULL,'2009-01-17 11:28:43','Completed','2009-01-17 11:28:43',0,'','Medium',18,7,NULL,10,12,'',NULL,1,NULL),
 (91,NULL,NULL,'cnn',NULL,'2009-01-15 00:00:00','2009-01-17 11:38:04',NULL,'2009-01-17 11:37:48','Completed','2009-01-17 11:37:48',0,'','Medium',NULL,7,7,1,1,'','2009-01-17',3,7),
 (92,NULL,NULL,'timesofindia',NULL,NULL,'2009-01-17 11:41:00',NULL,'2009-01-17 11:41:00','Completed','2009-01-17 11:39:58',0,'','Medium',NULL,7,7,2,2,'','2009-01-17',2,7),
 (93,NULL,NULL,'siasat.com',NULL,'2009-01-15 00:00:00','2009-01-17 11:42:39',NULL,'2009-01-17 11:41:56','Completed','2009-01-17 11:41:56',0,'','Medium',NULL,7,7,10,10,'','2009-01-17',2,7),
 (94,NULL,NULL,'tempo',NULL,NULL,'2009-01-17 11:48:55',NULL,'2009-01-17 11:48:20','Completed','2009-01-17 11:48:20',0,'','Medium',NULL,7,7,1,1,'','2009-01-17',2,7),
 (95,NULL,NULL,'dream task',NULL,'2009-01-24 00:00:00','2009-01-17 12:01:20',NULL,'2009-01-17 12:00:59','Completed','2009-01-17 12:00:59',0,'','Medium',NULL,7,7,2,2,'','2009-01-17',3,7),
 (96,NULL,NULL,'new task',NULL,NULL,NULL,NULL,'2009-01-17 14:55:33','New','2009-01-17 14:55:33',0,'','High',NULL,7,7,0,0,'','2009-01-17',2,7),
 (97,NULL,NULL,'bbc',NULL,NULL,'2009-01-20 05:36:58',NULL,'2009-01-17 14:56:10','Completed','2009-01-17 14:56:10',0,'','Medium',NULL,7,7,0,0,'','2009-01-17',3,7),
 (98,NULL,NULL,'aa',NULL,'2009-01-30 00:00:00',NULL,NULL,'2009-01-17 15:58:51','In Progress','2009-01-17 15:08:27',0,'','Medium',NULL,7,7,0,0,'','2009-01-17',2,7),
 (99,NULL,NULL,'sdfasd',NULL,NULL,NULL,NULL,'2009-01-17 15:54:35','On Hold','2009-01-17 15:08:38',0,'','Medium',NULL,7,7,0,0,'','2009-01-17',2,7),
 (100,NULL,NULL,'aaa',NULL,'2009-01-16 22:43:54',NULL,NULL,'2009-01-17 15:46:20','In Progress','2009-01-17 15:09:04',0,'','Medium',NULL,7,7,0,0,'','2009-01-18',4,7),
 (101,NULL,NULL,'new task for testing',NULL,'2009-01-24 00:00:00',NULL,NULL,'2009-01-18 04:28:18','New','2009-01-18 04:28:18',0,'testing','Medium',NULL,7,NULL,2,0,'',NULL,1,NULL),
 (102,NULL,NULL,'osman',NULL,NULL,NULL,NULL,'2009-01-18 22:12:06','New','2009-01-18 22:12:06',0,'','Medium',NULL,7,7,0,0,'','2009-01-18',2,7),
 (103,NULL,NULL,'remember to blog on friday',NULL,'2009-01-29 00:00:00',NULL,NULL,'2009-01-19 04:03:51','New','2009-01-19 04:03:51',0,'','Medium',NULL,7,7,0,0,'','2009-01-20',2,7),
 (104,NULL,NULL,'Reminder, send timesheet before end of the day',NULL,'2009-01-28 00:00:00',NULL,NULL,'2009-01-19 04:04:20','New','2009-01-19 04:04:20',0,'','Medium',NULL,7,NULL,0,0,'',NULL,1,NULL),
 (105,NULL,NULL,'call vendor and submit ticket to resolve issue ',NULL,'2009-01-31 00:00:00',NULL,NULL,'2009-01-19 04:04:51','New','2009-01-19 04:04:51',0,'','Medium',NULL,7,NULL,0,0,'',NULL,1,NULL),
 (106,NULL,NULL,'testing new build',NULL,'2009-01-30 00:00:00',NULL,NULL,'2009-01-20 00:27:06','New','2009-01-20 00:27:06',0,'','Medium',NULL,7,5,1,0,'','2009-01-20',1,7),
 (109,NULL,NULL,'improve signup.jsp along with validation',NULL,'2009-01-22 00:00:00',NULL,NULL,'2009-01-20 05:53:23','New','2009-01-20 03:49:57',0,'','Medium',19,8,8,2,0,'','2009-01-20',2,8),
 (111,NULL,NULL,'test, does this how success msg',NULL,NULL,'2009-01-20 04:05:03',NULL,'2009-01-20 04:04:47','Completed','2009-01-20 04:04:47',0,'','Medium',NULL,8,8,0,0,'','2009-01-20',4,8),
 (112,NULL,NULL,'test, does this assigns user',NULL,NULL,'2009-01-20 04:05:39',NULL,'2009-01-20 04:05:31','Completed','2009-01-20 04:05:31',0,'','Medium',NULL,8,8,0,0,'','2009-01-20',3,8),
 (113,NULL,NULL,'intesar task do this',NULL,'2009-01-30 00:00:00',NULL,NULL,'2009-01-20 05:29:12','New','2009-01-20 05:29:12',0,'','Medium',NULL,7,NULL,1,0,'',NULL,1,NULL),
 (114,NULL,NULL,'Troy',NULL,'2009-01-20 00:00:00',NULL,NULL,'2009-01-20 05:30:01','New','2009-01-20 05:30:01',0,'test','Medium',NULL,7,NULL,1,0,'shiraaz@urooj.net',NULL,1,NULL),
 (115,NULL,NULL,'conference',NULL,'2009-01-28 00:00:00',NULL,NULL,'2009-01-20 05:31:32','New','2009-01-20 05:31:32',0,'Iam still working on it','Medium',NULL,7,NULL,1,1,'salman@urooj.net',NULL,1,NULL),
 (116,NULL,NULL,'validation for New Task, Edit Task',NULL,'2009-01-23 00:00:00',NULL,NULL,'2009-01-20 05:54:03','New','2009-01-20 05:53:01',0,'','Medium',19,8,NULL,1,0,'',NULL,2,NULL),
 (117,NULL,NULL,'validation for new user',NULL,NULL,NULL,NULL,'2009-01-20 05:54:55','New','2009-01-20 05:54:22',0,'','Medium',19,8,NULL,1,0,'',NULL,2,NULL),
 (118,NULL,NULL,'validation for new project',NULL,NULL,NULL,NULL,'2009-01-20 05:54:33','New','2009-01-20 05:54:33',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (119,NULL,NULL,'validation for add user to project',NULL,NULL,NULL,NULL,'2009-01-20 05:54:48','New','2009-01-20 05:54:48',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (120,NULL,NULL,'add title to all links so when mouse hover it shows',NULL,NULL,NULL,NULL,'2009-01-20 05:56:34','New','2009-01-20 05:55:42',0,'','Medium',19,8,NULL,2,0,'',NULL,2,NULL),
 (121,NULL,NULL,'help link should have all abbrevations used on this project',NULL,NULL,NULL,NULL,'2009-01-20 05:56:25','New','2009-01-20 05:56:25',0,'','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (122,NULL,NULL,'add security and performance info on home page login.jsp',NULL,NULL,NULL,NULL,'2009-01-20 05:57:37','New','2009-01-20 05:57:37',0,'','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (123,NULL,NULL,'add product features info on login.jsp',NULL,NULL,NULL,NULL,'2009-01-20 05:58:02','New','2009-01-20 05:58:02',0,'','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (124,NULL,NULL,'reduce refresh rate to 2mins',NULL,NULL,NULL,NULL,'2009-01-20 05:58:43','New','2009-01-20 05:58:43',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (125,NULL,NULL,'design Grow-Fast logo',NULL,NULL,NULL,NULL,'2009-01-20 05:59:01','New','2009-01-20 05:59:01',0,'','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (126,NULL,NULL,'improve content send on sign up email',NULL,NULL,NULL,NULL,'2009-01-20 05:59:28','New','2009-01-20 05:59:28',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (127,NULL,NULL,'improve content send on new task or edit task email',NULL,NULL,NULL,NULL,'2009-01-20 05:59:46','New','2009-01-20 05:59:46',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (128,NULL,NULL,'improve content send on task complete1',NULL,NULL,NULL,NULL,'2009-01-20 06:00:04','New','2009-01-20 06:00:04',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (129,NULL,NULL,'resolve email send bug on task create, udpate or complete',NULL,NULL,NULL,NULL,'2009-01-20 06:01:13','New','2009-01-20 06:01:13',0,'user should recieve only one email, currently user is getting two emails if he is assigned and owner','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (130,NULL,NULL,'put a firefox link on the homepage',NULL,NULL,NULL,NULL,'2009-01-20 06:02:37','New','2009-01-20 06:02:37',0,'currently supported browser are firefox, chrome, opera','Medium',19,8,NULL,0,0,'',NULL,1,NULL),
 (132,NULL,NULL,'format new task, edit task form properly, layout is not that good',NULL,NULL,NULL,NULL,'2009-01-20 08:10:44','New','2009-01-20 06:04:22',0,'','Medium',19,8,NULL,1,0,'',NULL,2,NULL),
 (133,NULL,NULL,'replace logout image with some decent image',NULL,NULL,NULL,NULL,'2009-01-20 06:04:55','New','2009-01-20 06:04:55',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (134,NULL,NULL,'show logged in user firstname and lastname on top so user knows he is logged in right',NULL,NULL,NULL,NULL,'2009-01-20 06:05:54','New','2009-01-20 06:05:54',0,'','Medium',19,8,NULL,3,0,'',NULL,1,NULL),
 (135,NULL,NULL,'publish that we build Grow-Fast on Grow-fast, since we are using grow-fast to complete its',NULL,NULL,NULL,NULL,'2009-01-20 06:07:34','New','2009-01-20 06:07:34',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (136,NULL,NULL,'provide direct task link in email, so click should open task in edit mode',NULL,NULL,NULL,NULL,'2009-01-20 06:08:23','New','2009-01-20 06:08:23',0,'','Medium',19,8,NULL,6,0,'',NULL,1,NULL),
 (137,NULL,NULL,'organizations should be able to use there mail servers to send emails,',NULL,NULL,NULL,NULL,'2009-01-20 06:12:07','New','2009-01-20 06:12:07',0,'create this functionality','Medium',19,8,NULL,10,0,'',NULL,1,NULL),
 (138,NULL,NULL,'login page should be similar to google apps, ',NULL,NULL,NULL,NULL,'2009-01-20 06:13:13','New','2009-01-20 06:13:13',0,'organizations should be able to configure login page company name something similar to google apps','Medium',19,8,NULL,10,0,'',NULL,1,NULL),
 (139,NULL,NULL,'user report should return zeros but not null instance on right hand graph',NULL,NULL,NULL,NULL,'2009-01-20 07:44:41','New','2009-01-20 07:44:41',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (140,NULL,NULL,'implement functionality for daily report send to admins',NULL,NULL,NULL,NULL,'2009-01-20 08:10:30','New','2009-01-20 07:47:28',0,'','Medium',19,8,NULL,10,0,'',NULL,2,NULL),
 (141,NULL,NULL,'re-occuring task feature',NULL,NULL,NULL,NULL,'2009-01-20 07:53:18','New','2009-01-20 07:53:18',0,'1. Add functionality from page to db\n2. reocurring task, tasks which occur on some intervals like daily, weekly, monthly etc\n3. like bill payment on every 2nd of month','Medium',19,8,NULL,20,0,'',NULL,1,NULL),
 (142,NULL,NULL,'task report for unassigned tasks, ',NULL,NULL,NULL,NULL,'2009-01-20 07:55:46','New','2009-01-20 07:55:46',0,'1. users should be able to see all dangling tasks reports, which give a view of all task open and how we plan to complete them\n','Medium',19,8,NULL,8,0,'',NULL,1,NULL),
 (143,NULL,NULL,'create demo link link, remove user/pass from login section',NULL,NULL,NULL,NULL,'2009-01-20 08:01:55','New','2009-01-20 08:01:55',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (144,NULL,NULL,'sorting not working on task table',NULL,NULL,NULL,NULL,'2009-01-20 08:05:10','New','2009-01-20 08:05:10',0,'','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (145,NULL,NULL,'project users table looks squeezed, fix it',NULL,NULL,NULL,NULL,'2009-01-20 08:06:42','New','2009-01-20 08:06:42',0,'','Medium',19,8,NULL,2,0,'',NULL,1,NULL),
 (146,NULL,NULL,'improve look&feel of forgot password page',NULL,NULL,NULL,NULL,'2009-01-20 08:10:11','New','2009-01-20 08:10:11',0,'','Medium',19,8,NULL,1,0,'',NULL,1,NULL),
 (147,NULL,NULL,'create a link for newsgroup or forum',NULL,NULL,NULL,NULL,'2009-01-21 02:35:09','New','2009-01-21 02:32:30',0,'','Medium',19,8,NULL,2,0,'',NULL,2,NULL),
 (148,NULL,NULL,'create a updated News section on site',NULL,NULL,NULL,NULL,'2009-01-21 02:35:17','New','2009-01-21 02:33:10',0,'','Medium',19,8,NULL,2,0,'',NULL,2,NULL),
 (149,NULL,NULL,'link for release of updated builds on a regular basis',NULL,NULL,NULL,NULL,'2009-01-21 02:35:24','New','2009-01-21 02:33:58',0,'','Medium',19,8,NULL,4,0,'',NULL,2,NULL),
 (150,NULL,NULL,'create section on home page for \"technology & tool\" provider or powered by and include',NULL,NULL,NULL,NULL,'2009-01-21 02:38:50','New','2009-01-21 02:38:50',0,'Java, Spring, MySQL, Netbeans, Apache Tomcat, Maven, Ubuntu, JQuery, etc','Medium',19,8,NULL,2,0,'',NULL,1,NULL);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;


--
-- Definition of table `task_comments`
--

DROP TABLE IF EXISTS `task_comments`;
CREATE TABLE `task_comments` (
  `id` int(11) NOT NULL,
  `_text` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `task` int(11) NOT NULL,
  `create_user` int(11) default NULL,
  PRIMARY KEY  (`id`),
  CONSTRAINT `FK_task_comments_id` FOREIGN KEY (`id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task_comments`
--

/*!40000 ALTER TABLE `task_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_comments` ENABLE KEYS */;


--
-- Definition of table `task_history`
--

DROP TABLE IF EXISTS `task_history`;
CREATE TABLE `task_history` (
  `id` int(11) NOT NULL,
  `_status` varchar(255) NOT NULL,
  `priority` varchar(255) NOT NULL,
  `description` varchar(255) default NULL,
  `sequence_number` double default NULL,
  `visibility` varchar(255) NOT NULL,
  `update_date` datetime default NULL,
  `title` varchar(255) NOT NULL,
  `percentage_completed` int(11) NOT NULL,
  `deadline` datetime default NULL,
  `task` int(11) default NULL,
  `update_user` int(11) default NULL,
  `assigned_to` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task_history`
--

/*!40000 ALTER TABLE `task_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_history` ENABLE KEYS */;


--
-- Definition of table `task_impedance`
--

DROP TABLE IF EXISTS `task_impedance`;
CREATE TABLE `task_impedance` (
  `id` int(11) NOT NULL,
  `resolved_date` datetime NOT NULL,
  `last_updated_user` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `_text` varchar(255) NOT NULL,
  `is_resolved` tinyint(1) NOT NULL default '0',
  `task` int(11) default NULL,
  `create_user` int(11) default NULL,
  `resolved_user` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task_impedance`
--

/*!40000 ALTER TABLE `task_impedance` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_impedance` ENABLE KEYS */;


--
-- Definition of table `user_report`
--

DROP TABLE IF EXISTS `user_report`;
CREATE TABLE `user_report` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user` int(10) unsigned NOT NULL,
  `month` int(10) unsigned NOT NULL,
  `year` int(10) unsigned NOT NULL,
  `total_created` int(10) unsigned default '0',
  `assigned` int(10) unsigned default '0',
  `self_assigned` int(10) unsigned default '0',
  `total_assigned` int(10) unsigned default '0',
  `estimated_hours` double unsigned default '500',
  `hours_spend` double unsigned default '0',
  `total_completed` int(10) unsigned NOT NULL default '0',
  `created_self_assigned` int(10) unsigned default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_3` (`user`,`month`,`year`),
  KEY `Index_4` (`assigned`),
  KEY `Index_5` (`total_completed`),
  KEY `Index_6` (`hours_spend`),
  CONSTRAINT `FK_user_report_1` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_report`
--

/*!40000 ALTER TABLE `user_report` DISABLE KEYS */;
INSERT INTO `user_report` (`id`,`user`,`month`,`year`,`total_created`,`assigned`,`self_assigned`,`total_assigned`,`estimated_hours`,`hours_spend`,`total_completed`,`created_self_assigned`) VALUES 
 (10,7,9,2008,50,50,10,60,180,160,100,0),
 (11,7,10,2008,40,20,20,40,200,190,40,5),
 (12,7,11,2008,50,10,7,7,190,200,25,10),
 (13,7,0,2009,57,0,0,34,106,190,29,34),
 (14,5,0,2009,0,1,0,1,3,0,0,0),
 (17,8,0,2009,38,0,0,2,0,0,2,2);
/*!40000 ALTER TABLE `user_report` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `username` varchar(45) NOT NULL,
  `password` varchar(145) NOT NULL,
  `is_encrypted` tinyint(1) unsigned default NULL,
  `enabled` tinyint(1) unsigned NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `city` varchar(45) default NULL,
  `country` varchar(45) default NULL,
  `create_user` int(10) unsigned default NULL,
  `create_date` datetime NOT NULL,
  `is_administrator` tinyint(1) unsigned NOT NULL default '0',
  `expiration_date` date default NULL,
  `last_update_user` varchar(45) default NULL,
  `organization` int(10) unsigned NOT NULL,
  `last_update_date` datetime default NULL,
  `vid` int(10) unsigned default NULL,
  `is_super` tinyint(1) unsigned default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_3` (`username`),
  UNIQUE KEY `Index_4` (`email`),
  KEY `FK_users_organization` (`organization`),
  KEY `Index_5` (`firstname`),
  KEY `Index_6` (`lastname`),
  KEY `Index_7` (`firstname`,`lastname`),
  KEY `Index_8` (`city`),
  KEY `Index_9` (`country`),
  CONSTRAINT `FK_users_1` FOREIGN KEY (`organization`) REFERENCES `organization` (`id`),
  CONSTRAINT `FK_users_organization` FOREIGN KEY (`organization`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`username`,`password`,`is_encrypted`,`enabled`,`firstname`,`lastname`,`email`,`city`,`country`,`create_user`,`create_date`,`is_administrator`,`expiration_date`,`last_update_user`,`organization`,`last_update_date`,`vid`,`is_super`) VALUES 
 (5,'mdshannan@gmail.com','d0nad9J2DLgqygxEIUXR7xeDyNz5sjilQ9/poHNvLQKAoz68LUMZC4E3yLiZMh0L',1,1,'mohammed','shannan','mdshannan@gmail.com','hyderabad',NULL,NULL,'2008-11-26 23:14:33',1,NULL,'7',8,'2009-01-17 17:14:51',3,0),
 (7,'fatima.shanoor@gmail.com','uKR0wQutHl2fNWsQmTRYODC2kMZHQwfreK6fMv6EwPfD5Xxp7IKrjiM5B03vNCr6',1,1,'admin','admin','fatima.shanoor@gmail.com',NULL,NULL,5,'2008-11-28 23:01:55',1,NULL,'7',8,'2009-01-18 05:30:49',3,0),
 (8,'intesar.mohammed@bizintelapps.com','uJpP4TUXQOAG2AMLljRT3I/7Iwf20JsPDYQxtFRmSYdNJZFPVqFsuTuwc+RAwAcK',1,1,'intesar','mohammed','intesar.mohammed@bizintelapps.com',NULL,NULL,NULL,'2009-01-20 03:37:24',1,NULL,NULL,9,'2009-01-20 03:37:24',2,1),
 (9,'fatimashanoor1@gmail.com','BQURIvVn3E8D47duTD/hDPwZ9K5h07ZCeDQAPok8tfG0Auueppr+IO61Yn1ytwZj',1,1,'shanoor','fatima','fatimashanoor1@gmail.com',NULL,NULL,8,'2009-01-20 03:39:33',0,NULL,'8',9,'2009-01-20 03:39:33',1,0),
 (10,'salman@urooj.net','05oqoqqMGjDTKsTq/VczWGruNpbzIA6KxQU+OQgwVOefX1pKznWwxxZg+ZwkKqoY',1,1,'Salman','Mohammed','salman@urooj.net',NULL,NULL,NULL,'2009-01-20 04:36:21',1,NULL,NULL,10,'2009-01-20 04:36:21',1,1),
 (11,'raadia@urooj.net','nGzochf0L/Af9VkhMhRqJmNkwQvWF7a0/3qKqJTqFWSDHS7QDDDQXduC5FpqElc8',1,1,'Raadia','samaher','raadia@urooj.net',NULL,NULL,NULL,'2009-01-20 05:06:33',1,NULL,NULL,11,'2009-01-20 05:06:33',1,1),
 (12,'anuj.s@urooj.net','+UtE6LZzrQLJoWzBCN7n2Hzj9EWfjzjmU3ylw+4W8dwe6pBw1HBMdV0veBtf1LbH',1,1,'Anuj','Sharma','anuj.s@urooj.net',NULL,NULL,NULL,'2009-01-20 05:18:55',1,NULL,NULL,12,'2009-01-20 05:18:55',1,1),
 (13,'shiraaz@urooj.net','ABQq0qXl/HGOb5RhAFcJyZm/6WmCr4n01LBNV78TeU0bcUFvPf4ISimkr4AGe32I',1,1,'shiraaz','khan','shiraaz@urooj.net',NULL,NULL,NULL,'2009-01-20 20:07:44',1,NULL,NULL,13,'2009-01-20 20:07:44',1,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
