CREATE DATABASE  IF NOT EXISTS `bd_olimpo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bd_olimpo`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_olimpo
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
-- Table structure for table `cat_gym`
--

DROP TABLE IF EXISTS `cat_gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_gym` (
  `id_cat_gym` int(11) NOT NULL,
  `nombre_gym` varchar(250) NOT NULL,
  `cve_gym` varchar(45) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_cat_gym`),
  UNIQUE KEY `cve_gym_UNIQUE` (`cve_gym`),
  UNIQUE KEY `nombre_gym_UNIQUE` (`nombre_gym`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tabla de catálogos donde se obtendrán todas las susucrsales';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_gym`
--

LOCK TABLES `cat_gym` WRITE;
/*!40000 ALTER TABLE `cat_gym` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_membresias`
--

DROP TABLE IF EXISTS `cat_membresias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_membresias` (
  `id_cat_membresias` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_membresias` varchar(250) NOT NULL,
  `costo_membresia` decimal(10,5) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_cat_membresias`,`tipo_membresias`),
  UNIQUE KEY `id_cat_membresias_UNIQUE` (`id_cat_membresias`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_membresias`
--

LOCK TABLES `cat_membresias` WRITE;
/*!40000 ALTER TABLE `cat_membresias` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_membresias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_promocion`
--

DROP TABLE IF EXISTS `cat_promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_promocion` (
  `id:_cat_promocion` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_promocion` varchar(250) DEFAULT NULL,
  `desc_porcentaje` decimal(4,2) NOT NULL,
  `desc_costo` decimal(10,5) NOT NULL,
  `num_meses_aplicados` int(11) NOT NULL,
  `mes_raiz` int(11) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id:_cat_promocion`),
  UNIQUE KEY `id:_cat_promocion_UNIQUE` (`id:_cat_promocion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_promocion`
--

LOCK TABLES `cat_promocion` WRITE;
/*!40000 ALTER TABLE `cat_promocion` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_proveedor`
--

DROP TABLE IF EXISTS `cat_proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_proveedor` (
  `id_cat_proveedor` int(11) NOT NULL AUTO_INCREMENT,
  `id_cat_proveedor_servicio` int(10) NOT NULL,
  `proveedor` varchar(250) NOT NULL,
  `observaciones` varchar(250) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_cat_proveedor`),
  UNIQUE KEY `id_cat_proveedor_UNIQUE` (`id_cat_proveedor`),
  KEY `servicio_proveedor_idx` (`id_cat_proveedor_servicio`),
  CONSTRAINT `servicio_proveedor` FOREIGN KEY (`id_cat_proveedor_servicio`) REFERENCES `cat_proveedor_servicio` (`id_cat_proveedor_sevicio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_proveedor`
--

LOCK TABLES `cat_proveedor` WRITE;
/*!40000 ALTER TABLE `cat_proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_proveedor_servicio`
--

DROP TABLE IF EXISTS `cat_proveedor_servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_proveedor_servicio` (
  `id_cat_proveedor_sevicio` int(10) NOT NULL AUTO_INCREMENT,
  `servicio` varchar(250) NOT NULL,
  `observaciones` varchar(250) NOT NULL,
  `vigencia` varchar(250) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_cat_proveedor_sevicio`),
  UNIQUE KEY `id_cat_proveedor_sevicio_UNIQUE` (`id_cat_proveedor_sevicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_proveedor_servicio`
--

LOCK TABLES `cat_proveedor_servicio` WRITE;
/*!40000 ALTER TABLE `cat_proveedor_servicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_proveedor_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_tipo_empleado`
--

DROP TABLE IF EXISTS `cat_tipo_empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_empleado` (
  `id_cat_tipo_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_empleado` varchar(250) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id_cat_tipo_empleado`),
  UNIQUE KEY `id_cat_tipo_empleado_UNIQUE` (`id_cat_tipo_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_tipo_empleado`
--

LOCK TABLES `cat_tipo_empleado` WRITE;
/*!40000 ALTER TABLE `cat_tipo_empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_tipo_empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_tipo_ingreso`
--

DROP TABLE IF EXISTS `cat_tipo_ingreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_ingreso` (
  `id_cat_tipo_ingreso` int(11) NOT NULL AUTO_INCREMENT,
  `concepto` varchar(250) NOT NULL,
  PRIMARY KEY (`id_cat_tipo_ingreso`),
  UNIQUE KEY `id_cat_tipo_ingreso_UNIQUE` (`id_cat_tipo_ingreso`),
  UNIQUE KEY `concepto_UNIQUE` (`concepto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_tipo_ingreso`
--

LOCK TABLES `cat_tipo_ingreso` WRITE;
/*!40000 ALTER TABLE `cat_tipo_ingreso` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_tipo_ingreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cat_tipo_usuario`
--

DROP TABLE IF EXISTS `cat_tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_usuario` (
  `id_cat_tipo_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_usuario` varchar(250) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_cat_tipo_usuario`),
  UNIQUE KEY `id_cat_tipo_usuario_UNIQUE` (`id_cat_tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat_tipo_usuario`
--

LOCK TABLES `cat_tipo_usuario` WRITE;
/*!40000 ALTER TABLE `cat_tipo_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `cat_tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_acceso_gym`
--

DROP TABLE IF EXISTS `ctrl_acceso_gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_acceso_gym` (
  `id_ctrl_acceso_gym` int(11) NOT NULL AUTO_INCREMENT,
  `id_ctrl_usuario_gym` int(11) NOT NULL,
  `id_cat_gym` int(11) NOT NULL,
  `fecha_acceso` datetime NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_acceso_gym`),
  UNIQUE KEY `id_ctrl_acceso_gym_UNIQUE` (`id_ctrl_acceso_gym`),
  KEY `usuario_acceso_idx` (`id_ctrl_usuario_gym`),
  KEY `id_cat_gym_idx` (`id_cat_gym`),
  KEY `autorizo_ctrl_acceso_gym_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_acceso_gym` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_cat_gym_acceso` FOREIGN KEY (`id_cat_gym`) REFERENCES `cat_gym` (`id_cat_gym`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usuario_acceso` FOREIGN KEY (`id_ctrl_usuario_gym`) REFERENCES `ctrl_usuario_gym` (`id_ctrl_usuario_gym`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_acceso_gym`
--

LOCK TABLES `ctrl_acceso_gym` WRITE;
/*!40000 ALTER TABLE `ctrl_acceso_gym` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_acceso_gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_corte_caja`
--

DROP TABLE IF EXISTS `ctrl_corte_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_corte_caja` (
  `id_ctrl_corte_caja` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio_corte` datetime NOT NULL,
  `fecha_final_corte` datetime NOT NULL,
  `descripcion_corte` varchar(250) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_corte_caja`),
  UNIQUE KEY `id_ctrl_corte_caja_UNIQUE` (`id_ctrl_corte_caja`),
  UNIQUE KEY `fecha_inicio_corte_UNIQUE` (`fecha_inicio_corte`),
  KEY `autorizo_corte_caja_idx` (`autorizo`),
  CONSTRAINT `autorizo_corte_caja` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_corte_caja`
--

LOCK TABLES `ctrl_corte_caja` WRITE;
/*!40000 ALTER TABLE `ctrl_corte_caja` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_corte_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_egresos`
--

DROP TABLE IF EXISTS `ctrl_egresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_egresos` (
  `id_ctrl_egresos` int(11) NOT NULL AUTO_INCREMENT,
  `id_proveedor` int(11) NOT NULL,
  `descripcion_egreso` varchar(250) NOT NULL,
  `Pago_total` decimal(10,5) NOT NULL,
  `id_cat_gym` int(11) NOT NULL,
  `id_corte_caja` int(11) NOT NULL,
  `archivo_factura` varchar(250) NOT NULL,
  `factura` mediumblob NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_uactualizar` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_egresos`),
  UNIQUE KEY `id_ctrl_egresos_UNIQUE` (`id_ctrl_egresos`),
  KEY `gym_cat_idx` (`id_cat_gym`),
  KEY `proveedor_egreso_idx` (`id_proveedor`),
  KEY `id_corte_idx` (`id_corte_caja`),
  KEY `autorizo_ctrl_egreso_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_egreso` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `gym_cat_egreso` FOREIGN KEY (`id_cat_gym`) REFERENCES `cat_gym` (`id_cat_gym`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_corte_caja_egresos` FOREIGN KEY (`id_corte_caja`) REFERENCES `ctrl_corte_caja` (`id_ctrl_corte_caja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proveedor_egreso` FOREIGN KEY (`id_proveedor`) REFERENCES `cat_proveedor` (`id_cat_proveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_egresos`
--

LOCK TABLES `ctrl_egresos` WRITE;
/*!40000 ALTER TABLE `ctrl_egresos` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_egresos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_empleado`
--

DROP TABLE IF EXISTS `ctrl_empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_empleado` (
  `id_ctrl_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `id_cat_tipo_empleado` int(11) NOT NULL,
  `nombre_empledado` varchar(250) NOT NULL,
  `ap_paterno` varchar(250) NOT NULL,
  `ap_materno` varchar(250) NOT NULL,
  `sexo` varchar(45) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_ctrl_empleado`),
  UNIQUE KEY `idc_trl_empleado_UNIQUE` (`id_ctrl_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_empleado`
--

LOCK TABLES `ctrl_empleado` WRITE;
/*!40000 ALTER TABLE `ctrl_empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_finanzas_otros_pagos`
--

DROP TABLE IF EXISTS `ctrl_finanzas_otros_pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_finanzas_otros_pagos` (
  `id_ctrl_finanzas_otros_pagos` int(11) NOT NULL AUTO_INCREMENT,
  `id_ctrl_usuario_finanzas` int(11) NOT NULL,
  `concepto_pago` varchar(250) NOT NULL,
  `id_cat_gym` int(11) NOT NULL,
  `total_pago` decimal(10,5) NOT NULL,
  `fecha_pago` datetime NOT NULL,
  `id_cat_tipo_ingreso` int(11) DEFAULT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_finanzas_otros_pagos`),
  UNIQUE KEY `id_ctrl_finanzas_otros_pagos_UNIQUE` (`id_ctrl_finanzas_otros_pagos`),
  KEY `id_ctrl_usuario_finanzas_idx` (`id_ctrl_usuario_finanzas`),
  KEY `id_gym_idx` (`id_cat_gym`),
  KEY `id_cat_tipo_ingreso_idx` (`id_cat_tipo_ingreso`),
  KEY `autorizo_ctrl_finanzas_otros_pagos_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_finanzas_otros_pagos` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_cat_tipo_ingreso` FOREIGN KEY (`id_cat_tipo_ingreso`) REFERENCES `cat_tipo_ingreso` (`id_cat_tipo_ingreso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_ctrl_usuario_finanzas_otros` FOREIGN KEY (`id_ctrl_usuario_finanzas`) REFERENCES `ctrl_usuario_finanzas` (`id_ctrl_usuario_finanzas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_gym` FOREIGN KEY (`id_cat_gym`) REFERENCES `cat_gym` (`id_cat_gym`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_finanzas_otros_pagos`
--

LOCK TABLES `ctrl_finanzas_otros_pagos` WRITE;
/*!40000 ALTER TABLE `ctrl_finanzas_otros_pagos` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_finanzas_otros_pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_ingresos`
--

DROP TABLE IF EXISTS `ctrl_ingresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_ingresos` (
  `id_ctrl_ingresos` int(11) NOT NULL AUTO_INCREMENT,
  `id_ctrl_usuario_finanzas_pagos` int(11) NOT NULL,
  `id_cat_tipo_ingreso` int(11) NOT NULL,
  `Monto_ingreso` decimal(10,5) NOT NULL,
  `fecha_ingreso` datetime NOT NULL,
  `id_cat_gym` int(11) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `id_ctrl_corte_caja` int(11) NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_ingresos`),
  UNIQUE KEY `id_ctrl_ingresos_UNIQUE` (`id_ctrl_ingresos`),
  KEY `gym_idx` (`id_cat_gym`),
  KEY `tipo_ing_idx` (`id_cat_tipo_ingreso`),
  KEY `id_pago_otros_idx` (`id_ctrl_usuario_finanzas_pagos`),
  KEY `id_corte_idx` (`id_ctrl_corte_caja`),
  KEY `autorizo_ctrl_pagos_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_pagos` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `gym` FOREIGN KEY (`id_cat_gym`) REFERENCES `cat_gym` (`id_cat_gym`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_corte` FOREIGN KEY (`id_ctrl_corte_caja`) REFERENCES `ctrl_corte_caja` (`id_ctrl_corte_caja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tipo_ing` FOREIGN KEY (`id_cat_tipo_ingreso`) REFERENCES `cat_tipo_ingreso` (`id_cat_tipo_ingreso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_ingresos`
--

LOCK TABLES `ctrl_ingresos` WRITE;
/*!40000 ALTER TABLE `ctrl_ingresos` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_ingresos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_seg_perfil`
--

DROP TABLE IF EXISTS `ctrl_seg_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_seg_perfil` (
  `id ctrl_seg_perfil` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_perfil` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id ctrl_seg_perfil`),
  UNIQUE KEY `id ctrl_seg_perfil_UNIQUE` (`id ctrl_seg_perfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_seg_perfil`
--

LOCK TABLES `ctrl_seg_perfil` WRITE;
/*!40000 ALTER TABLE `ctrl_seg_perfil` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_seg_perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_seg_usuario`
--

DROP TABLE IF EXISTS `ctrl_seg_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_seg_usuario` (
  `id_ctrl_seg_usuario` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `id_perfil` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_ctrl_seg_usuario`),
  UNIQUE KEY `id_empleado_UNIQUE` (`id_empleado`),
  KEY `perfil_usuario_sist_idx` (`id_perfil`),
  CONSTRAINT `empleado_usuario` FOREIGN KEY (`id_empleado`) REFERENCES `ctrl_empleado` (`id_ctrl_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `perfil_usuario_sist` FOREIGN KEY (`id_perfil`) REFERENCES `ctrl_seg_perfil` (`id ctrl_seg_perfil`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_seg_usuario`
--

LOCK TABLES `ctrl_seg_usuario` WRITE;
/*!40000 ALTER TABLE `ctrl_seg_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_seg_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_usuario`
--

DROP TABLE IF EXISTS `ctrl_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_usuario` (
  `id_Ctrl_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_usuario` varchar(250) NOT NULL,
  `Ap_Paterno_usuario` varchar(250) NOT NULL,
  `Ap_materno_usuario` varchar(250) NOT NULL,
  `Imagen_usuario` mediumblob NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_Ctrl_usuario`),
  UNIQUE KEY `id_Ctrl_usuario_UNIQUE` (`id_Ctrl_usuario`),
  KEY `autorizo_ctrl_usuario_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_usuario` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de Control de usuarios';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_usuario`
--

LOCK TABLES `ctrl_usuario` WRITE;
/*!40000 ALTER TABLE `ctrl_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_usuario_atributos`
--

DROP TABLE IF EXISTS `ctrl_usuario_atributos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_usuario_atributos` (
  `id_Ctrl_usuario_atributos` int(11) NOT NULL AUTO_INCREMENT,
  `id_Ctrl_usuario` int(11) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `Sexo` varchar(45) NOT NULL,
  `dir_calle` varchar(250) NOT NULL,
  `dir_num_ext` varchar(45) NOT NULL,
  `dir_colonia` varchar(250) NOT NULL,
  `dir_municipio` varchar(250) NOT NULL,
  `dir_estado` varchar(250) NOT NULL,
  `correo_electronico` varchar(250) NOT NULL,
  `tel_celular` varchar(45) NOT NULL,
  `tel_fijo` varchar(45) NOT NULL,
  `tel_emergencia` varchar(45) NOT NULL,
  `med_enfermedad` varchar(250) NOT NULL,
  `med_alergia` varchar(250) NOT NULL,
  `med_tipo_sangre` varchar(45) NOT NULL,
  `med_servicio_medico` varchar(45) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_Ctrl_usuario_atributos`),
  UNIQUE KEY `id_Ctrl_usuario_atributos_UNIQUE` (`id_Ctrl_usuario_atributos`),
  UNIQUE KEY `id_Ctrl_usuario_UNIQUE` (`id_Ctrl_usuario`),
  CONSTRAINT `` FOREIGN KEY (`id_Ctrl_usuario`) REFERENCES `ctrl_usuario` (`id_Ctrl_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla donde se alojan los atributos de los usuario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_usuario_atributos`
--

LOCK TABLES `ctrl_usuario_atributos` WRITE;
/*!40000 ALTER TABLE `ctrl_usuario_atributos` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_usuario_atributos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_usuario_finanzas`
--

DROP TABLE IF EXISTS `ctrl_usuario_finanzas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_usuario_finanzas` (
  `id_ctrl_usuario_finanzas` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario_finanzas` int(11) NOT NULL,
  `id_cat_membresia` int(11) NOT NULL,
  `viegencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_usuario_finanzas`),
  UNIQUE KEY `id_usuario_finanzas_UNIQUE` (`id_usuario_finanzas`),
  KEY `id_cat_membresias_idx` (`id_cat_membresia`),
  KEY `autorizo_ctrl_usuario_finanzas_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_usuario_finanzas` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_cat_membresias` FOREIGN KEY (`id_cat_membresia`) REFERENCES `cat_membresias` (`id_cat_membresias`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_usuario_finanzas` FOREIGN KEY (`id_usuario_finanzas`) REFERENCES `ctrl_usuario_gym` (`id_usuario_finanzas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_usuario_finanzas`
--

LOCK TABLES `ctrl_usuario_finanzas` WRITE;
/*!40000 ALTER TABLE `ctrl_usuario_finanzas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_usuario_finanzas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_usuario_finanzas_pago`
--

DROP TABLE IF EXISTS `ctrl_usuario_finanzas_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_usuario_finanzas_pago` (
  `id_ctrl_usuario_finanzas_pago` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_ctrl_usuario_finanzas` int(11) NOT NULL,
  `id_ctrl_usuario_promo` int(11) NOT NULL,
  `num_pago` int(11) NOT NULL,
  `id_cat_gym` int(11) NOT NULL,
  `pago_descuento` decimal(10,5) NOT NULL,
  `pago_total` decimal(10,5) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `id_cat_concepto_pago` int(11) NOT NULL,
  `fecha_pago` datetime NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `id_cat_ingreso` int(11) NOT NULL,
  `Descripcion pago` varchar(250) NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_usuario_finanzas_pago`),
  KEY `id_ctrl_usuario_promo_idx` (`id_ctrl_usuario_promo`),
  KEY `ingreso_tipo_idx` (`id_cat_ingreso`),
  KEY `autorizo_ctrl_usuario_finanzas_pago_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_usuario_finanzas_pago` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_ctrl_usuario_promo` FOREIGN KEY (`id_ctrl_usuario_promo`) REFERENCES `ctrl_usuario_promocion` (`id_ctrl_usuario_promocion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ingreso_tipo` FOREIGN KEY (`id_cat_ingreso`) REFERENCES `cat_tipo_ingreso` (`id_cat_tipo_ingreso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_usuario_finanzas_pago`
--

LOCK TABLES `ctrl_usuario_finanzas_pago` WRITE;
/*!40000 ALTER TABLE `ctrl_usuario_finanzas_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_usuario_finanzas_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_usuario_gym`
--

DROP TABLE IF EXISTS `ctrl_usuario_gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_usuario_gym` (
  `id_ctrl_usuario_gym` int(11) NOT NULL,
  `id_ctrl_usuario` int(11) NOT NULL,
  `fecha_incorporacion` datetime NOT NULL,
  `id_cat_gym` int(11) NOT NULL,
  `id_cat_tipo_usuario` int(11) NOT NULL,
  `id_usuario_finanzas` int(11) NOT NULL AUTO_INCREMENT,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  `autorizo` int(11) NOT NULL,
  PRIMARY KEY (`id_ctrl_usuario_gym`),
  UNIQUE KEY `id_ctrl_usuario_UNIQUE` (`id_ctrl_usuario`),
  UNIQUE KEY `id_ctrl_usuario_gym_UNIQUE` (`id_ctrl_usuario_gym`),
  UNIQUE KEY `id_usuario_finanzas_UNIQUE` (`id_usuario_finanzas`),
  KEY `id_cat_gym_idx` (`id_cat_gym`),
  KEY `id_cat_tipo_usuario_idx` (`id_cat_tipo_usuario`),
  KEY `autorizo_ctrl_usuario_gym_idx` (`autorizo`),
  CONSTRAINT `autorizo_ctrl_usuario_gym` FOREIGN KEY (`autorizo`) REFERENCES `ctrl_seg_usuario` (`id_ctrl_seg_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_cat_gym` FOREIGN KEY (`id_cat_gym`) REFERENCES `cat_gym` (`id_cat_gym`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_cat_tipo_usuario` FOREIGN KEY (`id_cat_tipo_usuario`) REFERENCES `cat_tipo_usuario` (`id_cat_tipo_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_ctrl_usuario` FOREIGN KEY (`id_ctrl_usuario`) REFERENCES `ctrl_usuario` (`id_Ctrl_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla nucleo donde se enlazará al usuario con sus actividades financieras y fisicas dentro del club ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_usuario_gym`
--

LOCK TABLES `ctrl_usuario_gym` WRITE;
/*!40000 ALTER TABLE `ctrl_usuario_gym` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_usuario_gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl_usuario_promocion`
--

DROP TABLE IF EXISTS `ctrl_usuario_promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctrl_usuario_promocion` (
  `id_ctrl_usuario_promocion` int(11) NOT NULL AUTO_INCREMENT,
  `id_cat_promocion` int(11) NOT NULL,
  `id_ctrl_usuario_finanzas` int(11) NOT NULL,
  `vigencia` int(11) NOT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id_ctrl_usuario_promocion`),
  UNIQUE KEY `id_ctrl_usuario_promocion_UNIQUE` (`id_ctrl_usuario_promocion`),
  KEY `id_cat_promocion_idx` (`id_cat_promocion`),
  KEY `id_ctrl_usuario_finanzas_idx` (`id_ctrl_usuario_finanzas`),
  CONSTRAINT `id_cat_promocion` FOREIGN KEY (`id_cat_promocion`) REFERENCES `cat_promocion` (`id:_cat_promocion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_ctrl_usuario_finanzas` FOREIGN KEY (`id_ctrl_usuario_finanzas`) REFERENCES `ctrl_usuario_finanzas` (`id_ctrl_usuario_finanzas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl_usuario_promocion`
--

LOCK TABLES `ctrl_usuario_promocion` WRITE;
/*!40000 ALTER TABLE `ctrl_usuario_promocion` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl_usuario_promocion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-08 21:07:51
