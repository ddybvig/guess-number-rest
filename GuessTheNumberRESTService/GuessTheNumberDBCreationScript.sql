-- drop database if exists guessTheNumber;
-- create database guessTheNumber;

use guessTheNumber;

CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` char(4) NOT NULL,
  `inProgress` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);

CREATE TABLE `guess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guess` char(4) NOT NULL,
  `timeOfGuess` datetime NOT NULL,
  `result` varchar(10) DEFAULT NULL,
  `gameID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gameID` (`gameID`),
  CONSTRAINT `guess_ibfk_1` FOREIGN KEY (`gameID`) REFERENCES `game` (`id`)
)