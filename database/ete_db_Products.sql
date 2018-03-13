CREATE DATABASE  IF NOT EXISTS `ete_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ete_db`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: ete_db
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Products`
--

DROP TABLE IF EXISTS `Products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Products` (
  `idProducts` int(11) NOT NULL AUTO_INCREMENT,
  `Products_Code` varchar(45) NOT NULL,
  `Products_Description` varchar(250) NOT NULL,
  `Products_Stock` int(11) NOT NULL DEFAULT '0',
  `Products_Price` decimal(15,0) NOT NULL,
  `Product_Types_idProduct_Types` int(11) NOT NULL,
  PRIMARY KEY (`idProducts`),
  KEY `fk_Products_Product_Types1_idx` (`Product_Types_idProduct_Types`),
  CONSTRAINT `fk_Products_Product_Types1` FOREIGN KEY (`Product_Types_idProduct_Types`) REFERENCES `Product_Types` (`idProduct_Types`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Products`
--

LOCK TABLES `Products` WRITE;
/*!40000 ALTER TABLE `Products` DISABLE KEYS */;
INSERT INTO `Products` VALUES (1,'MJ001','Madagascar Jasimine',100,22,3),(2,'BP001','Butterfly Pea',100,26,3),(3,'CS001','Camellia Senensis Tea',80,48,3),(4,'CO001','Chocolate Orage Rudbeckia',120,28,3),(5,'PP001','Purple Pitcher Plant',60,62,3),(6,'AG001','American Ginsing',600,12,3),(7,'QS001','Queen Sago',200,20,3),(8,'AS001','Alpine Strawberry',400,16,3),(9,'DG001','Dwarf Godetia',20,36,3),(10,'BB001','Black Bat Plant',40,100,3),(11,'MB001','Miniature Blue Popcorn',500,11,3),(12,'BM002','Black Maui Orchids',200,150,3),(13,'VF002','Venus Fly Trap',500,30,3),(14,'BC003','Black Currant Whirl Hollyhock',300,23,3),(15,'BC001','Blue Camphor',60,45,2),(16,'KP001','Kangaroo Paw',68,34,2),(17,'YB001','Yellow Buckeye',71,28,2),(18,'BB001','Butterfly Bush',36,81,2),(19,'BB002','Boxwood Bush',48,31,2),(20,'YC001','Yello Callicarpa',30,67,2),(21,'SB001','Smoke Bush',100,26,2),(22,'AF001','African Forsythia',80,38,2),(23,'BP001','Blue Paeonia',60,41,2),(24,'JB001','Juniper Berry',40,18,2),(25,'SH001','Saaz Hops',300,6,2),(27,'GN003','Great Northern Camellias',120,97,2),(28,'EF001','Elephant Foot',6,800,1),(29,'BB001','Black Bamboo',30,100,1),(30,'BF001','Banyan Fig',13,325,1),(31,'RSM001','Red Snakebark Maple',200,98,1),(32,'BE001','Box Elder',400,36,1),(33,'JYM001','Japanese Yama Maple',500,42,1),(34,'EOM001','European Olive',62,225,1),(35,'YK001','Yemen Khat',21,625,1),(36,'AT001','Asian Teak',83,260,1),(37,'WW001','Worm Wood',91,115,1),(38,'LC001','Lemon Cypress',280,67,1),(39,'GB001','Ginkgo Biloba',75,80,1),(40,'CT001','Cigar Tree',10,83,1),(41,'AM002','Arden Maple',40,70,1),(42,'FL002','Finger Leaf Elm',16,75,1);
/*!40000 ALTER TABLE `Products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-16 18:54:05
