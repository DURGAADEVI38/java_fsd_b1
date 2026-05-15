-- MySQL Workbench Forward Engineering


-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`officer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`officer` (
  `id` INT NOT NULL,
  `namel` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`policy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`policy` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `details` VARCHAR(45) NULL,
  `active_ploicy_count` INT NULL,
  `policy_claimed` INT NULL,
  `officer_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`proposal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`proposal` (
  `id` INT NOT NULL,
  `vehicle_type` VARCHAR(45) NULL,
  `policy_status` VARCHAR(45) NULL,
  `payment_status` VARCHAR(45) NULL,
  `policy_id1` INT NOT NULL,
  `officer_id` INT NOT NULL,
  `user_id1` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
