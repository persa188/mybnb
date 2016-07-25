-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mybnb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mybnb` ;

-- -----------------------------------------------------
-- Schema mybnb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mybnb` DEFAULT CHARACTER SET utf8 ;
USE `mybnb` ;

-- -----------------------------------------------------
-- Table `mybnb`.`accounts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`accounts` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`accounts` (
  `user` VARCHAR(45) NOT NULL,
  `pwd` VARCHAR(45) NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `dob` VARCHAR(45) NOT NULL,
  `occ` VARCHAR(45) NOT NULL,
  `sin` INT(11) NOT NULL,
  PRIMARY KEY (`user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`address` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`address` (
  `lat` DOUBLE NOT NULL,
  `lng` DOUBLE NOT NULL,
  `addr` VARCHAR(255) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `pcode` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`lat`, `lng`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`ammenities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`ammenities` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`ammenities` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`availability`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`availability` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`availability` (
  `lid` INT(11) NOT NULL,
  `sdate` DATE NOT NULL,
  `edate` DATE NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`creditcard`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`creditcard` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`creditcard` (
  `cardno` INT(11) NOT NULL,
  `verno` INT(4) NOT NULL,
  `fullname` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `exp` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`cardno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`feedback` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`feedback` (
  `type` VARCHAR(15) NOT NULL,
  `lid` INT(11) NOT NULL,
  `rating` INT(11) NOT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`type`, `lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`gives`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`gives` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`gives` (
  `user` VARCHAR(45) NOT NULL,
  `fid` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`has`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`has` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`has` (
  `user` VARCHAR(45) NOT NULL,
  `cardno` INT(11) NOT NULL,
  PRIMARY KEY (`user`, `cardno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`have`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`have` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`have` (
  `lid` INT(11) NOT NULL,
  `fid` INT(11) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`host`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`host` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`host` (
  `user` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`hosts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`hosts` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`hosts` (
  `user` VARCHAR(45) NOT NULL,
  `lid` INT(11) NOT NULL,
  PRIMARY KEY (`user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`listing`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`listing` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`listing` (
  `id` INT(11) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `characteristics` VARCHAR(255) NOT NULL,
  `rentalPrice` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`lives`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`lives` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`lives` (
  `user` VARCHAR(45) NOT NULL,
  `lat` DOUBLE NOT NULL,
  `lng` DOUBLE NOT NULL,
  PRIMARY KEY (`user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`located`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`located` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`located` (
  `lid` INT(11) NOT NULL,
  `lng` DOUBLE NOT NULL,
  `lat` DOUBLE NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`offers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`offers` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`offers` (
  `id` INT(11) NOT NULL,
  `aid` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`receives`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`receives` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`receives` (
  `ruser` VARCHAR(45) NOT NULL,
  `fid` INT(11) NOT NULL,
  PRIMARY KEY (`ruser`, `fid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`renter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`renter` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`renter` (
  `user` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`rents`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mybnb`.`rents` ;

CREATE TABLE IF NOT EXISTS `mybnb`.`rents` (
  `ruser` VARCHAR(45) NOT NULL,
  `lid` INT(11) NOT NULL,
  `sdate` DATE NOT NULL,
  `edate` DATE NOT NULL,
  PRIMARY KEY (`ruser`, `lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
