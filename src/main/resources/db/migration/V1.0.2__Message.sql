CREATE TABLE `MESSAGE` (
    `ID` VARCHAR(36) NOT NULL,
    `TENANT_ID` VARCHAR(36) NOT NULL,
    `CREATED_BY` VARCHAR(36) NOT NULL,
    `MESSAGE` VARCHAR(255) NOT NULL,
    `SEND_TO` VARCHAR(100) NOT NULL,
    `STATUS` VARCHAR(100) NOT NULL,
    `SCHEDULED_DATE`  DATETIME NOT NULL,
    `PROCESSED_DATE` DATETIME NULL,
    `CREATED_DATE`  DATETIME NOT NULL,
    `UPDATED_DATE` DATETIME NULL,
    PRIMARY KEY (`ID`),
    CONSTRAINT `FK_MESSAGE_USER` FOREIGN KEY (`TENANT_ID`, `CREATED_BY`) REFERENCES `USER` (`TENANT_ID`, `ID`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;