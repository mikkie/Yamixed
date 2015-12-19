ALTER TABLE `yamixed`.`mix` CHANGE COLUMN `description` `description` TEXT NULL DEFAULT NULL  , CHANGE COLUMN `preview_img_url` `preview_img_url` TEXT NULL DEFAULT NULL  , CHANGE COLUMN `title` `title` TEXT NULL DEFAULT NULL  , CHANGE COLUMN `url` `url` TEXT NULL DEFAULT NULL  ;

ALTER TABLE `yamixed`.`comment` CHANGE COLUMN `content` `content` TEXT NULL DEFAULT NULL  ;