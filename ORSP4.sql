/*
SQLyog Ultimate v9.02 
MySQL - 5.0.24-community-nt : Database - orsp4
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`orsp4` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `orsp4`;

/*Table structure for table `st_college` */

DROP TABLE IF EXISTS `st_college`;

CREATE TABLE `st_college` (
  `ID` bigint(20) NOT NULL,
  `COLLEGE_NAME` varchar(255) default NULL,
  `ADDRESS` varchar(255) default NULL,
  `STATE` varchar(255) default NULL,
  `CITY` varchar(255) default NULL,
  `MOBILE_NO` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`),
  KEY `NAME_IDX` (`COLLEGE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_college` */

insert  into `st_college`(`ID`,`COLLEGE_NAME`,`ADDRESS`,`STATE`,`CITY`,`MOBILE_NO`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'SVITS','indore','MP','INDORE','9678798909',NULL,NULL,NULL,NULL),(2,'GSITS','ghgcgh','UP','AGRA','8999898989',NULL,NULL,NULL,NULL),(3,'RIT','N KJJ','MP','BHOPAL','9678798909',NULL,NULL,NULL,NULL),(4,'LNCT','BKJ','MP','INDORE','9678798909',NULL,NULL,NULL,NULL),(5,'LCA','VJHJ','MP','INDORE','9678798909',NULL,NULL,NULL,NULL),(6,'Acropolice2','JHB','MP','INDORE','9678798909',NULL,NULL,NULL,NULL),(7,'GITS','DGDJFDFJ','MP','INDORE','9678798909',NULL,NULL,NULL,NULL),(8,'RKDF','UYFF','MP','INDORE','9897667676',NULL,NULL,NULL,NULL),(9,'MIT','INDORE','MP','INDORE','9868934274',NULL,NULL,NULL,NULL);

/*Table structure for table `st_course` */

DROP TABLE IF EXISTS `st_course`;

CREATE TABLE `st_course` (
  `id` bigint(200) NOT NULL,
  `COURSE_NAME` varchar(200) default NULL,
  `COURSE_DESCRIPTION` varchar(220) default NULL,
  `COURSE_DURATION` varchar(220) default NULL,
  `CREATED_BY` varchar(222) default NULL,
  `MODIFIED_BY` varchar(222) default NULL,
  `CREATED_DATETIME` timestamp NULL default NULL,
  `MODIFIED_DATETIME` timestamp NULL default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_course` */

insert  into `st_course`(`id`,`COURSE_NAME`,`COURSE_DESCRIPTION`,`COURSE_DURATION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'B.TECH','Bachelor of TECHNOLOGY','4 Years',NULL,NULL,NULL,NULL),(2,'M.TECH','Master of TECHNOLOGY','2 Years',NULL,NULL,NULL,NULL),(3,'BE','Bachelor of Enginnering','4 Years',NULL,NULL,NULL,NULL),(4,'ME','Master of Enginnering','2 Years',NULL,NULL,NULL,NULL),(5,'BSC','BSC','3 Years',NULL,NULL,NULL,NULL),(6,'MSC','MSC','2 Years',NULL,NULL,NULL,NULL),(7,'B.COM','BCOM','3 Years',NULL,NULL,NULL,NULL),(8,'M.COM','MCOM','2 Years',NULL,NULL,NULL,NULL),(9,'BBA','BBA','3 Years',NULL,NULL,NULL,NULL),(10,'MBA','MBA','2 Years',NULL,NULL,NULL,NULL),(11,'BA','BA','3 Years',NULL,NULL,NULL,NULL),(12,'MA','MA','2 Years',NULL,NULL,NULL,NULL);

/*Table structure for table `st_faculty` */

DROP TABLE IF EXISTS `st_faculty`;

CREATE TABLE `st_faculty` (
  `ID` bigint(250) NOT NULL,
  `COLLEGE_ID` bigint(200) default NULL,
  `COLLEGE_NAME` varchar(200) default NULL,
  `COURSE_ID` bigint(20) default NULL,
  `COURSE_NAME` varchar(200) default NULL,
  `FIRST_NAME` varchar(200) default NULL,
  `LAST_NAME` varchar(200) default NULL,
  `JOINING_DATE` date default NULL,
  `QUALIFICATION` varchar(200) default NULL,
  `EMAIL` varchar(200) default NULL,
  `MOBILE_NO` varchar(200) default NULL,
  `DATE_OF_BIRTH` date default NULL,
  `GENDER` varchar(200) default NULL,
  `CREATED_BY` varchar(200) default NULL,
  `MODIFIED_BY` varchar(200) default NULL,
  `CREATED_DATE_TIME` timestamp NULL default NULL,
  `MODIFIED_DATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_st_faculty` (`COURSE_ID`),
  KEY `CK_st_faculty` (`COLLEGE_ID`),
  CONSTRAINT `CK_st_faculty` FOREIGN KEY (`COLLEGE_ID`) REFERENCES `st_college` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_st_faculty` FOREIGN KEY (`COURSE_ID`) REFERENCES `st_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_faculty` */

insert  into `st_faculty`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`COURSE_ID`,`COURSE_NAME`,`FIRST_NAME`,`LAST_NAME`,`JOINING_DATE`,`QUALIFICATION`,`EMAIL`,`MOBILE_NO`,`DATE_OF_BIRTH`,`GENDER`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATE_TIME`,`MODIFIED_DATE_TIME`) values (1,6,'Acropolice2',6,'BCA','Anil','Sharma','2000-06-18','MSC','anil@gmail.com','8982045404','1989-06-25','Male',NULL,NULL,NULL,NULL),(2,2,'GSITS',2,'BE','Sohel','Qureshi','1991-06-18','MTECH','s@gmail.com','8982740403','1992-06-22','Male',NULL,NULL,NULL,NULL),(3,5,'LCA',7,'C','Pankul','Jain','1991-06-18','ME','p@gmail.com','9999999999','1995-06-13','Male',NULL,NULL,NULL,NULL),(4,4,'LKCT',4,'JAVA','Lavesh','Jaiswal','2004-06-18','BE','l@yahoo.com','9999999999','1991-06-18','Male',NULL,NULL,NULL,NULL),(5,9,'MIT',3,'ME','Dheeraj','Jain','1991-06-18','PHD','d@gmail.com','7777777777','1985-06-23','Male',NULL,NULL,NULL,NULL),(6,8,'RKDF',3,'ME','Ritu','Raj','1991-06-18','MSC','rr@gmail.com','8787878787','1992-06-16','Male',NULL,NULL,NULL,NULL),(7,8,'RKDF',2,'BE','Aasif','Khan','2006-06-18','ME','as@gmail.com','9789789879','1992-06-29','Male',NULL,NULL,NULL,NULL),(8,5,'LCA',2,'BE','Promil','Jain','1991-06-18','MTECH','pj@gmail.com','9898787877','1994-06-15','Male',NULL,NULL,NULL,NULL),(9,2,'GSITS',6,'BCA','Ajit','Verma','1991-06-18','MTECH','aj@gmail.com','9789879879','1992-06-29','Male',NULL,NULL,NULL,NULL),(10,7,'GITS',7,'C','Amar','Verma','1991-06-18','MTECH','am@gmail.com','9897978987','1996-06-11','Male',NULL,NULL,NULL,NULL),(12,7,'GITS',4,'JAVA','Harsha','Agrawal','2021-07-17','BE','harshaagrawal1@gmail.com','7898767876','1990-09-14','Female',NULL,NULL,NULL,NULL),(13,8,'RKDF',6,'BCA','nmlof','vnso','2021-07-17','MCA','sadsd@gm.com','8309110112','1989-07-19','Female',NULL,NULL,NULL,NULL);

/*Table structure for table `st_marksheet` */

DROP TABLE IF EXISTS `st_marksheet`;

CREATE TABLE `st_marksheet` (
  `ID` bigint(200) NOT NULL,
  `ROLL_NO` varchar(200) default NULL,
  `STUDENT_ID` bigint(20) default NULL,
  `NAME` varchar(200) default NULL,
  `PHYSICS` bigint(200) default NULL,
  `CHEMISTRY` bigint(200) default NULL,
  `MATHS` bigint(200) default NULL,
  `CREATED_BY` varchar(200) default NULL,
  `MODIFIED_BY` varchar(200) default NULL,
  `CREATED_DATETIME` timestamp NULL default NULL,
  `MODIFIED_DATETIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_st_marksheet` (`STUDENT_ID`),
  CONSTRAINT `FK_st_marksheet` FOREIGN KEY (`STUDENT_ID`) REFERENCES `st_student` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_marksheet` */

insert  into `st_marksheet`(`ID`,`ROLL_NO`,`STUDENT_ID`,`NAME`,`PHYSICS`,`CHEMISTRY`,`MATHS`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'0817cs091063',6,'Pankaj Sahu',98,99,97,NULL,NULL,NULL,NULL),(2,'0817cs091064',2,'Manish Sharma',98,97,90,NULL,NULL,NULL,NULL),(3,'0817cs091062',7,'RAM GUPTA',98,93,94,NULL,NULL,NULL,NULL),(5,'0817cs091067',14,'abhi gyanganga',95,97,96,NULL,NULL,NULL,NULL),(7,'0817cs091072',8,'shyam gupta',99,99,99,NULL,NULL,NULL,NULL),(8,'0817cs091071',4,'raju mali',89,93,90,NULL,NULL,NULL,NULL),(9,'0817cs091076',11,'pankaj lnct',89,90,91,NULL,NULL,NULL,NULL),(10,'0817cs091070',9,'mohan lnct',98,96,99,NULL,NULL,NULL,NULL),(11,'0817cs091069',12,'kamlesh gyanganga',99,99,99,NULL,NULL,NULL,NULL),(12,'0817cs091082',16,'rahul gyanganga',98,97,99,NULL,NULL,NULL,NULL),(13,'0817cs0956',12,'kamleshgyanganga',10,10,10,NULL,NULL,NULL,NULL);

/*Table structure for table `st_role` */

DROP TABLE IF EXISTS `st_role`;

CREATE TABLE `st_role` (
  `ID` bigint(200) NOT NULL,
  `ROLE_NAME` varchar(255) default NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` timestamp NULL default NULL,
  `MODIFIED_DATETIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`),
  KEY `NAME_IDX` (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_role` */

insert  into `st_role`(`ID`,`ROLE_NAME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'Admin','Administrator Role',NULL,NULL,NULL,NULL),(2,'Staff','Staff Role',NULL,NULL,NULL,NULL),(3,'Student','Student Role',NULL,NULL,NULL,NULL),(4,'Guest','Guest Role',NULL,NULL,NULL,NULL),(15,'Teacher','Teachers Role',NULL,NULL,NULL,NULL),(16,'Peon','Peon Role',NULL,NULL,NULL,NULL);

/*Table structure for table `st_student` */

DROP TABLE IF EXISTS `st_student`;

CREATE TABLE `st_student` (
  `ID` bigint(200) NOT NULL,
  `COLLEGE_ID` bigint(20) default NULL,
  `COLLEGE_NAME` varchar(255) default NULL,
  `FIRST_NAME` varchar(255) default NULL,
  `LAST_NAME` varchar(255) default NULL,
  `DATE_OF_BIRTH` date default NULL,
  `MOBILE_NO` varchar(255) default NULL,
  `EMAIL` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_st_student` (`COLLEGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_student` */

insert  into `st_student`(`ID`,`COLLEGE_ID`,`COLLEGE_NAME`,`FIRST_NAME`,`LAST_NAME`,`DATE_OF_BIRTH`,`MOBILE_NO`,`EMAIL`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,1,'SVITS','Rahul','Sahu','2000-07-04','9999999999','ram@gmail.com',NULL,NULL,NULL,NULL),(2,9,'MIT','Manish','Sharma','2014-07-18','9999999999','manish@gmail.com',NULL,NULL,NULL,NULL),(3,5,'LCA','Sul','kh','1996-06-03','9999999999','ashish@nehra.com',NULL,NULL,NULL,NULL),(4,12,'abc','raju','mali','1985-12-11','8234567890','b@gmail.com',NULL,NULL,NULL,NULL),(5,1,'SVITS','rakesh','varma12','2014-08-20','7868787878','ramkumar_choudhary1@yahoo.com',NULL,NULL,NULL,NULL),(6,8,'RKDF','Pankaj','Sahu','1986-11-07','9827568186','sahu.pankaj07@gmail.com',NULL,NULL,NULL,NULL),(7,4,'LNCT','RAM','GUPTA','2000-09-17','9876543210','ramgupta@gmail.com',NULL,NULL,NULL,NULL),(8,4,'LNCT','shyam','gupta','1999-09-02','987654332','shyam@gmail.com',NULL,NULL,NULL,NULL),(9,4,'LNCT','mohan','lnct','2000-09-16','9876543210','mohan@gmail.com',NULL,NULL,NULL,NULL),(10,4,'LNCT','hari','lnct','2014-09-23','9876543210','hari@gmail.com',NULL,NULL,NULL,NULL),(11,4,'LNCT','pankaj','lnct','2014-09-17','9876543210','pankaj@gmail.com',NULL,NULL,NULL,NULL),(12,2,'GSITS','kamlesh','gyanganga','2000-09-16','9876543210','kalesh@gmail.com',NULL,NULL,NULL,NULL),(13,2,'GSITS','hemant','gyanganga','1998-09-23','9876543210','hemant@gmail.com',NULL,NULL,NULL,NULL),(14,2,'GSITS','abhi','gyanganga','1996-09-15','9876543210','abhi@gmail.com',NULL,NULL,NULL,NULL),(15,2,'GSITS','neel','gyanganga','1995-09-15','9876543210','neel@gmail.com',NULL,NULL,NULL,NULL),(16,2,'GSITS','rahul','gyanganga','1995-09-04','9876543210','rahul@gmail.com',NULL,NULL,NULL,NULL),(18,2,'GSITS','fjnmkfw','hkghkfsh','1987-07-07','8090000000','sadsd@gm.com',NULL,NULL,NULL,NULL),(20,5,'LCA','abc','hkghkfsh','1989-07-18','8090000000','sadsd@gm.com',NULL,NULL,NULL,NULL),(21,8,'RKDF','sdgds','gdg','1984-07-11','8839291960','ansh@gmail.com',NULL,NULL,NULL,NULL),(24,5,'LCA','fjnmkfw','Naina','1989-07-18','8090000000','ihvkshv@gdjl.com',NULL,NULL,NULL,NULL),(25,6,'Acropolice2','Ayaansh','Naina','1990-07-27','8765456755','ayaanshnaina@gmail.com',NULL,NULL,NULL,NULL);

/*Table structure for table `st_subject` */

DROP TABLE IF EXISTS `st_subject`;

CREATE TABLE `st_subject` (
  `ID` bigint(200) NOT NULL,
  `SUBJECT_NAME` varchar(200) default NULL,
  `DESCRIPTION` varchar(200) default NULL,
  `COURSE_NAME` varchar(255) default NULL,
  `COURSE_ID` bigint(255) default NULL,
  `CREATED_BY` varchar(200) default NULL,
  `MODIFIED_BY` varchar(200) default NULL,
  `CREATED_DATE_TIME` timestamp NULL default NULL,
  `MODIFIED_DATE_TIME` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_st_subject` (`COURSE_ID`),
  CONSTRAINT `FK_st_subject` FOREIGN KEY (`COURSE_ID`) REFERENCES `st_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_subject` */

insert  into `st_subject`(`ID`,`SUBJECT_NAME`,`DESCRIPTION`,`COURSE_NAME`,`COURSE_ID`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATE_TIME`,`MODIFIED_DATE_TIME`) values (1,'Maths','MATHS','B.TECH',1,NULL,NULL,NULL,NULL),(2,'Chemistry','CHEM','M.TECH',2,'er.anshulnaina@gmail.com','er.anshulnaina@gmail.com','2021-07-28 21:49:09','2021-07-28 21:49:09'),(3,'Physics','PHYSICS','BE',3,NULL,NULL,NULL,NULL),(4,'Computer','COMP','ME',4,NULL,NULL,NULL,NULL),(5,'History','HISTORY','BE',5,NULL,NULL,NULL,NULL),(6,'Mechannics','MECH','BSC',6,NULL,NULL,NULL,NULL),(7,'Accounts','ACC','MSC',7,NULL,NULL,NULL,NULL);

/*Table structure for table `st_timetable` */

DROP TABLE IF EXISTS `st_timetable`;

CREATE TABLE `st_timetable` (
  `ID` bigint(200) NOT NULL,
  `COURSE_ID` bigint(200) default NULL,
  `COURSE_NAME` varchar(200) default NULL,
  `SUBJECT_ID` bigint(200) default NULL,
  `SUBJECT_NAME` varchar(200) default NULL,
  `SEMESTER` varchar(200) default NULL,
  `EXAM_DATE` date default NULL,
  `EXAM_TIME` varchar(200) default NULL,
  `DESCRIPTION` varchar(200) default NULL,
  `CREATED_BY` varchar(200) default NULL,
  `MODIFIED_BY` varchar(200) default NULL,
  `CREATED_DATE_TIME` datetime default NULL,
  `MODIFIED_DATE_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_st_timetable` (`COURSE_ID`),
  KEY `FK_st_timetabl` (`SUBJECT_ID`),
  CONSTRAINT `FK_st_timetabl` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `st_subject` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_st_timetable` FOREIGN KEY (`COURSE_ID`) REFERENCES `st_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_timetable` */

insert  into `st_timetable`(`ID`,`COURSE_ID`,`COURSE_NAME`,`SUBJECT_ID`,`SUBJECT_NAME`,`SEMESTER`,`EXAM_DATE`,`EXAM_TIME`,`DESCRIPTION`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATE_TIME`,`MODIFIED_DATE_TIME`) values (1,2,'BE',5,'.Net','3','2021-08-14','09:00AM-12:00PM','.NET','er.anshulnaina@gmail.com','er.anshulnaina@gmail.com','2021-08-10 15:00:12','2021-08-10 15:00:12'),(2,3,'ME',6,'abc','2','2021-08-21','10:00AM-01:00PM','abc','er.anshulnaina@gmail.com','er.anshulnaina@gmail.com','2021-08-10 15:00:35','2021-08-10 15:00:35'),(3,9,'Hindi',1,'HINDI','1','2021-08-21','07:00AM-10:00AM','HINDI','er.anshulnaina@gmail.com','er.anshulnaina@gmail.com','2021-08-10 15:01:08','2021-08-10 15:01:08');

/*Table structure for table `st_user` */

DROP TABLE IF EXISTS `st_user`;

CREATE TABLE `st_user` (
  `ID` bigint(200) NOT NULL,
  `FIRST_NAME` varchar(255) default NULL,
  `LAST_NAME` varchar(255) default NULL,
  `EMAIL` varchar(255) default NULL,
  `PASSWORD` varchar(255) default NULL,
  `DOB` date default NULL,
  `MOBILE_NO` varchar(255) default NULL,
  `ROLE_ID` bigint(20) default NULL,
  `UNSUCCESSFUL_LOGIN` int(11) default NULL,
  `GENDER` varchar(255) default NULL,
  `LAST_LOGIN` datetime default NULL,
  `USER_LOCK` varchar(255) default NULL,
  `REGISTERED_IP` varchar(255) default NULL,
  `LAST_LOGIN_IP` varchar(255) default NULL,
  `CREATED_BY` varchar(255) default NULL,
  `MODIFIED_BY` varchar(255) default NULL,
  `CREATED_DATETIME` datetime default NULL,
  `MODIFIED_DATETIME` datetime default NULL,
  PRIMARY KEY  (`ID`),
  KEY `fk_ROLE_ID_idx` (`ROLE_ID`),
  KEY `MOBILE_NO_IDX` (`MOBILE_NO`),
  KEY `FIRST_LAST_NAME_IDX` (`FIRST_NAME`,`LAST_NAME`),
  CONSTRAINT `fk_ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `st_role` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `st_user` */

insert  into `st_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`EMAIL`,`PASSWORD`,`DOB`,`MOBILE_NO`,`ROLE_ID`,`UNSUCCESSFUL_LOGIN`,`GENDER`,`LAST_LOGIN`,`USER_LOCK`,`REGISTERED_IP`,`LAST_LOGIN_IP`,`CREATED_BY`,`MODIFIED_BY`,`CREATED_DATETIME`,`MODIFIED_DATETIME`) values (1,'Pankul','Agarwal','agarwalpankul28@gmail.com','pass1234','1991-06-28','9999999999',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'vipin','Chandore','vipin.chandore1452@gmail.com','sohel22','1991-08-08','9165254357',2,0,'Male',NULL,NULL,NULL,NULL,'er.anshulnaina@gmail.com','er.anshulnaina@gmail.com','2021-07-20 14:56:18','2021-07-20 14:56:18'),(3,'Cmclimited','Pvt','ranjit210@gmail.com',NULL,'1970-01-14','9456936545',15,0,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'ranjit','choudhary','ranjitchoudhary210@gmail.com','rrjh','2014-08-20','1234567890',2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'Sunil','Sahu','sunil.sahu@nenosystems.com',NULL,'2014-09-01','9827360504',2,0,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'mohammed','imran','imranmohammed875@gmail.com','120891','1991-08-08','9981095676',2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'shubham','tiwari','shubhamtiwari@gmail.com',NULL,'2014-07-27','9090909220',2,0,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'harsh','dubey','hdubey29@gmail.com','124','1993-07-29',NULL,2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'superadmin','superadmin','superadmin@gmail.com','pass1234','2000-09-24','9876543210',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'jay','jay','jay@gmail.com','jay1234','2014-09-03','9876543210',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'dk','pandey','dk@gmail.com','pass1234','1983-09-20','9876543210',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,'ashish','gupta','ashishgupta@gmail.com','123','2014-09-30',NULL,2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,'Ayush','Patidar','ayush.patidar1@gmail.com','9826754940','1990-11-18',NULL,2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,'Aameer','Khan','a@gmail.com','1234123','1986-05-21',NULL,2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,'Anil','Verma','anil@yahoo.com','asd123','1998-05-31','9999999999',2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,'Pankuk','Jain','p@gmail.com','12345678','1996-05-14','9999999999',2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,'Amit','Sharma','amit@gmail.com','1234asd','1998-05-12','9999999999',4,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,'Vishal','Khanna','vishal@yahoo.com','123456','1996-05-14','8982740403',4,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'Sohel','Khan','Ersohel@gmail.com','pass12345','1992-03-22','8982740403',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'Lawesh','Jain','lawesh@gmail.com','pass1234','1995-05-29','9999999999',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'Vipin','Verma','vipin@gmail.com','123456','1987-05-12',NULL,2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'Ajit','Sharma','1234@1234.com','123456','1998-05-26','1234567890',2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'Abhay','Jain','abhay@gmail.com','1234567','1987-05-05','7811111111',1,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'Ahana','Agrawal','aaa@ggg.sss','62718191','1998-05-20','7811111111',3,0,'Female',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'Raj','Verma','raj@gmail.com','raj1234','1984-05-02',NULL,2,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'Adas','Assa','adn@gmail.com','asdfgh','1979-05-01',NULL,2,NULL,'Female',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'PRiyanka','Patil','priyankapatil2128@gmail.com','1234567','1992-11-21',NULL,2,NULL,'Female',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'Anushka','Sharma','anushka@gmail.com','anushka1234','1996-05-22','9999999999',2,NULL,'Female',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,'Salman','Khan','Ersohel22@gmail.com','pass123456','1988-06-08',NULL,4,NULL,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,'Anshul','Naina','er.anshulnaina@gmail.com','naina','1990-04-18','8839291960',1,0,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(32,'Abc','Naina','anshnainaji@gmail.com','12345678','1989-07-18','8090000000',3,0,'Female',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,'Fjnmkfw','Hkghkfsh','ansh@gmail.com','naina123','1984-07-09','8092820011',2,0,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,'Harsha','Agrawal','harshaagrawal@gmail.com','harsha','1990-09-14','7987481059',1,0,'Female',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,'Shrey','Agrawal','shreyagrawal@gmail.com','shreya','1992-07-16','8878014250',2,0,'Male',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,'Abc','Def','sadsd@gm.com','12345678','1981-07-15','8309110112',3,0,'Male',NULL,NULL,NULL,NULL,'er.anshulnaina@gmail.com','er.anshulnaina@gmail.com','2021-07-18 12:15:13','2021-07-18 12:15:13');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
