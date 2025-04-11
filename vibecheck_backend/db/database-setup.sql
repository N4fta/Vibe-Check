-- Active: 1730728556123@@127.0.0.1@3306@vibecheck
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `username` varchar(20) NOT NULL,
  `hashed_password` char(30) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `account` VALUES (1,'John','johnny','password','2024-11-01 00:00:00'),(2,'Jane Doe','janey','password','2024-11-01 00:00:00');

CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `body` varchar(200) NOT NULL,
  `owner_id` int DEFAULT NULL COMMENT 'If NOT NULL indicates which post this post replied to',
  `created` datetime DEFAULT (now()),
  PRIMARY KEY (`id`)
);
INSERT INTO `post` VALUES (1,1,'title','body',NULL,'2024-11-13 15:46:40');

CREATE TABLE `post_like` (
  `account_id` int NOT NULL,
  `post_id` int NOT NULL,
  `create_time` datetime DEFAULT (now()) COMMENT 'Create Time',
  PRIMARY KEY (`account_id`,`post_id`) COMMENT 'Prevents duplicates'
);
INSERT INTO `post_like` VALUES (1,1,'2024-11-13 16:00:42'),(2,1,'2024-11-13 16:00:49');
