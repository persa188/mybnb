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

-- -----------------------------------------------------
-- Schema mybnb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mybnb` DEFAULT CHARACTER SET utf8 ;
USE `mybnb` ;

-- -----------------------------------------------------
-- Table `mybnb`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`address` (
  `long` FLOAT NOT NULL,
  `lat` FLOAT NOT NULL,
  `addr` VARCHAR(255) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `pcode` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`long`, `lat`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`ammenities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`ammenities` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`available`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`available` (
  `lid` INT(11) NOT NULL,
  `cid` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`calendar` (
  `id` INT(11) NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`creditcard`
-- -----------------------------------------------------
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
-- Table `mybnb`.`gives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`gives` (
  `rid` INT(11) NOT NULL,
  `fid` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`has`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`has` (
  `rid` INT(11) NOT NULL,
  `cardno` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`have`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`have` (
  `lid` INT(11) NOT NULL,
  `fid` INT(11) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`host`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`host` (
  `rid` INT(11) NOT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`hosts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`hosts` (
  `rid` INT(11) NOT NULL,
  `lid` INT(11) NOT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`listing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`listing` (
  `id` INT(11) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `charcteristics` VARCHAR(255) NOT NULL,
  `rentalPrice` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`lives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`lives` (
  `rid` INT(11) NOT NULL,
  `lat` FLOAT NOT NULL,
  `long` FLOAT NOT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`located`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`located` (
  `lid` INT(11) NOT NULL,
  `long` FLOAT NULL DEFAULT NULL,
  `lat` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`offers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`offers` (
  `id` INT(11) NOT NULL,
  `aid` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`receives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`receives` (
  `rid` INT(11) NOT NULL,
  `fid` INT(11) NOT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`renter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`renter` (
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `dob` VARCHAR(10) NOT NULL,
  `occupation` VARCHAR(45) NOT NULL,
  `sin` INT(11) NOT NULL,
  `age` INT(11) NOT NULL,
  PRIMARY KEY (`sin`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mybnb`.`rents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mybnb`.`rents` (
  `rid` INT(11) NOT NULL,
  `lid` INT(11) NOT NULL,
  PRIMARY KEY (`rid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `mybnb`;

DELIMITER $$
USE `mybnb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `mybnb`.`renter_BEFORE_INSERT`
BEFORE INSERT ON `mybnb`.`renter`
FOR EACH ROW
BEGIN
DECLARE msg varchar(255);
IF NEW.age < 18 THEN
SET msg = 'Constraints violated!';
SIGNAL sqlstate '45000' set message_text = msg;
END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
