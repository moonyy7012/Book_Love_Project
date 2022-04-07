-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: j6d106.p.ssafy.io    Database: bookdb
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category_user`
--

DROP TABLE IF EXISTS `category_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_user` (
  `category_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FK3a7pwr8n39tfj14839aictse3` (`user_id`),
  KEY `FK2the0jrotcmvqp7a3igpleont` (`category_id`),
  CONSTRAINT `FK2the0jrotcmvqp7a3igpleont` FOREIGN KEY (`category_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK3a7pwr8n39tfj14839aictse3` FOREIGN KEY (`user_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_user`
--

LOCK TABLES `category_user` WRITE;
/*!40000 ALTER TABLE `category_user` DISABLE KEYS */;
INSERT INTO `category_user` VALUES (3,2551),(3,1196),(4,2105),(4,987),(4,74),(1,8257),(1,336),(1,112011),(5,656),(5,351),(5,1322),(2,2551),(6,1),(6,13789),(7,1),(7,170),(8,1),(9,13789),(9,1137),(10,2030),(10,74),(10,170),(11,1),(13,1),(12,1322),(15,2105),(15,1),(16,2105),(17,351),(17,55889),(18,656),(18,798),(19,1),(20,1383),(21,1383),(22,1383),(14,1),(23,13789),(23,170),(24,517),(25,1),(26,170),(27,1),(28,1108),(28,1137),(28,2030),(29,1196),(30,656);
/*!40000 ALTER TABLE `category_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-07 16:45:09
