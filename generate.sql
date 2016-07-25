-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Boats`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Boats` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Boats` (
  `bid` INT(50) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `color` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`bid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Sailors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Sailors` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Sailors` (
  `sid` INT(50) NOT NULL AUTO_INCREMENT,
  `sname` VARCHAR(100) NOT NULL,
  `firstName` VARCHAR(100) NULL DEFAULT NULL,
  `rating` INT(11) NOT NULL,
  `age` INT(11) NOT NULL,
  PRIMARY KEY (`sid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `mydb`;

DELIMITER $$

USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`sailorsIns` $$
USE `mydb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `mydb`.`sailorsIns`
BEFORE INSERT ON `mydb`.`Sailors`
FOR EACH ROW
BEGIN
DECLARE msg varchar(255);
IF NEW.rating < 1 OR NEW.rating > 5 OR NEW.age < 18 THEN
SET msg = 'Constraints violated!';
SIGNAL sqlstate '45000' set message_text = msg;
END IF;
END$$


USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`sailorsUpd` $$
USE `mydb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `mydb`.`sailorsUpd`
BEFORE UPDATE ON `mydb`.`Sailors`
FOR EACH ROW
BEGIN
DECLARE msg varchar(255);
IF NEW.rating < 1 OR NEW.rating > 5 OR NEW.age < 18 THEN
SET msg = 'Constraints violated!';
SIGNAL sqlstate '45001' set message_text = msg;
END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
