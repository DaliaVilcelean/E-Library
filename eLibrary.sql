
unlock tables;
CREATE SCHEMA IF NOT EXISTS `eLibrary` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `eLibrary` ;


create table if not exists `eLibrary`.`book`(
CALLNO varchar(10) NOT NULL,
NAME varchar(45) DEFAULT NULL,
AUTHOR varchar(45) DEFAULT NULL,
PUBLISHER varchar(45) DEFAULT NULL,
QUANTITY int(11) ,
BORROWED int(11),
PRIMARY KEY(CALLNO)
)ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;


create table if not exists `eLibrary`.`user`
(ID int(11) NOT NULL AUTO_INCREMENT,
NAME varchar(45) DEFAULT NULL,
PASSWORD varchar(45) DEFAULT NULL,
EMAIL varchar(45) DEFAULT NULL,
MOBILE INT(10),
primary key(ID)
)ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;


create table if not exists `eLibrary`.`borrowedBook`
(CALLNO varchar(10) NOT NULL,
STUDENTID int(11) NOT NULL,
STUDENTNAME varchar(45) default null,
MOBILE varchar(10) default null,
ISSUEDATE date default null,
RETURNSTATUS varchar(45) default null,
  CONSTRAINT `CALLNO`
    FOREIGN KEY (`CALLNO`)
    REFERENCES `book` (`CALLNO`),
      CONSTRAINT `STUDENTID`
    FOREIGN KEY (`STUDENTID`)
    REFERENCES `user` (`ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=latin1;




