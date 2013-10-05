delimiter $$

CREATE DATABASE `taskmanager` /*!40100 DEFAULT CHARACTER SET latin1 */$$

delimiter $$

CREATE TABLE `task` (
  `idTask` int(11) NOT NULL AUTO_INCREMENT,
  `generatedId` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `category` varchar(25) NOT NULL,
  `timespent` varchar(45) DEFAULT NULL,
  `url` varchar(145) DEFAULT NULL,
  `status` varchar(15) NOT NULL,
  `userEmail` varchar(40) NOT NULL,
  `parentTaskId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTask`),
  UNIQUE KEY `idTask_UNIQUE` (`idTask`),
  UNIQUE KEY `generatedId_UNIQUE` (`generatedId`)
) ENGINE=InnoDB AUTO_INCREMENT=251 DEFAULT CHARSET=latin1$$

