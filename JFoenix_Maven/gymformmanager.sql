-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 17 fév. 2019 à 17:26
-- Version du serveur :  5.7.19
-- Version de PHP :  7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gymformmanager`
--

-- --------------------------------------------------------

--
-- Structure de la table `employeesequence`
--

DROP TABLE IF EXISTS `employeesequence`;
CREATE TABLE IF NOT EXISTS `employeesequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `membre`
--

DROP TABLE IF EXISTS `membre`;
CREATE TABLE IF NOT EXISTS `membre` (
  `idMembre` int(6) NOT NULL,
  `nomMembre` varchar(30) NOT NULL,
  `prenomMembre` varchar(30) NOT NULL,
  `emailMembre` varchar(50) DEFAULT NULL,
  `numPortableMembre` varchar(11) NOT NULL,
  `dtnMembre` date DEFAULT NULL,
  `poidMembre` float(5,2) DEFAULT NULL,
  `tailleMembre` float(3,2) DEFAULT NULL,
  `sportsMembre` tinyblob,
  `genre` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`idMembre`),
  UNIQUE KEY `idMembre` (`idMembre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `membresequence`
--

DROP TABLE IF EXISTS `membresequence`;
CREATE TABLE IF NOT EXISTS `membresequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `membre_sport`
--

DROP TABLE IF EXISTS `membre_sport`;
CREATE TABLE IF NOT EXISTS `membre_sport` (
  `idSport` int(5) NOT NULL,
  `idMembre` int(5) NOT NULL,
  `idMembre_Sport` int(11) NOT NULL,
  PRIMARY KEY (`idMembre_Sport`),
  KEY `membre_sport___FK___sport` (`idSport`),
  KEY `membre_sport___FK___membre` (`idMembre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `membre_sportsequence`
--

DROP TABLE IF EXISTS `membre_sportsequence`;
CREATE TABLE IF NOT EXISTS `membre_sportsequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

DROP TABLE IF EXISTS `paiement`;
CREATE TABLE IF NOT EXISTS `paiement` (
  `idMembre_Sport` int(5) NOT NULL,
  `datePaiement` date DEFAULT NULL,
  `sommePaye` int(11) DEFAULT NULL,
  `typePaiement` varchar(20) DEFAULT NULL,
  `duree` varchar(20) DEFAULT NULL,
  `plusRecent` tinyint(1) DEFAULT '0',
  `idPaiement` int(5) NOT NULL,
  PRIMARY KEY (`idPaiement`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `paiementsequence`
--

DROP TABLE IF EXISTS `paiementsequence`;
CREATE TABLE IF NOT EXISTS `paiementsequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `sport`
--

DROP TABLE IF EXISTS `sport`;
CREATE TABLE IF NOT EXISTS `sport` (
  `idSport` int(11) NOT NULL,
  `intituleSport` varchar(20) DEFAULT NULL,
  `prixAnnee` int(5) DEFAULT NULL,
  `prixTrimestre` int(5) DEFAULT NULL,
  `prixMois` int(5) DEFAULT NULL,
  `entraineur` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idSport`),
  UNIQUE KEY `idSport` (`idSport`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `sportsequence`
--

DROP TABLE IF EXISTS `sportsequence`;
CREATE TABLE IF NOT EXISTS `sportsequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
