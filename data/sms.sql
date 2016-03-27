-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: sms
-- ------------------------------------------------------
-- Server version	5.5.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admission`
--

DROP TABLE IF EXISTS `admission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admission_num` double DEFAULT NULL,
  `admission_date` date DEFAULT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `class` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(45) NOT NULL,
  `blood_group` varchar(45) NOT NULL,
  `birth_place` varchar(45) NOT NULL,
  `nationality` varchar(45) NOT NULL,
  `religion` varchar(45) DEFAULT NULL,
  `cast_category` varchar(45) DEFAULT NULL,
  `physical_disability` tinyint(1) DEFAULT NULL,
  `subcast` varchar(45) DEFAULT NULL,
  `mother_toungue` varchar(45) NOT NULL,
  `local_address` varchar(100) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `pincode` double DEFAULT NULL,
  `perm_address` varchar(100) DEFAULT NULL,
  `perm_city` varchar(45) DEFAULT NULL,
  `perm_state` varchar(45) DEFAULT NULL,
  `perm_pincode` double DEFAULT NULL,
  `phone_number` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `image` text,
  `mother_full_name` varchar(45) DEFAULT NULL,
  `mother_occupation` varchar(45) DEFAULT NULL,
  `mother_education` varchar(45) DEFAULT NULL,
  `mother_income` double DEFAULT NULL,
  `father_full_name` varchar(45) DEFAULT NULL,
  `father_occupation` varchar(50) DEFAULT NULL,
  `father_education` varchar(45) DEFAULT NULL,
  `father_income` double DEFAULT NULL,
  `parent_as_guardian` tinyint(1) DEFAULT NULL,
  `primary_contact_num` varchar(45) DEFAULT NULL,
  `secondary_contact_number` varchar(45) DEFAULT NULL,
  `prev_institution` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `previous_class` varchar(45) DEFAULT NULL,
  `total_marks` double DEFAULT NULL,
  `marks_obtained` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admission`
--

