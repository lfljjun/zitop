#
# Structure for the `role` table : 
#

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `preinstall` bit(1) NOT NULL,
  `showed` bit(1) NOT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#
# Structure for the `security_resource` table : 
#

CREATE TABLE `security_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `preinstall` bit(1) NOT NULL,
  `showed` bit(1) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#
# Structure for the `role_security_resource` table : 
#

CREATE TABLE `role_security_resource` (
  `role_id` bigint(20) NOT NULL,
  `security_resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`security_resource_id`),
  KEY `FK9F321EA498AE14C` (`role_id`),
  KEY `FK9F321EA4288C402D` (`security_resource_id`),
  CONSTRAINT `FK9F321EA4288C402D` FOREIGN KEY (`security_resource_id`) REFERENCES `security_resource` (`id`),
  CONSTRAINT `FK9F321EA498AE14C` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `user` table : 
#

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#
# Structure for the `user_role` table : 
#

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `role` (`id`, `code`, `description`, `enabled`, `name`, `preinstall`, `showed`, `version`) VALUES 
  (1,'ROLE_ADMIN','',1,'admins',0,1,26);
COMMIT;

#
# Data for the `security_resource` table  (LIMIT 0,500)
#

INSERT INTO `security_resource` (`id`, `description`, `enabled`, `name`, `preinstall`, `showed`, `type`, `value`, `version`) VALUES 
  (1,'',1,'adminpages',0,1,'URL','/admin/**',2);
COMMIT;

#
# Data for the `role_security_resource` table  (LIMIT 0,500)
#

INSERT INTO `role_security_resource` (`role_id`, `security_resource_id`) VALUES 
  (1,1);
COMMIT;

#用户初始密码为1
# Data for the `user` table  (LIMIT 0,500)
#

INSERT INTO `user` (`id`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`, `password`, `username`, `version`) VALUES 
  (1,1,1,1,1,'c4ca4238a0b923820dcc509a6f75849b','admin',0);
COMMIT;

#
# Data for the `user_role` table  (LIMIT 0,500)
#

INSERT INTO `user_role` (`id`, `role_id`, `user_id`, `version`) VALUES 
  (1,1,1,0);
COMMIT;