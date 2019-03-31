CREATE TABLE `cities` (
	`city_id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL UNIQUE,
	PRIMARY KEY (`city_id`)
);

CREATE TABLE `languages` (
	`lang_id` bigint NOT NULL AUTO_INCREMENT,
	`name_lng` varchar(255) NOT NULL UNIQUE,
	PRIMARY KEY (`lang_id`)
);

CREATE TABLE `localizations` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`lang_id` bigint NOT NULL,
	`city_id` bigint NOT NULL,
	`value` varchar(255),
	PRIMARY KEY (`id`)
);

ALTER TABLE `localizations` ADD CONSTRAINT `localizations_fk0` FOREIGN KEY (`lang_id`) REFERENCES `languages`(`lang_id`);

ALTER TABLE `localizations` ADD CONSTRAINT `localizations_fk1` FOREIGN KEY (`city_id`) REFERENCES `cities`(`city_id`);