LOCK TABLES `admission` WRITE;
/*!40000 ALTER TABLE `admission` DISABLE KEYS */;
INSERT INTO `admission` VALUES (1,NULL,NULL,'John2','S1','7th','2006-01-01','male','AB+ve','Hyderabad','Indian','Hindu','OBC',0,NULL,'telugu','Snehapuri colony,Motinagar','Hyderabad','Telangana',500018,'Snehapuri Colony,Motinagar','Hyderabad','Telangana',500018,'8099307634','madhunali@gmail.com',NULL,'Katy','House Wife',NULL,NULL,'Jack','0','MCA',500000,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,987654321,'2015-08-27','Stuart','K','7th','2005-05-05','male','O+ve','Vijatawada','Indian','Muslim','OBC',0,NULL,'telugu','KpHB Colony','Hyderabad','Tlangana',500019,'Benz circle','Vijayawad','Andhrapradesh',522658,'9703713997','madhu4u.nali@gmail.com',NULL,'Lara','House Wife',NULL,NULL,'Broad','0','BTECH',700000,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,123456780,'2015-08-28','John1','S1','7th','2006-01-01','male','AB+ve','Hyderabad','Indian','Hindu','OBC',0,NULL,'telugu','Snehapuri colony,Motinagar','Hyderabad','Telangana',500018,'Snehapuri Colony,Motinagar','Hyderabad','Telangana',500018,'8099307634','madhu@gmail.com',NULL,'Katy','House Wife',NULL,NULL,'Jack','0','MCA',500000,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `admission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `dateOfAbsent` date DEFAULT NULL,
  `classes_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `weekly_status` tinyint(4) DEFAULT '0',
  `monthly_status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `attendance_foreignKey_idx` (`st_id`),
  KEY `attendance_foreignKey1_idx` (`classes_id`),
  KEY `attendance_foreignKey2_idx` (`section_id`),
  CONSTRAINT `attendance_foreignKey` FOREIGN KEY (`st_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `attendance_foreignKey1` FOREIGN KEY (`classes_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `attendance_foreignKey2` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (33,40,0,'2015-03-09',13,1,0,1),(34,41,1,'2015-03-09',13,1,0,1),(35,42,0,'2015-03-09',13,1,0,1),(36,40,1,'2015-03-10',13,1,0,1),(37,41,0,'2015-03-10',13,1,0,1),(38,42,1,'2015-03-10',13,1,0,1),(39,40,1,'2015-03-11',13,1,0,1),(40,41,1,'2015-03-11',13,1,0,1),(41,42,1,'2015-03-11',13,1,0,1),(42,40,1,'2015-03-12',13,1,0,1),(43,41,1,'2015-03-12',13,1,0,1),(44,42,1,'2015-03-12',13,1,0,1),(45,40,1,'2015-03-13',13,1,0,1),(46,41,1,'2015-03-13',13,1,0,1),(47,42,1,'2015-03-13',13,1,0,1),(48,40,1,'2015-03-14',13,1,0,1),(49,41,1,'2015-03-14',13,1,0,1),(50,42,0,'2015-03-14',13,1,0,1);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_subject_teacher_mapping`
--

DROP TABLE IF EXISTS `class_subject_teacher_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_subject_teacher_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_subject_teacher_mapping_foreignKey1_idx` (`class_id`),
  KEY `class_subject_teacher_mapping_foreignKey2_idx` (`subject_id`),
  KEY `class_subject_teacher_mapping_foreignKey3_idx` (`teacher_id`),
  KEY `class_subject_teacher_mapping_foreignKey1_idx1` (`section_id`),
  CONSTRAINT `class_subject_teacher_mapping_foreignKey1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `class_subject_teacher_mapping_foreignKey2` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `class_subject_teacher_mapping_foreignKey3` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `class_subject_teacher_mapping_foreignKey4` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_subject_teacher_mapping`
--

LOCK TABLES `class_subject_teacher_mapping` WRITE;
/*!40000 ALTER TABLE `class_subject_teacher_mapping` DISABLE KEYS */;
INSERT INTO `class_subject_teacher_mapping` VALUES (1,13,1,1,1),(2,13,2,2,1),(3,13,3,3,1),(4,13,4,4,1),(5,12,1,1,1),(6,12,2,2,1),(7,12,3,3,1),(8,12,4,4,1),(9,12,5,5,1),(10,12,6,5,1);
/*!40000 ALTER TABLE `class_subject_teacher_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES (1,'Nursary'),(2,'LKG'),(3,'UKG'),(4,'1st'),(5,'2nd'),(6,'3rd'),(7,'4th'),(8,'5th'),(9,'6th'),(10,'7th'),(11,'8th'),(12,'9th'),(13,'10th');
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `days`
--

DROP TABLE IF EXISTS `days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `days` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `days`
--

LOCK TABLES `days` WRITE;
/*!40000 ALTER TABLE `days` DISABLE KEYS */;
INSERT INTO `days` VALUES (1,'Monday'),(2,'Tuesday'),(3,'Wednesday'),(4,'Thursday'),(5,'Friday'),(6,'Saturday'),(7,'Sunday');
/*!40000 ALTER TABLE `days` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (24,'Madhusudhana Rao','Nali','madhuinfo99@gmail.com','7386766574'),(25,'Madhu','N','madhu4u.nali@gmail.com','8099307634'),(27,'NMSR','Nali','madhu@gmail.com','123456789');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examschedule`
--

DROP TABLE IF EXISTS `examschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examschedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `examtype_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `examdate` date DEFAULT NULL,
  `start_time` varchar(45) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `examschedule_foreignkey1_idx` (`class_id`),
  KEY `examschedule_foreignkey2_idx` (`examtype_id`),
  KEY `examschedule_foreignkey3_idx` (`subject_id`),
  CONSTRAINT `examschedule_foreignkey1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `examschedule_foreignkey2` FOREIGN KEY (`examtype_id`) REFERENCES `examtype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `examschedule_foreignkey3` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examschedule`
--

LOCK TABLES `examschedule` WRITE;
/*!40000 ALTER TABLE `examschedule` DISABLE KEYS */;
INSERT INTO `examschedule` VALUES (66,13,1,1,'2015-09-05','10AM','11AM','2015-09-14 01:48:32'),(67,13,1,2,'2015-09-05','11AM','12PM','2015-09-14 01:48:32'),(68,13,1,3,'2015-09-06','10AM','11AM','2015-09-14 01:48:32'),(69,13,1,4,'2015-09-06','11AM','12PM','2015-09-14 01:48:32'),(70,13,1,5,'2015-09-07','10AM','11AM','2015-09-14 01:48:32'),(71,13,1,6,'2015-09-07','11AM','12PM','2015-09-14 01:48:32');
/*!40000 ALTER TABLE `examschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examtype`
--

DROP TABLE IF EXISTS `examtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ex_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examtype`
--

LOCK TABLES `examtype` WRITE;
/*!40000 ALTER TABLE `examtype` DISABLE KEYS */;
INSERT INTO `examtype` VALUES (1,'Unit1'),(2,'Unit2'),(3,'Unit3'),(4,'Unit4'),(5,'Quarterly'),(6,'HalfYearly'),(7,'Pre-Final'),(8,'Final');
/*!40000 ALTER TABLE `examtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holidays` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holidays`
--

LOCK TABLES `holidays` WRITE;
/*!40000 ALTER TABLE `holidays` DISABLE KEYS */;
INSERT INTO `holidays` VALUES (7,'Muharram ','2015-12-11','2015-10-18 17:49:12'),(8,'Deepavali ','2015-11-11','2015-10-18 17:49:12'),(9,'Dasara','2015-10-22','2015-10-18 17:49:12');
/*!40000 ALTER TABLE `holidays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marks_table`
--

DROP TABLE IF EXISTS `marks_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marks_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` int(11) DEFAULT NULL,
  `subj_id` int(11) DEFAULT NULL,
  `marks_obtained` double DEFAULT NULL,
  `max_marks` double DEFAULT NULL,
  `subject_wise_rank` int(11) DEFAULT NULL,
  `date_of_exam` date DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `exam_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `marks_foreignKey_idx` (`st_id`),
  KEY `marks_foreignKey1_idx` (`subj_id`),
  KEY `marks_foreignKey3_idx` (`section_id`),
  KEY `marks_foreignKey2_idx` (`class_id`),
  KEY `marks_foreignKey3_idx1` (`exam_type`),
  CONSTRAINT `marks_foreignKey` FOREIGN KEY (`st_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `marks_foreignKey1` FOREIGN KEY (`subj_id`) REFERENCES `subject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `marks_foreignKey2` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `marks_foreignKey3` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `marks_foreignKey4` FOREIGN KEY (`exam_type`) REFERENCES `examtype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marks_table`
--

LOCK TABLES `marks_table` WRITE;
/*!40000 ALTER TABLE `marks_table` DISABLE KEYS */;
INSERT INTO `marks_table` VALUES (43,42,1,85,100,1,'2015-01-09',13,1,5),(44,41,1,85,100,1,'2015-01-09',13,1,5),(45,40,1,84,100,2,'2015-01-09',13,1,5),(46,40,2,71,100,1,'2015-02-09',13,1,5),(47,42,2,70,100,2,'2015-02-09',13,1,5),(48,41,2,70,100,2,'2015-02-09',13,1,5),(49,40,3,76,100,1,'2015-03-09',13,1,5),(50,42,3,75,100,2,'2015-03-09',13,1,5),(51,41,3,75,100,2,'2015-03-09',13,1,5),(52,40,4,91,100,1,'2015-04-09',13,1,5),(53,42,4,90,100,2,'2015-04-09',13,1,5),(54,41,4,90,100,2,'2015-04-09',13,1,5),(55,40,5,91,100,1,'2015-05-09',13,1,5),(56,42,5,90,100,2,'2015-05-09',13,1,5),(57,41,5,90,100,2,'2015-05-09',13,1,5),(58,40,6,91,100,1,'2015-06-09',13,1,5),(59,42,6,90,100,2,'2015-06-09',13,1,5),(60,41,6,90,100,2,'2015-06-09',13,1,5);
/*!40000 ALTER TABLE `marks_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagetype`
--

DROP TABLE IF EXISTS `messagetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagetype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagetype`
--

LOCK TABLES `messagetype` WRITE;
/*!40000 ALTER TABLE `messagetype` DISABLE KEYS */;
INSERT INTO `messagetype` VALUES (1,'General'),(2,'Classwise'),(3,'Individual'),(4,'Transactions'),(5,'Activities'),(6,'Parent'),(7,'Admin'),(8,'Teacher');
/*!40000 ALTER TABLE `messagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthlywise_attendance`
--

DROP TABLE IF EXISTS `monthlywise_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthlywise_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` int(11) DEFAULT NULL,
  `month` varchar(45) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `date_of_absent` date DEFAULT NULL,
  `total_days_present` int(11) DEFAULT NULL,
  `total_days_absent` int(11) DEFAULT NULL,
  `num_of_working_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `monthlywise_attendance_foreignKey_idx` (`st_id`),
  CONSTRAINT `monthlywise_attendance_foreignKey` FOREIGN KEY (`st_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthlywise_attendance`
--

LOCK TABLES `monthlywise_attendance` WRITE;
/*!40000 ALTER TABLE `monthlywise_attendance` DISABLE KEYS */;
INSERT INTO `monthlywise_attendance` VALUES (4,40,'Mar',0,NULL,5,1,6),(5,41,'Mar',0,NULL,5,1,6),(6,42,'Mar',0,NULL,4,2,6);
/*!40000 ALTER TABLE `monthlywise_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_id` int(11) DEFAULT NULL,
  `message_type` int(11) DEFAULT NULL,
  `message` text,
  `status` tinyint(1) DEFAULT NULL,
  `from_name` varchar(45) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `to_name` varchar(45) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notifications_foreignKey_idx` (`message_type`),
  KEY `notifications_foreignKey1_idx` (`from_id`),
  CONSTRAINT `notifications_foreignKey` FOREIGN KEY (`message_type`) REFERENCES `messagetype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `notifications_foreignKey1` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COMMENT='					';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (6,8,8,'How about my child',0,'Parent',1,'Teacher',13,1,'2015-09-22 07:00:00'),(8,11,3,'Your child not studying well',0,'Teacher',40,'Parent',13,1,'2015-09-27 02:04:19'),(9,11,2,'Child should get things',0,'Teacher',NULL,NULL,13,1,'2015-09-27 02:12:49'),(10,1,3,'Fee Reminder',0,'Admin',41,'Parent',13,1,'2015-09-27 02:30:52'),(11,1,2,'Fee Reminder',0,'Admin',NULL,NULL,13,1,'2015-09-27 03:05:03'),(12,1,1,'Fee Reminder',0,'Admin',NULL,NULL,NULL,NULL,'2015-09-27 03:14:56'),(13,1,5,'Activites started',0,'Admin',NULL,NULL,NULL,NULL,'2015-09-27 03:21:14'),(14,8,7,'Fee already paid',0,'Parent',0,'Admin',13,1,'2015-10-10 18:30:00'),(15,8,8,'My child not studying well',0,'Parent',1,'Teacher',NULL,NULL,'2015-10-12 17:10:28');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_table`
--

DROP TABLE IF EXISTS `permission_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_table`
--

LOCK TABLES `permission_table` WRITE;
/*!40000 ALTER TABLE `permission_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rank`
--

DROP TABLE IF EXISTS `rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` int(11) DEFAULT NULL,
  `exam_type` varchar(45) DEFAULT NULL,
  `marks_obtained` double DEFAULT NULL,
  `max_marks` double DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rank_foreignKey1_idx` (`st_id`),
  CONSTRAINT `rank_foreignKey1` FOREIGN KEY (`st_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank`
--

LOCK TABLES `rank` WRITE;
/*!40000 ALTER TABLE `rank` DISABLE KEYS */;
/*!40000 ALTER TABLE `rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Parent'),(3,'Teacher');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_permissions_foreignKey_idx` (`role_id`),
  CONSTRAINT `role_permissions_foreignKey` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_name` varchar(45) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `section_foreignKey_idx` (`class_id`),
  CONSTRAINT `section_foreignKey` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,'A',13),(2,'B',13),(3,'C',13),(4,'D',13),(5,'A',12),(6,'B',12),(7,'C',12),(8,'D',12),(9,'A',11),(10,'B',11),(11,'C',11),(12,'D',11);
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `roll_number` int(11) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `blood_group` varchar(45) DEFAULT NULL,
  `relegion` varchar(45) DEFAULT NULL,
  `cast_category` varchar(100) DEFAULT NULL,
  `subcast` varchar(45) DEFAULT NULL,
  `mother_tongue` varchar(45) DEFAULT NULL,
  `local_address` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `pincode` double DEFAULT NULL,
  `perm_address` varchar(45) DEFAULT NULL,
  `perm_city` varchar(45) DEFAULT NULL,
  `perm_state` varchar(45) DEFAULT NULL,
  `perm_pincode` double DEFAULT NULL,
  `mother_full_name` varchar(45) DEFAULT NULL,
  `mother_occupation` varchar(45) DEFAULT NULL,
  `mother_education` varchar(45) DEFAULT NULL,
  `father_full_name` varchar(45) DEFAULT NULL,
  `father_occupation` varchar(45) DEFAULT NULL,
  `total_income` double DEFAULT NULL,
  `primary_contact_number` varchar(45) DEFAULT NULL,
  `secondary_contact_number` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `joined_date` date DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `classes_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `father_education` varchar(45) DEFAULT NULL,
  `physical_disability` tinyint(4) DEFAULT NULL,
  `parent_as_guardian` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_foreignKey2_idx` (`subcast`),
  KEY `student_foreignkey_idx` (`classes_id`),
  KEY `student_forignkey1_idx` (`section_id`),
  CONSTRAINT `student_foreignkey` FOREIGN KEY (`classes_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `student_forignkey1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (40,'Madhu','Nali',2,'1999-11-16','male','AB+ve','hindu','OBC','Yadava','Telugu','motinagar','Hyderabad','Telangana',500018,'ABM Compound','Vinukonda','AndhraPradesh',522647,'AdiLakshmi','House wife','Illiturate','RameswaraRo','Business',500000,'7386766574','8099307634','madhuinfo992@gmail.com','2013-09-15','2015-09-21 03:37:43',13,1,'Illiterate',0,1),(41,'NMSR','Nali',3,'2000-11-17','male','B+ve','Muslim','OC','Turaka','Telugu','motinagar','Hyderabad','Telangana',500018,'ABM Compound','Vinukonda','AndhraPradesh',522647,'AdiLakshmi','House wife','Illiterate','RameswaraRo','Business',500000,'7386766574','8099307634','madhuinfo99@gmail.com','2013-09-15','2015-09-21 03:37:43',13,1,'Illiterate',0,1),(42,'Madhu','Nali',1,'1999-11-16','male','AB+ve','hindu','OBC','Yadava','Telugu','motinagar','Hyderabad','Telangana',500018,'ABM Compound','Vinukonda','AndhraPradesh',522647,'AdiLakshmi','House wife','Illiturate','RameswaraRo','Business',500000,'7386766574','8099307634','madhuinfo991@gmail.com','2013-09-15','2015-09-21 03:37:43',13,1,'Illiterate',0,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(50) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `subject_index_name` (`subject_name`,`class_id`),
  UNIQUE KEY `UK_p9bvhfmhdbi6avcg09ohlki9t` (`subject_name`,`class_id`),
  KEY `subject_foreignKey_idx` (`class_id`),
  CONSTRAINT `subject_foreignKey` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (20,'English',10),(14,'English',11),(8,'English',12),(2,'English',13),(21,'Hindi',10),(15,'Hindi',11),(9,'Hindi',12),(3,'Hindi',13),(22,'Maths',10),(16,'Maths',11),(10,'Maths',12),(4,'Maths',13),(23,'Scince',10),(17,'Scince',11),(11,'Scince',12),(5,'Scince',13),(24,'Social',10),(18,'Social',11),(12,'Social',12),(6,'Social',13),(19,'Telugu',10),(13,'Telugu',11),(7,'Telugu',12),(1,'Telugu',13);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(45) DEFAULT NULL,
  `qualification` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `experience` float DEFAULT NULL,
  `photo` varchar(45) DEFAULT NULL,
  `phone1` varchar(45) DEFAULT NULL,
  `phone2` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `joining_date` timestamp NULL DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `job_title` varchar(45) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `UK_4l9jjfvsct1dd5aufnurxcvbs` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'Narayana','BSC','narayana@gmail.com',2,NULL,'8099307634',NULL,'Hyderabad','2014-12-31 18:30:00',NULL,NULL,NULL,NULL),(2,'Pullarao','BED','pullarao@gmail.com',1,NULL,'9703713998',NULL,'Hyderabad','2013-12-31 18:30:00',NULL,NULL,NULL,NULL),(3,'Prasadarao','BSC','prasad@gmail.com',10,NULL,'7386766574',NULL,'Hyderabad','2004-12-31 18:30:00',NULL,NULL,NULL,NULL),(4,'Shivaprasad','BA','Shiva@gmail.com',8,NULL,'0123456789',NULL,'Hyderabad','2006-12-31 18:30:00',NULL,NULL,NULL,NULL),(5,'Lalitha Kumari','BCOM','lalitha@gmail.com',9,NULL,'1234567890',NULL,'Hyderabad','2006-12-31 18:30:00',NULL,NULL,NULL,NULL),(10,'Madhu','MCA','madhuinfo99@gmail.com',5,'C://jboss-7.1.1.Final/images/download.jpg','9703713997','8099307634','Vinukonda','2015-09-08 18:30:00',1,'male','S.R Teacher','2015-09-05 05:36:07');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `subj_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `start_time` varchar(45) DEFAULT NULL,
  `day_id` int(11) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `timetable_foreignKey_idx` (`class_id`),
  KEY `timetable_foreignKey1_idx` (`section_id`),
  KEY `timetable_foreignKey2_idx` (`subj_id`),
  KEY `timetable_foreignKey3_idx` (`teacher_id`),
  KEY `timetable_foreignKey4_idx` (`day_id`),
  CONSTRAINT `timetable_foreignKey` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `timetable_foreignKey1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `timetable_foreignKey2` FOREIGN KEY (`subj_id`) REFERENCES `subject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `timetable_foreignKey3` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `timetable_foreignKey34` FOREIGN KEY (`day_id`) REFERENCES `days` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES (1,13,1,1,1,'09:00 AM to 10:00 AM',1,NULL),(2,13,1,2,2,'10:00 AM to 11:00 AM',1,NULL),(3,13,1,3,3,'11:00 AM to 12:00PM',1,NULL),(4,13,1,4,4,'1:00 PM to 2:00 PM',1,NULL),(5,13,1,5,5,'3:00 PM to 4:00PM',1,NULL),(6,13,1,6,5,'4:00 PM to 5:00PM',1,NULL),(7,13,1,1,1,'09:00 AM to 10:00 AM',2,NULL),(8,13,1,2,2,'10:00 AM to 11:00 AM',2,NULL),(9,13,1,3,3,'11:00 AM to 12:00PM',2,NULL),(10,13,1,4,4,'1:00 PM to 2:00 PM',2,NULL),(11,13,1,5,5,'3:00 PM to 4:00PM',2,NULL),(12,13,1,6,5,'4:00 PM to 5:00PM',2,NULL),(13,13,1,1,1,'09:00 AM to 10:00 AM',3,NULL),(14,13,1,2,2,'10:00 AM to 11:00 AM',3,NULL),(15,13,1,3,3,'11:00 AM to 12:00PM',3,NULL),(16,13,1,4,4,'1:00 PM to 2:00 PM',3,NULL),(17,13,1,5,5,'3:00 PM to 4:00PM',3,NULL),(18,13,1,6,5,'4:00 PM to 5:00PM',3,NULL),(19,13,1,1,1,'09:00 AM to 10:00 AM',4,NULL),(20,13,1,2,2,'10:00 AM to 11:00 AM',4,NULL),(21,13,1,3,3,'11:00 AM to 12:00PM',4,NULL),(22,13,1,4,4,'1:00 PM to 2:00 PM',4,NULL),(23,13,1,5,5,'3:00 PM to 4:00PM',4,NULL),(24,13,1,6,5,'4:00 PM to 5:00PM',4,NULL),(25,13,1,1,1,'09:00 AM to 10:00 AM',5,NULL),(26,13,1,2,2,'10:00 AM to 11:00 AM',5,NULL),(27,13,1,3,3,'11:00 AM to 12:00PM',5,NULL),(28,13,1,4,4,'1:00 PM to 2:00 PM',5,NULL),(29,13,1,5,5,'3:00 PM to 4:00PM',5,NULL),(30,13,1,6,5,'4:00 PM to 5:00PM',5,NULL),(31,13,1,1,1,'09:00 AM to 10:00 AM',6,NULL),(32,13,1,2,2,'10:00 AM to 11:00 AM',6,NULL),(33,13,1,3,3,'11:00 AM to 12:00PM',6,NULL),(34,13,1,4,4,'1:00 PM to 2:00 PM',6,NULL),(35,13,1,5,5,'3:00 PM to 4:00PM',6,NULL),(36,13,1,6,5,'4:00 PM to 5:00PM',6,NULL),(73,12,6,1,1,'09:00 AM',1,'10:00 AM'),(74,12,6,2,2,'10:00 AM',1,'11:00 AM'),(75,12,6,3,3,'11:00 AM',1,'12:00PM'),(76,12,6,4,4,'1:00 PM',1,'2:00 PM'),(77,12,6,5,5,'3:00 PM',1,'4:00 PM'),(78,12,6,6,5,'4:00 PM',1,'5:00PM'),(79,12,6,1,1,'09:00 AM',2,'10:00 AM'),(80,12,6,2,2,'10:00 AM',2,'11:00 AM'),(81,12,6,3,3,'11:00 AM',2,'12:00PM'),(82,12,6,4,4,'1:00 PM',2,'2:00 PM'),(83,12,6,5,5,'3:00 PM',2,'4:00 PM'),(84,12,6,6,5,'4:00 PM',2,'5:00PM'),(85,12,6,1,1,'09:00 AM',3,'10:00 AM'),(86,12,6,2,2,'10:00 AM',3,'11:00 AM'),(87,12,6,3,3,'11:00 AM',3,'12:00PM'),(88,12,6,4,4,'1:00 PM',3,'2:00 PM'),(89,12,6,5,5,'3:00 PM',3,'4:00 PM'),(90,12,6,6,5,'4:00 PM',3,'5:00PM'),(91,12,6,1,1,'09:00 AM',4,'10:00 AM'),(92,12,6,2,2,'10:00 AM',4,'11:00 AM'),(93,12,6,3,3,'11:00 AM',4,'12:00PM'),(94,12,6,4,4,'1:00 PM',4,'2:00 PM'),(95,12,6,5,5,'3:00 PM',4,'4:00 PM'),(96,12,6,6,5,'4:00 PM',4,'5:00PM'),(97,12,6,1,1,'09:00 AM',5,'10:00 AM'),(98,12,6,2,2,'10:00 AM',5,'11:00 AM'),(99,12,6,3,3,'11:00 AM',5,'12:00PM'),(100,12,6,4,4,'1:00 PM',5,'2:00 PM'),(101,12,6,5,5,'3:00 PM',5,'4:00 PM'),(102,12,6,6,5,'4:00 PM',5,'5:00PM'),(103,12,6,1,1,'09:00 AM',6,'10:00 AM'),(104,12,6,2,2,'10:00 AM',6,'11:00 AM'),(105,12,6,3,3,'11:00 AM',6,'12:00PM'),(106,12,6,4,4,'1:00 PM',6,'2:00 PM'),(107,12,6,5,5,'3:00 PM',6,'4:00 PM'),(108,12,6,6,5,'4:00 PM',6,'5:00PM');
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `total_marks`
--

DROP TABLE IF EXISTS `total_marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `total_marks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `examtype_id` int(11) DEFAULT NULL,
  `total_marks_obtained` double DEFAULT NULL,
  `max_marks` double DEFAULT NULL,
  `percentage` double DEFAULT NULL,
  `class_wise_rank` int(11) DEFAULT NULL,
  `roll_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foreignkey_classId_idx` (`class_id`),
  KEY `foreignkey_sectionId_idx` (`section_id`),
  KEY `foreignkey_examtypeId_idx` (`examtype_id`),
  CONSTRAINT `foreignkey_classId` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `foreignkey_examtypeId` FOREIGN KEY (`examtype_id`) REFERENCES `examtype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `foreignkey_sectionId` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `total_marks`
--

LOCK TABLES `total_marks` WRITE;
/*!40000 ALTER TABLE `total_marks` DISABLE KEYS */;
INSERT INTO `total_marks` VALUES (1,13,1,5,504,600,84,1,2),(2,13,1,5,500,600,83.33333333333334,2,1),(3,13,1,5,500,600,83.33333333333334,2,3);
/*!40000 ALTER TABLE `total_marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_report`
--

DROP TABLE IF EXISTS `transaction_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `subj_id` int(11) DEFAULT NULL,
  `topic` text,
  `date` date DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `transaction_report_foreignKey_idx` (`class_id`),
  KEY `transaction_report_foreignKey1_idx` (`section_id`),
  KEY `transaction_report_foreignKey2_idx` (`subj_id`),
  CONSTRAINT `transaction_report_foreignKey` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `transaction_report_foreignKey1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `transaction_report_foreignKey2` FOREIGN KEY (`subj_id`) REFERENCES `subject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_report`
--

LOCK TABLES `transaction_report` WRITE;
/*!40000 ALTER TABLE `transaction_report` DISABLE KEYS */;
INSERT INTO `transaction_report` VALUES (11,13,1,4,'allgebra1','2015-09-21','2015-10-11 17:03:31'),(12,13,1,4,'allgebra2','2015-09-22','2015-10-11 17:03:31'),(13,13,1,4,'allgebra3','2015-09-23','2015-10-11 17:03:31'),(14,13,1,4,'allgebra4','2015-09-24','2015-10-11 17:03:31'),(15,13,1,4,'allgebra4','2015-09-25','2015-10-11 17:03:31'),(16,13,1,5,'science1','2015-09-21','2015-10-11 17:52:32'),(17,13,1,5,'science2','2015-09-22','2015-10-11 17:52:32'),(18,13,1,5,'science3','2015-09-23','2015-10-11 17:52:32'),(19,13,1,5,'science4','2015-09-24','2015-10-11 17:52:32'),(20,13,1,5,'science5','2015-09-25','2015-10-11 17:52:32'),(26,13,1,1,'telugu1','2015-10-01','2015-10-12 16:44:51'),(27,13,1,1,'telugu2','2015-10-02','2015-10-12 16:44:51'),(28,13,1,1,'telugu3','2015-10-03','2015-10-12 16:44:51'),(29,13,1,1,'telugu4','2015-10-04','2015-10-12 16:44:51'),(30,13,1,1,'telugu5','2015-10-05','2015-10-12 16:44:51');
/*!40000 ALTER TABLE `transaction_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `st_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_foreignKey_idx` (`role_id`),
  CONSTRAINT `user_foreignKey` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'madhu','MTIzNDU2','Madhu',NULL,NULL,1,NULL,1,NULL,NULL),(8,'madhu4u.nali@gmail.com','bWFkaHU=','Ramu',40,NULL,2,'2015-09-18 01:49:03',1,13,1),(9,'madhuinfo99@gmail.com','cmFuZG9t','RameswaraRo',41,NULL,2,'2015-09-18 01:49:49',0,12,6),(10,'madhusnali@gmail.com','cmFuZG9t','nali ramu',42,NULL,2,'2015-09-18 01:51:50',1,13,1),(11,'narayana@gmail.com','cmFuZG9t','Narayana',NULL,1,3,'2015-09-18 03:08:59',0,NULL,NULL),(12,'pullarao@gmail.com','cmFuZG9t','Pullarao',NULL,2,3,'2015-09-18 03:10:25',1,NULL,NULL),(13,'prasad@gmail.com','cmFuZG9t','Prasadarao',NULL,3,3,'2015-09-18 03:48:30',1,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weekwise_attendance`
--

DROP TABLE IF EXISTS `weekwise_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weekwise_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` int(11) DEFAULT NULL,
  `month` varchar(45) DEFAULT NULL,
  `week_num` int(11) DEFAULT NULL,
  `date_of_absent` date DEFAULT NULL,
  `total_days_present` int(11) DEFAULT NULL,
  `total_days_absent` int(11) DEFAULT NULL,
  `Num_of_working_days` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `weekwise_attendance_foreignKey_idx` (`st_id`),
  CONSTRAINT `weekwise_attendance_foreignKey` FOREIGN KEY (`st_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weekwise_attendance`
--

LOCK TABLES `weekwise_attendance` WRITE;
/*!40000 ALTER TABLE `weekwise_attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `weekwise_attendance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-27 10:23:50
