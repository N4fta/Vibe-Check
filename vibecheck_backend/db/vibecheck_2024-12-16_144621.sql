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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='User accounts';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (7,'johnny2@test.tes','johnny2','$2a$10$mTP3UgTwG42pFevPT.9YF.SydRbw2oRaHfg5ZMJyzb9GPmQmqUmRm','1111-11-11 00:00:00',0x00,'user-default-64.png'),(8,'johnny@test.com','johnny','$2a$10$fblRCt6RmAeJXWEAt2jnf./YBpvQSTYAa8dwxyGmGr.ET7AG1589W','2000-11-11 00:00:00',0x00,'user-default-64.png');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,3,'Vibecheck','🌟 Excited to announce the launch of our new app, Vibecheck! 🚀  📱 Download now and explore a world of tech news, reviews, and tutorials.  🔗 [Link to download]  #Vibecheck #AppLaunch #TechNews #In',NULL,'2024-11-13 15:46:40'),(2,4,'Second Evil','😈 Mischief managed... for now. 🔥  Just caused a \"minor\" internet outage in the penthouse. Nothing like a little chaos to start the day! 😌  #DevilishDelights #ChaosReigns #MischiefMade',1,'2024-11-13 16:07:33'),(3,1,'test test','Is this working?',NULL,'2024-11-13 16:11:53'),(4,2,'A new day','🌞 Morning coffee never disappoints! ☕️ Ready to tackle the day. #CoffeeLovers #MorningVibes',NULL,'2024-11-13 16:17:14'),(5,2,'Lofi','🌧️ Rainy days call for cozy socks and a good book. 📚 Perfect way to spend the afternoon. #RainyDayReads #CozyVibes',NULL,'2024-11-18 23:03:46'),(6,2,'Yum!','🍔 Just tried that new burger joint downtown. 🍟 Verdict: Delicious! 😍 #FoodieLife #BurgerBliss',NULL,'2024-11-18 23:03:53'),(7,1,'Tech Upgrade','💻 Finally upgraded my rig with the latest GPU! 🎮 Can\'t wait to see the difference in performance. #GamingLife #TechUpgrade',NULL,'2024-11-18 22:04:50'),(8,1,'Hot tip!','💡 Just discovered a game-changing productivity hack. 🌟 My workflow will never be the same! #TechTips #ProductivityHacks',NULL,'2024-11-18 22:05:17'),(9,6,'Monday Nov 2024','🌞 Behold, mortals! Today shall be a day of radiant sunshine and clear skies. ☀️ Bask in my glory, for tomorrow may bring the patter of rain. #WeatherWisdom #SunnyDays',NULL,'2024-11-18 23:06:14'),(10,5,'Ice Cream Mishap','🍦 Just tried to eat ice cream with chopsticks... 🥢 It was a sticky disaster! 😂 Who needs spoons anyway? #IceCreamFail #SillyMoments',NULL,'2024-11-18 22:07:52'),(11,5,'Dog Life 1','🐶 Tried to teach my dog to sing the national anthem... 🎤 He howled, I laughed, and the neighbors thought it was a coyote convention! 😂 #DogLife #SillySymphonies',NULL,'2024-11-18 22:08:27'),(12,3,'Hello ...brother','Hows hell?',2,'2024-11-18 23:41:36'),(13,1,'test','testeststest',NULL,'2024-12-09 14:26:51');
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
INSERT INTO `post_like` VALUES (1,1,'2024-11-13 16:00:42'),(1,3,'2024-12-10 12:10:51'),(1,6,'2024-12-10 11:22:55'),(1,7,'2024-12-11 08:26:10'),(1,13,'2024-12-10 12:11:05'),(2,1,'2024-11-13 16:00:49');
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;

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

-- Dump completed on 2024-12-16 14:46:24
