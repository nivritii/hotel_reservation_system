-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: hrs
-- Source Schemata: hrs
-- Created: Mon May 20 11:46:57 2019
-- Workbench Version: 8.0.12
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema hrs
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `hrs` ;
CREATE SCHEMA IF NOT EXISTS `hrs` ;

-- ----------------------------------------------------------------------------
-- Table hrs.customer
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrs`.`customer` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  UNIQUE INDEX `username` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table hrs.room_type
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrs`.`room_type` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(32) NOT NULL,
  `description` VARCHAR(256) NULL DEFAULT NULL,
  `image` VARCHAR(256) NULL DEFAULT NULL,
  `quantity` INT(10) UNSIGNED NULL DEFAULT '0',
  `price` DECIMAL(12,2) UNSIGNED NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------------------------------------------------------
-- Table hrs.reservation
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrs`.`reservation` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_type_id` INT(10) UNSIGNED NOT NULL,
  `customer_id` INT(10) UNSIGNED NOT NULL,
  `quantity` INT(10) UNSIGNED NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `cancelled` TINYINT(1) NOT NULL DEFAULT '0',
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `room_type_id` (`room_type_id` ASC),
  INDEX `customer_id` (`customer_id` ASC),
  CONSTRAINT `reservation_ibfk_1`
    FOREIGN KEY (`room_type_id`)
    REFERENCES `hrs`.`room_type` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `reservation_ibfk_2`
    FOREIGN KEY (`customer_id`)
    REFERENCES `hrs`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
