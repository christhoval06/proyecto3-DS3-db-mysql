-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: tiendadb
-- ------------------------------------------------------
-- Server version	5.5.37-1

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

-- backup: # mysqldump -u root -p[root_password] [database_name] > dumpfilename.sql
-- restore:# mysql -u root -p[root_password] [database_name] < dumpfilename.sql

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `clienteid` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(30) NOT NULL,
  `cedula` varchar(15) NOT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `telefono` varchar(11) DEFAULT NULL,
  `provincia` varchar(2) DEFAULT NULL,
  `compra_anual` decimal(9,2) DEFAULT NULL,
  `activo` int(11) DEFAULT '1',
  PRIMARY KEY (`clienteid`),
  UNIQUE KEY `unique_cedula` (`cedula`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Christhoval','Barba','5-706-1803','24 de Dic, Felipilo','61023295','PA',30500.75,1),(2,'Angl','Smith','E-963-56','Costa Azul, Costa del Este','64519601','PA',100024.99,1),(3,'Reyd','Barba','PE-1803','Altos de Pacora, Pacora','64519601','PA',4500.75,1),(5,'Cliente','de Pruebas','8-888-888','','','PA',0.00,0),(6,'Rogelio','Rivas','6-888-888','','','HE',0.00,1);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendedor`
--

DROP TABLE IF EXISTS `vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendedor` (
  `vendedorid` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(4) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `departamento` varchar(2) DEFAULT NULL,
  `cargo` varchar(20) DEFAULT NULL,
  `venta_mensual` decimal(9,2) DEFAULT '0.00',
  `venta_anual` decimal(9,2) DEFAULT '0.00',
  `activo` int(11) DEFAULT '1',
  PRIMARY KEY (`vendedorid`),
  UNIQUE KEY `unique_codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendedor`
--

LOCK TABLES `vendedor` WRITE;
/*!40000 ALTER TABLE `vendedor` DISABLE KEYS */;
INSERT INTO `vendedor` VALUES (1,'001','Daisy','Donalds','LN','Demostradora',20165.63,144695.75,1),(2,'002','Fabian','Fernández','CO','Cajero',6523.99,7963250.00,1),(3,'003','Susana','Riveiro','JO','Demostradora',1069532.00,2956263.62,1);
/*!40000 ALTER TABLE `vendedor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-23 16:28:38
