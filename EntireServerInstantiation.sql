-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema A.R.M.S
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema A.R.M.S
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `A.R.M.S` DEFAULT CHARACTER SET utf8 ;
USE `A.R.M.S` ;

-- -----------------------------------------------------
-- Table `A.R.M.S`.`FloorDepartments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`FloorDepartments` (
  `departmentID` INT NOT NULL,
  `departmentName` VARCHAR(45) NULL,
  `departmentBudget` VARCHAR(45) NULL,
  `departmentManager` VARCHAR(45) NULL,
  PRIMARY KEY (`departmentID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`High Value Products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`High Value Products` (
  `productUUID` INT NOT NULL,
  `productSKU` INT NOT NULL,
  `itemBrand` VARCHAR(45) NOT NULL,
  `itemSubBrand` VARCHAR(45) NULL,
  `itemName` VARCHAR(45) NOT NULL,
  `itemCategory` VARCHAR(45) NOT NULL,
  `itemDepartment` VARCHAR(45) NOT NULL,
  `backroomLocation` VARCHAR(45) NOT NULL,
  `floorLocation` VARCHAR(45) NOT NULL,
  `securityLevel` VARCHAR(45) NULL,
  `managerPing` TINYINT(1) NULL,
  UNIQUE INDEX `productUUID_UNIQUE` (`productUUID` ASC),
  PRIMARY KEY (`productUUID`, `productSKU`, `itemName`),
  INDEX `fk_High Value Products_ProductRoster_idx` (`productSKU` ASC),
  CONSTRAINT `fk_High Value Products_ProductRoster`
    FOREIGN KEY (`productSKU`)
    REFERENCES `A.R.M.S`.`ProductRoster` (`productSKU`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`BackroomLocations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`BackroomLocations` (
  `locationID` INT NOT NULL,
  `coordinates` VARCHAR(45) NOT NULL,
  `departmentID` INT NOT NULL,
  `aisleID` INT NOT NULL,
  PRIMARY KEY (`locationID`, `coordinates`),
  INDEX `fk_BackroomLocations_FloorDepartments1_idx` (`departmentID` ASC),
  INDEX `fk_BackroomLocations_FloorMap1_idx` (`aisleID` ASC),
  CONSTRAINT `fk_BackroomLocations_FloorDepartments1`
    FOREIGN KEY (`departmentID`)
    REFERENCES `A.R.M.S`.`FloorDepartments` (`departmentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BackroomLocations_FloorMap1`
    FOREIGN KEY (`aisleID`)
    REFERENCES `A.R.M.S`.`FloorMap` (`aisleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`FloorMap`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`FloorMap` (
  `aisleID` INT NOT NULL,
  `aisleCapacity` VARCHAR(45) NULL,
  `securityLevel` VARCHAR(45) NULL,
  `departmentID` INT NOT NULL,
  `backroomLocationID` INT NOT NULL,
  PRIMARY KEY (`aisleID`),
  INDEX `fk_FloorMap_FloorDepartments1_idx` (`departmentID` ASC),
  INDEX `fk_FloorMap_BackroomLocations1_idx` (`backroomLocationID` ASC),
  CONSTRAINT `fk_FloorMap_FloorDepartments1`
    FOREIGN KEY (`departmentID`)
    REFERENCES `A.R.M.S`.`FloorDepartments` (`departmentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FloorMap_BackroomLocations1`
    FOREIGN KEY (`backroomLocationID`)
    REFERENCES `A.R.M.S`.`BackroomLocations` (`locationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`AisleProductCount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`AisleProductCount` (
  `aisleID` INT NOT NULL,
  `productSKU` INT NOT NULL,
  `quantityStocked` VARCHAR(45) NULL,
  `quantityActual` VARCHAR(45) NULL,
  INDEX `fk_AisleProductCount_FloorMap1_idx` (`aisleID` ASC),
  PRIMARY KEY (`aisleID`),
  INDEX `fk_AisleProductCount_ProductRoster1_idx` (`productSKU` ASC),
  CONSTRAINT `fk_AisleProductCount_FloorMap1`
    FOREIGN KEY (`aisleID`)
    REFERENCES `A.R.M.S`.`FloorMap` (`aisleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AisleProductCount_ProductRoster1`
    FOREIGN KEY (`productSKU`)
    REFERENCES `A.R.M.S`.`ProductRoster` (`productSKU`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`Product` (
  `shipmentID` INT NOT NULL,
  `storeAuthenticationID` VARCHAR(45) NOT NULL,
  `productSKU` VARCHAR(45) NOT NULL,
  `carrierDeliveryDate` VARCHAR(45) NOT NULL,
  `actualDeliveryDate` VARCHAR(45) NOT NULL,
  `productUUID` INT NOT NULL,
  `shipmentValue` VARCHAR(45) NOT NULL,
  `shipmentLocation` VARCHAR(45) NOT NULL,
  `shipmentOrigin` VARCHAR(45) NOT NULL,
  `vendorContactInfo` VARCHAR(45) NOT NULL,
  `AisleProductCount` INT NOT NULL,
  PRIMARY KEY (`shipmentID`, `storeAuthenticationID`, `productSKU`, `carrierDeliveryDate`, `actualDeliveryDate`),
  UNIQUE INDEX `shipmentID_UNIQUE` (`shipmentID` ASC),
  UNIQUE INDEX `storeAuthenticationID_UNIQUE` (`storeAuthenticationID` ASC),
  INDEX `fk_Product_High Value Products1_idx` (`productUUID` ASC),
  INDEX `fk_Product_AisleProductCount1_idx` (`AisleProductCount` ASC),
  CONSTRAINT `fk_Product_High Value Products1`
    FOREIGN KEY (`productUUID`)
    REFERENCES `A.R.M.S`.`High Value Products` (`productUUID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_AisleProductCount1`
    FOREIGN KEY (`AisleProductCount`)
    REFERENCES `A.R.M.S`.`AisleProductCount` (`aisleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`ProductRoster`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`ProductRoster` (
  `productSKU` INT NOT NULL,
  `departmentID` INT NOT NULL,
  `itemBrand` VARCHAR(45) NOT NULL,
  `itemSubBrand` VARCHAR(45) NULL,
  `itemName` VARCHAR(45) NULL,
  `itemCategory` VARCHAR(45) NOT NULL,
  `itemDepartment` VARCHAR(45) NOT NULL,
  `backroomLocation` VARCHAR(45) NOT NULL,
  `aisleID` INT NOT NULL,
  `lastShipmentID` INT NOT NULL,
  `lastShipmentDate` VARCHAR(45) NULL,
  PRIMARY KEY (`productSKU`),
  UNIQUE INDEX `itemUUID_UNIQUE` (`productSKU` ASC),
  INDEX `fk_ProductRoster_FloorDepartments1_idx` (`departmentID` ASC),
  INDEX `fk_ProductRoster_Product1_idx` (`lastShipmentID` ASC),
  INDEX `fk_ProductRoster_FloorMap1_idx` (`aisleID` ASC),
  CONSTRAINT `fk_ProductRoster_FloorDepartments1`
    FOREIGN KEY (`departmentID`)
    REFERENCES `A.R.M.S`.`FloorDepartments` (`departmentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProductRoster_Product1`
    FOREIGN KEY (`lastShipmentID`)
    REFERENCES `A.R.M.S`.`Product` (`shipmentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProductRoster_FloorMap1`
    FOREIGN KEY (`aisleID`)
    REFERENCES `A.R.M.S`.`FloorMap` (`aisleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`ProductCosts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`ProductCosts` (
  `productSKU` INT NOT NULL,
  `itemName` VARCHAR(45) NOT NULL,
  `itemBrand` VARCHAR(45) NULL,
  `itemSubBrand` VARCHAR(45) NULL,
  `itemCategory` VARCHAR(45) NULL,
  `itemDepartment` VARCHAR(45) NULL,
  `itemRetailPrice` VARCHAR(45) NULL,
  `itemActualPrice` VARCHAR(45) NOT NULL,
  `itemSalePrice` VARCHAR(45) NULL,
  INDEX `fk_ProductCosts_ProductRoster1_idx` (`productSKU` ASC),
  PRIMARY KEY (`productSKU`, `itemName`, `itemActualPrice`),
  CONSTRAINT `fk_ProductCosts_ProductRoster1`
    FOREIGN KEY (`productSKU`)
    REFERENCES `A.R.M.S`.`ProductRoster` (`productSKU`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`Losses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`Losses` (
  `eventID` INT NOT NULL,
  `departmentID` INT NOT NULL,
  `date` DATE NOT NULL,
  `estimatedCost` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`eventID`, `departmentID`, `date`),
  INDEX `fk_Losses_FloorDepartments1_idx` (`departmentID` ASC),
  CONSTRAINT `fk_Losses_FloorDepartments1`
    FOREIGN KEY (`departmentID`)
    REFERENCES `A.R.M.S`.`FloorDepartments` (`departmentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `A.R.M.S`.`ScannerLookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`ScannerLookup` (
  `productID` INT NOT NULL COMMENT '	',
  `productName` VARCHAR(45) NULL,
  `productPrice` DOUBLE NULL,
  `aisleLocation` VARCHAR(45) NULL,
  `inventoryCount` INT NULL,
  PRIMARY KEY (`productID`))
ENGINE = InnoDB;

USE `A.R.M.S` ;

-- -----------------------------------------------------
-- Placeholder table for view `A.R.M.S`.`ProductCount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `A.R.M.S`.`ProductCount` (`productSKU` INT, `quantityActual` INT);

-- -----------------------------------------------------
-- View `A.R.M.S`.`ProductCount`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `A.R.M.S`.`ProductCount`;
USE `A.R.M.S`;
CREATE  OR REPLACE VIEW `ProductCount` AS
    SELECT 
        productSKU, quantityActual
    FROM
        ProductRoster
            NATURAL JOIN
        AisleProductCount
    WHERE
        quantityActual >= 0;
CREATE USER 'basicTeamMember' IDENTIFIED BY '0000';

GRANT SELECT ON TABLE `A.R.M.S`.* TO 'basicTeamMember';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
