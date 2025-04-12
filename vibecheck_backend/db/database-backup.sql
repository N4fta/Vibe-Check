-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: vibecheck
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(40) NOT NULL,
  `username` varchar(20) NOT NULL,
  `hashed_password` char(70) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `admin_role` bit(1) NOT NULL DEFAULT b'0',
  `profile_picture` varchar(255) DEFAULT 'user-default-64.png',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='User accounts';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (7,'johnny2@test.tes','notjohn','$2a$10$mTP3UgTwG42pFevPT.9YF.SydRbw2oRaHfg5ZMJyzb9GPmQmqUmRm','1111-11-11 00:00:00',0x00,'user-default-64.png'),(8,'johnny@test.com','johnny','$2a$10$f1kRf2qbWIIodU3FGX0k9ekFa/Z/VmfatDpKIBGzsqh7rrbc8mOuq','2000-11-11 00:00:00',0x00,'user-default-64.png'),(9,'jane@doe.com','janney','$2a$10$XPdfSKR7h1cmu6XU7LoqxuYZjqH6WrH70NlNvuG3Gws0nXUG8eOum','2003-07-13 00:00:00',0x00,NULL),(10,'mariana@gmail.com','mariana','$2a$10$g37caBSeHw1KE9LkpsU2EeE5UQsudkvlojH8OsRqltQk8A93gVGFO','2000-01-11 00:00:00',0x00,NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `body` varchar(200) NOT NULL,
  `owner_id` int DEFAULT NULL COMMENT 'If NOT NULL indicates which post this post replied to',
  `created` datetime DEFAULT (now()),
  `track_id` varchar(255) DEFAULT ' ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,7,'Vibecheck','🌟 Excited to announce the launch of our new app, Vibecheck! 🚀  📱 Download now and explore a world of tech news, reviews, and tutorials.  🔗 [Link to download]  #Vibecheck #AppLaunch #TechNews #In',NULL,'2024-11-13 15:46:40',' '),(2,7,'Second Evil','😈 Mischief managed... for now. 🔥  Just caused a \"minor\" internet outage in the penthouse. Nothing like a little chaos to start the day! 😌  #DevilishDelights #ChaosReigns #MischiefMade',1,'2024-11-13 16:07:33',' '),(3,7,'test test','Is this working?',NULL,'2024-11-13 16:11:53',' '),(4,8,'A new day','🌞 Morning coffee never disappoints! ☕️ Ready to tackle the day. #CoffeeLovers #MorningVibes',NULL,'2024-11-13 16:17:14',' '),(5,8,'Lofi','🌧️ Rainy days call for cozy socks and a good book. 📚 Perfect way to spend the afternoon. #RainyDayReads #CozyVibes',NULL,'2024-11-18 23:03:46',' '),(6,8,'Yum!','🍔 Just tried that new burger joint downtown. 🍟 Verdict: Delicious! 😍 #FoodieLife #BurgerBliss',NULL,'2024-11-18 23:03:53',' '),(7,1,'Tech Upgrade','💻 Finally upgraded my rig with the latest GPU! 🎮 Can\'t wait to see the difference in performance. #GamingLife #TechUpgrade',NULL,'2024-11-18 22:04:50',' '),(8,1,'Hot tip!','💡 Just discovered a game-changing productivity hack. 🌟 My workflow will never be the same! #TechTips #ProductivityHacks',NULL,'2024-11-18 22:05:17',' '),(9,6,'Monday Nov 2024','🌞 Behold, mortals! Today shall be a day of radiant sunshine and clear skies. ☀️ Bask in my glory, for tomorrow may bring the patter of rain. #WeatherWisdom #SunnyDays',NULL,'2024-11-18 23:06:14',' '),(10,5,'Ice Cream Mishap','🍦 Just tried to eat ice cream with chopsticks... 🥢 It was a sticky disaster! 😂 Who needs spoons anyway? #IceCreamFail #SillyMoments',NULL,'2024-11-18 22:07:52',' '),(11,5,'Dog Life 1','🐶 Tried to teach my dog to sing the national anthem... 🎤 He howled, I laughed, and the neighbors thought it was a coyote convention! 😂 #DogLife #SillySymphonies',NULL,'2024-11-18 22:08:27',' '),(12,3,'Hello ...brother','Hows hell?',2,'2024-11-18 23:41:36',' '),(13,1,'test','testeststest',NULL,'2024-12-09 14:26:51',' '),(14,8,'jaaaannnnnne','jaanneee i love youuuu\ni love youu i dooo',NULL,NULL,' '),(15,11,'Yumi','Tried a new restaurant; chicken is 30 euros; delicious',NULL,NULL,' '),(16,14,'music zen ','nmerhvnf,ckxejwr,ktwjg,.kw45jc,kj j k43 bku ,kj4re,kwg ',NULL,NULL,' '),(17,8,'testt','testttt',NULL,NULL,' '),(18,8,'testttt2','testtttt21',NULL,NULL,' '),(19,8,'testttt3','testttt3',NULL,NULL,' '),(20,8,'testtt4','testtt4',NULL,NULL,' '),(21,8,'testtt5','testttt5',NULL,NULL,' '),(22,8,'testtt6','testtt6',NULL,NULL,' '),(23,8,'testtt7','testtt7',NULL,NULL,' '),(24,8,'testttt','testttttt',NULL,NULL,' '),(25,8,'fffire in me','fire that burnssss',NULL,NULL,' '),(26,8,'fffire in me 2','ffffff',NULL,NULL,NULL),(27,8,'fffire in me 3','ffffiiireeee',NULL,NULL,'46gSk82duJtX3TTA182ruG'),(28,8,'when theres blood in the water...','BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN THE WATER //BLOOD IN',NULL,NULL,'2IGMVunIBsBLtEQyoI1Mu7'),(29,8,'blooooood','blod in the water!!',NULL,NULL,'2IGMVunIBsBLtEQyoI1Mu7'),(30,8,'BLOOOddd','in the waterrr!!',NULL,NULL,'0AUyNF6iFxMNQsNx2nhtrw'),(31,8,'test title','test body',NULL,NULL,''),(32,8,'test title','test body',NULL,NULL,''),(33,8,'test title','test body',NULL,NULL,''),(34,8,'resre','ersrers',NULL,NULL,'4NFI5YKWjOL6lWD5VcinH4'),(35,8,'test title','test body',NULL,NULL,''),(36,8,'test title','test body',NULL,NULL,''),(37,8,'test title','test body',NULL,NULL,''),(38,8,'test title','test body',NULL,NULL,''),(39,8,'test title','test body',NULL,NULL,''),(40,8,'test title','test body',NULL,NULL,''),(41,8,'test title','test body',NULL,NULL,''),(42,8,'test title','test body',NULL,NULL,''),(43,8,'test title','test body',NULL,NULL,''),(44,8,'test title','test body',NULL,NULL,''),(45,8,'test title','test body',NULL,NULL,''),(46,8,'test title','test body',NULL,NULL,''),(47,8,'test title','test body',NULL,NULL,''),(48,8,'test title','test body',NULL,NULL,''),(49,8,'test title','test body',NULL,NULL,''),(50,8,'test title','test body',NULL,NULL,''),(51,8,'test title','test body',NULL,NULL,''),(52,8,'test title','test body',NULL,NULL,''),(53,8,'test title','test body',NULL,NULL,''),(54,8,'test title','test body',NULL,NULL,''),(55,8,'test title','test body',NULL,NULL,''),(56,8,'test title','test body',NULL,NULL,''),(57,8,'test title','test body',NULL,NULL,''),(58,8,'test title','test body',NULL,NULL,''),(59,8,'test title','test body',NULL,NULL,''),(60,8,'test title','test body',NULL,NULL,''),(61,8,'test title','test body',NULL,NULL,''),(62,8,'test title','test body',NULL,NULL,''),(63,8,'test title','test body',NULL,NULL,''),(64,8,'test title','test body',NULL,NULL,''),(65,8,'test title','test body',NULL,NULL,''),(66,8,'test title','test body',NULL,NULL,''),(67,8,'test title','test body',NULL,NULL,''),(68,8,'test title','test body',NULL,NULL,''),(69,8,'test title','test body',NULL,NULL,''),(70,8,'test title','test body',NULL,NULL,'');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_like` (
  `account_id` int NOT NULL,
  `post_id` int NOT NULL,
  `create_time` datetime DEFAULT (now()) COMMENT 'Create Time',
  PRIMARY KEY (`account_id`,`post_id`) COMMENT 'Prevents duplicates'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Saves account likes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_like`
--

/*!40000 ALTER TABLE `post_like` DISABLE KEYS */;
INSERT INTO `post_like` VALUES (1,1,'2024-11-13 16:00:42'),(1,3,'2024-12-10 12:10:51'),(1,6,'2024-12-10 11:22:55'),(1,7,'2024-12-11 08:26:10'),(1,13,'2024-12-10 12:11:05'),(2,1,'2024-11-13 16:00:49'),(8,1,'2025-01-05 20:49:02'),(8,3,'2025-01-05 20:52:40'),(8,6,'2025-01-05 20:48:13'),(8,9,'2024-12-20 18:43:37'),(8,14,'2024-12-27 18:56:40'),(8,15,'2025-01-05 20:39:36'),(8,16,'2025-01-05 20:37:31'),(8,21,'2025-01-05 21:11:06'),(8,23,'2025-01-05 21:20:29'),(14,15,'2025-01-05 15:26:21');
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `token` varchar(512) NOT NULL,
  `jwt_token` varchar(512) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expires_at` timestamp NOT NULL,
  `is_revoked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `refresh_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (1,8,'5b56aac0-9f9c-4ff5-97a1-fae8322c9a24',NULL,'2025-01-17 15:22:34','2025-02-16 15:22:34',0),(2,8,'c71fbd15-0cd2-4022-96ff-9eb34bef6b0a',NULL,'2025-01-17 15:23:48','2025-02-16 15:23:48',0),(3,8,'69e19a0f-6216-400d-ac96-e234cbe70ae4',NULL,'2025-01-17 15:26:38','2025-02-16 15:26:38',0),(4,8,'6cea2413-6fdb-4c6d-9815-a84374da9d15',NULL,'2025-01-17 15:38:59','2025-02-16 15:38:59',0),(5,8,'c8bc4fdc-f1fb-4b8e-bfc4-5cd2581ae37e',NULL,'2025-01-17 15:52:46','2025-02-16 15:52:46',0),(6,8,'a8642099-e16f-4b3b-8aaa-93ce08696b7d',NULL,'2025-01-17 16:02:49','2025-02-16 16:02:49',0),(7,8,'ad2d4874-5f84-4281-a70c-32203a85e8cb',NULL,'2025-01-17 15:34:12','2025-02-16 16:29:45',1),(8,8,'ab04a0d6-e4f6-4bc4-a5ae-3d1035709698',NULL,'2025-01-17 16:34:13','2025-02-16 16:34:13',0),(9,8,'8ee9e1f0-be2a-437f-bd96-8af61c06d036',NULL,'2025-01-17 15:36:15','2025-02-16 16:34:13',1),(10,8,'708bb318-8c37-4a46-9fa3-222538a0267b',NULL,'2025-01-17 16:34:13','2025-02-16 16:34:13',0),(11,8,'fe8b2da7-7dac-435d-8cbd-6d980c409e6b',NULL,'2025-01-17 16:34:13','2025-02-16 16:34:13',0),(12,8,'7727bf0e-a33d-4143-bcfc-eeaad3320a57',NULL,'2025-01-17 16:36:16','2025-02-16 16:36:16',0),(13,8,'bcf37827-6a7f-43ec-b129-402e63e45be3',NULL,'2025-01-17 16:36:16','2025-02-16 16:36:16',0),(14,8,'bf5eabbe-4849-478d-94ed-4cacca43dba6',NULL,'2025-01-17 16:36:16','2025-02-16 16:36:16',0),(15,8,'1867c362-5ef2-4fbc-8d74-b26a66248b08',NULL,'2025-01-17 15:38:35','2025-02-16 16:36:16',1),(16,8,'084d2f4f-6ec1-4280-8b8d-461bb714c5ce',NULL,'2025-01-17 16:38:36','2025-02-16 16:38:36',0),(17,8,'b89f0042-90a0-40eb-9a0b-4a5951fabd0d',NULL,'2025-01-17 17:36:09','2025-02-16 16:38:36',1),(18,8,'7c26cfe0-0a31-4d59-931b-406befb36c72',NULL,'2025-01-17 16:38:36','2025-02-16 16:38:36',0),(19,8,'216f522b-a1eb-41b9-87c0-12ccb0e19210',NULL,'2025-01-17 16:38:36','2025-02-16 16:38:36',0),(20,8,'e2bc0d53-28d9-45fa-8d59-f52b553acf60',NULL,'2025-01-17 17:43:56','2025-02-16 18:36:09',1),(21,8,'46e99a66-603a-41f7-9c10-cc529135d78a',NULL,'2025-01-17 18:43:56','2025-02-16 18:43:56',0),(22,8,'6a2aba59-799a-4c17-bedb-47b53d3b074f',NULL,'2025-01-17 17:49:04','2025-02-16 18:43:56',1),(23,8,'2f6537a9-ee4c-4a57-9373-aeadefefb219',NULL,'2025-01-17 18:43:57','2025-02-16 18:43:57',0),(24,8,'ed3d676b-2b5d-4378-b9a5-dd06de5f6a5e',NULL,'2025-01-17 18:43:57','2025-02-16 18:43:57',0),(25,8,'1de0211d-e9aa-4ab9-a625-697eff2fe270',NULL,'2025-01-17 18:10:26','2025-02-16 18:49:04',1),(26,8,'0b68adf6-e64f-4c12-bd13-5b80a55fda6d',NULL,'2025-01-17 19:01:18','2025-02-16 19:01:18',0),(27,8,'a1fe33ff-c475-4d60-b53b-477c716f0518',NULL,'2025-01-17 18:07:58','2025-02-16 19:01:54',1),(28,8,'309ff48c-9c15-4698-b064-144956209f0b',NULL,'2025-01-17 19:07:59','2025-02-16 19:07:59',0),(29,8,'88d0f1e2-38cc-46c6-93ad-7e53fc08239e',NULL,'2025-01-17 19:07:59','2025-02-16 19:07:59',0),(30,8,'951e5a7c-8afe-4ec7-98ff-e8d72181d43f',NULL,'2025-01-17 19:07:59','2025-02-16 19:07:59',0),(31,8,'f17c3400-4323-490a-ae0d-4e597730e5ae',NULL,'2025-01-17 19:10:26','2025-02-16 19:10:26',0),(32,8,'7071c729-3dc9-4917-8816-4fd6c243dced',NULL,'2025-01-17 19:10:26','2025-02-16 19:10:26',0),(33,8,'2c353f15-f208-4ce0-8727-f4c4e3322b63',NULL,'2025-01-17 19:10:26','2025-02-16 19:10:26',0),(34,8,'dd4955c4-b0ae-4fd4-9977-b8d5f2e80649',NULL,'2025-01-17 18:23:49','2025-02-16 19:10:26',1),(35,8,'dddf21bc-0a11-4e8f-952d-a019bfd16746',NULL,'2025-01-17 19:12:44','2025-02-16 19:12:44',0),(36,8,'ffaced7c-5bd6-4b83-ba03-f174dcbe62ef',NULL,'2025-01-17 19:19:34','2025-02-16 19:19:34',0),(37,8,'d691b7ef-4cb2-479d-9f1e-b2966e4442bf',NULL,'2025-01-17 19:22:02','2025-02-16 19:22:02',0),(38,8,'43849b90-e91f-499b-a8f4-681d469021a1',NULL,'2025-01-17 18:31:27','2025-02-16 19:23:50',1),(39,8,'ead354d5-3f56-4361-9eda-c8bc61f0165e',NULL,'2025-01-17 19:29:07','2025-02-16 19:29:07',0),(40,8,'7fb1503c-7810-4f9e-a232-81eb9478fdbf',NULL,'2025-01-17 19:29:19','2025-02-16 19:29:19',0),(41,8,'34ad7869-1e97-4048-aefb-6af128c14fba',NULL,'2025-01-17 19:31:28','2025-02-16 19:31:28',0),(42,8,'f44c3818-bda4-42df-8b8f-115190c3bcae',NULL,'2025-01-17 19:31:28','2025-02-16 19:31:28',0),(43,8,'b21cebaf-7e91-4e02-b4ed-22260bfd5533',NULL,'2025-01-17 18:38:59','2025-02-16 19:31:28',1),(44,8,'912e07f3-1958-466b-a424-0bae4180b418',NULL,'2025-01-17 19:31:55','2025-02-16 19:31:55',0),(45,8,'7e0ad941-834e-42ce-8851-fbd1458270ec',NULL,'2025-01-17 19:32:37','2025-02-16 19:32:37',0),(46,8,'850d84d4-4e3d-43db-aa53-478398c352ce',NULL,'2025-01-17 19:32:44','2025-02-16 19:32:44',0),(47,8,'b7ff5d80-c91d-40fc-ac27-b9c3bda62d0c',NULL,'2025-01-17 19:33:38','2025-02-16 19:33:38',0),(48,8,'124c8737-93b0-4697-8243-652d3fb18507',NULL,'2025-01-17 18:42:15','2025-02-16 19:36:57',1),(49,8,'e70d2418-d1b2-44d9-ac1e-77e5c58fac60',NULL,'2025-01-17 18:51:49','2025-02-16 19:39:00',1),(50,8,'b9048e4b-7f43-41fa-9421-3f98969ad2ad',NULL,'2025-01-17 19:42:15','2025-02-16 19:42:15',0),(51,8,'c695dcbe-f38b-46e0-ab43-eed89c4e0b4f',NULL,'2025-01-17 19:45:19','2025-02-16 19:45:19',0),(52,8,'900769db-9644-4d5c-85f2-e09bade08c99',NULL,'2025-01-17 19:48:11','2025-02-16 19:48:11',0),(53,8,'88f7df01-7d58-48eb-ad74-7df1a872f4ac',NULL,'2025-01-17 19:48:47','2025-02-16 19:48:47',0),(54,8,'8d1380f2-0398-4e46-b4d9-4a6d589bcdc9',NULL,'2025-01-17 19:50:22','2025-02-16 19:50:22',0),(55,8,'8507f5bf-834c-451c-973a-d05d401c4d63',NULL,'2025-01-17 19:51:00','2025-02-16 19:51:00',0),(56,8,'59b34c7c-2230-43f1-ac9d-811d5286235f',NULL,'2025-01-17 19:51:50','2025-02-16 19:51:50',0),(57,8,'b4be1311-52c3-4f0d-b77d-f6af72a35776',NULL,'2025-01-17 19:51:50','2025-02-16 19:51:50',0),(58,8,'6057b0f8-7d31-4b25-aa29-aea9c92c877c',NULL,'2025-01-17 19:51:50','2025-02-16 19:51:50',0),(59,8,'7b5618a4-9de4-4e18-b6f3-5de0f8664bf8',NULL,'2025-01-17 19:02:42','2025-02-16 19:51:50',1),(60,8,'9154a47d-32f0-4312-ae28-a5af02d651f4',NULL,'2025-01-17 19:52:40','2025-02-16 19:52:40',0),(61,8,'8cd0183f-6d35-481f-a522-65bc1b5e146c',NULL,'2025-01-17 19:53:23','2025-02-16 19:53:23',0),(62,8,'4a731077-2e07-4b8f-a3bd-44420f8d7e4a',NULL,'2025-01-17 19:56:29','2025-02-16 19:56:29',0),(63,8,'91314141-c5cf-467f-8e26-a2ba4af1b944',NULL,'2025-01-17 19:57:02','2025-02-16 19:57:02',0),(64,8,'639ef152-51b6-49fd-a2a3-7032662534d3',NULL,'2025-01-17 19:57:40','2025-02-16 19:57:40',0),(65,8,'0eace62a-1e74-46f3-89ab-6ee06174c600',NULL,'2025-01-17 19:59:15','2025-02-16 19:59:15',0),(66,8,'531e1a0d-386f-4cc1-bd5d-db998627d660',NULL,'2025-01-17 19:59:44','2025-02-16 19:59:44',0),(67,8,'c8b10512-172e-4f4a-8627-573b2a0d0920',NULL,'2025-01-17 19:59:52','2025-02-16 19:59:52',0),(68,8,'673ac128-8a1c-4eab-8d13-d3ebbce54529',NULL,'2025-01-17 19:44:14','2025-02-16 20:02:43',1),(69,8,'06bd5ae5-d753-47e6-860f-5f1b38f5f961',NULL,'2025-01-17 20:44:15','2025-02-16 20:44:15',0),(70,8,'eacae7b0-8e3c-49e9-89c2-24842935621e',NULL,'2025-01-17 19:52:34','2025-02-16 20:44:15',1),(71,8,'36ff1ef3-c0e1-495e-806d-962467df720c',NULL,'2025-01-17 20:44:15','2025-02-16 20:44:15',0),(72,8,'45e1c85a-c2f6-4a40-9708-bf701d43f88d',NULL,'2025-01-17 20:44:15','2025-02-16 20:44:15',0),(73,8,'135b0e6f-23d9-4edc-8453-56e3f464eda6',NULL,'2025-01-17 20:52:34','2025-02-16 20:52:34',0),(74,8,'dd8dbc53-3e54-49c9-bebe-d8b32ea76a30',NULL,'2025-01-17 20:52:34','2025-02-16 20:52:34',0),(75,8,'3c8864b7-c430-46fb-bea3-c24c23bf240a',NULL,'2025-01-17 20:10:16','2025-02-16 20:52:34',1),(76,8,'bda24621-e388-4723-b708-64817f7289e2',NULL,'2025-01-17 21:10:16','2025-02-16 21:10:16',0),(77,8,'cb5a4cee-d70c-4108-8325-1dff0cc2de40',NULL,'2025-01-17 21:10:16','2025-02-16 21:10:16',0),(78,8,'1106a33f-8f37-4464-95d3-08e211c8f092',NULL,'2025-01-17 21:10:16','2025-02-16 21:10:16',0),(79,8,'c6e285ac-f6f8-433d-9f4f-82c0af56e581',NULL,'2025-01-17 20:36:34','2025-02-16 21:10:16',1),(80,8,'73e00642-a180-47bc-bf28-378f43cf08e3',NULL,'2025-01-17 21:36:34','2025-02-16 21:36:34',0),(81,8,'4b7d72f7-bf87-4632-a4b9-5a7be129e1d7',NULL,'2025-01-17 20:44:32','2025-02-16 21:36:34',1),(82,8,'cb3d4fee-8fcf-4253-9562-fa5cecc308e4',NULL,'2025-01-17 21:36:34','2025-02-16 21:36:34',0),(83,8,'49233bef-3596-47f0-9cbb-fc8fbe08715f',NULL,'2025-01-17 21:36:34','2025-02-16 21:36:34',0),(84,8,'f4c73d17-f2f0-4aa3-80e3-d9e607cc4c12',NULL,'2025-01-17 21:44:33','2025-02-16 21:44:33',0),(85,8,'2ff5000f-0342-4539-9d38-e9902e3888cc',NULL,'2025-01-17 21:44:33','2025-02-16 21:44:33',0),(86,8,'5442d042-feeb-4893-a8c3-2ebaa7c3b67c',NULL,'2025-01-17 21:44:33','2025-02-16 21:44:33',0),(87,8,'de348396-3a18-4cba-964d-3b49bc3414b8',NULL,'2025-01-17 20:51:31','2025-02-16 21:44:33',1),(88,8,'4c01bf0d-c0d9-440e-8e01-3d662f7c6551',NULL,'2025-01-17 21:51:32','2025-02-16 21:51:32',0),(89,8,'0b5a111a-f06f-4058-b9ea-b101eff5cb5e',NULL,'2025-01-17 21:51:32','2025-02-16 21:51:32',0),(90,8,'10ae6ff7-7080-44a7-8325-a9cedb2147cc',NULL,'2025-01-17 21:00:50','2025-02-16 21:51:32',1),(91,8,'13e267f0-cae5-4230-90c7-211aa72bdd55',NULL,'2025-01-17 21:51:32','2025-02-16 21:51:32',0),(92,8,'cfb1c369-dd01-4334-a7d0-ba90a8b5b8f9',NULL,'2025-01-17 22:00:51','2025-02-16 22:00:51',0),(93,8,'105c7170-0113-4ade-8ccc-c0359d11c24f',NULL,'2025-01-17 22:00:51','2025-02-16 22:00:51',0),(94,8,'05d22920-1129-4895-ab2b-9f66504e859d',NULL,'2025-01-17 22:00:51','2025-02-16 22:00:51',0),(95,8,'c331b954-46ca-4b3c-a0d3-0adbe8cbe769',NULL,'2025-01-17 21:11:05','2025-02-16 22:00:51',1),(96,8,'b8874a66-1a94-4b68-ab2a-1e54ac49deff',NULL,'2025-01-17 21:17:09','2025-02-16 22:11:06',1),(97,8,'c398275a-f2d4-4f1b-8451-aff0351e99ab',NULL,'2025-01-17 22:11:06','2025-02-16 22:11:06',0),(98,8,'1144cd89-06a2-4d39-90ef-e1ca9e54c99d',NULL,'2025-01-17 22:11:06','2025-02-16 22:11:06',0),(99,8,'69fccf75-f7b8-470d-87be-977650a2f769',NULL,'2025-01-17 22:11:06','2025-02-16 22:11:06',0),(100,8,'f65529bf-956b-423e-8043-6ce5b01f7da5',NULL,'2025-01-17 22:17:10','2025-02-16 22:17:10',0),(101,8,'ba8b4842-5329-439c-9d2e-cbc6b285016d',NULL,'2025-01-17 22:17:10','2025-02-16 22:17:10',0),(102,8,'8bbcb688-33d5-49e9-91f0-605a4fdea102',NULL,'2025-01-17 22:17:10','2025-02-16 22:17:10',0),(103,8,'9ee075f9-af3b-412b-93c5-c7e25f9dad34',NULL,'2025-01-17 22:17:10','2025-02-16 22:17:10',0),(104,8,'94c9d62e-b66c-4a6e-8e10-7d729359e1ea',NULL,'2025-01-17 22:24:05','2025-02-16 22:24:05',0),(105,8,'dca1e4b4-8ec2-4142-82b1-ffdce7291c94',NULL,'2025-01-17 22:24:35','2025-02-16 22:24:35',0),(106,8,'39cfe262-8a75-45a3-830d-c3225f3aa68d',NULL,'2025-01-17 22:24:42','2025-02-16 22:24:42',0);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;

--
-- Dumping routines for database 'vibecheck'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-17 23:02:42
