-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 07, 2022 at 08:29 PM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pidev`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id_admin` int(11) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`) VALUES
(111);

-- --------------------------------------------------------

--
-- Table structure for table `avis`
--

DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `idAvis` int(11) NOT NULL AUTO_INCREMENT,
  `referenceProduit` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idAvis`),
  KEY `fk_avis_produit` (`referenceProduit`),
  KEY `fk_avis_client` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `avis`
--

INSERT INTO `avis` (`idAvis`, `referenceProduit`, `idClient`, `description`, `type`) VALUES
(24, 6663, 222, 'pas Bon!!', 'mediocre'),
(37, 121212111, 111, 'capacité moyenne ,pas mal', 'moyen'),
(39, 963325, 111, 'La qualité n\'est pas excellente mais bonne surtout par rapport au prix', 'moyen'),
(42, 963325, 111, 'qualité pas trop mal!!', 'moyen'),
(44, 9595, 6253, 'mediocre', 'mediocre'),
(47, 9595, 111, 'bonne qualité', 'moyen'),
(51, 9595, 111, 'excellent!!', 'excellent');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int(11) NOT NULL AUTO_INCREMENT,
  `nbrAvertissement` int(11) NOT NULL,
  `ban` int(1) NOT NULL,
  `dateDebutBlock` date DEFAULT NULL,
  `dateFinBlock` date DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=6259 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`idClient`, `nbrAvertissement`, `ban`, `dateDebutBlock`, `dateFinBlock`) VALUES
(1, 0, 0, '2022-02-11', '2022-02-11'),
(2, 1, 0, '2022-02-02', '2022-02-02'),
(3, 0, 0, '2022-02-09', '2022-02-01'),
(4, 2, 0, '2022-02-11', '2022-02-11'),
(5, 0, 0, '2022-02-02', '2022-02-02'),
(6, 1, 0, '2022-02-11', '2022-02-11'),
(111, 0, 0, '2022-02-08', '2022-02-15'),
(222, 0, 0, '2022-02-13', '2022-02-15'),
(6253, 0, 0, '2022-02-17', '2022-02-22'),
(6257, 0, 0, NULL, NULL),
(6258, 0, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `idCommande` int(11) NOT NULL AUTO_INCREMENT,
  `idClient` int(11) NOT NULL,
  `adresse` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prix` float NOT NULL,
  `livree` tinyint(1) NOT NULL,
  `DateCommande` date NOT NULL,
  PRIMARY KEY (`idCommande`,`idClient`) USING BTREE,
  KEY `fk_client_commande` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`idCommande`, `idClient`, `adresse`, `prix`, `livree`, `DateCommande`) VALUES
(13, 111, 'Monastir', 402, 1, '2022-02-25'),
(14, 111, 'Ghazala', 402, 1, '2022-02-25'),
(15, 111, 'Ariana', 301.5, 0, '2022-02-25'),
(16, 111, 'binzert', 100.5, 0, '2022-02-25'),
(17, 111, 'charguiya', 1105.5, 0, '2022-02-25'),
(18, 111, 'souusa', 100.5, 0, '2022-02-25'),
(20, 111, 'manzah1', 105, 0, '2022-03-05'),
(21, 111, 'Ariana', 80, 0, '2022-03-07'),
(26, 1, 'Dar fathal', 370, 0, '2022-03-07'),
(27, 1, 'nasr2', 35, 0, '2022-03-07'),
(28, 1, 'gammarth', 7482.5, 0, '2022-03-07');

-- --------------------------------------------------------

--
-- Table structure for table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `idCommentaire` int(11) NOT NULL AUTO_INCREMENT,
  `idPublication` int(11) NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `idClient` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`idCommentaire`,`idPublication`),
  KEY `fk_commentaire` (`idPublication`),
  KEY `fk_commentaire_client` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `commentaire`
--

INSERT INTO `commentaire` (`idCommentaire`, `idPublication`, `description`, `idClient`, `date`) VALUES
(1, 1, 'Oééééééééééééééééé ça serait superbe ahla', 3, '2022-02-26'),
(2, 1, 'c\'est quoi ce truc là?', 2, '2022-02-26'),
(3, 1, 'une erreur qui n\'aurait pas due etre faite!!!!!!!! modif test', 1, '2022-02-26'),
(4, 2, 'Commentaire de test', 5, '2022-02-26'),
(5, 2, 'Confirmation test', 1, '2022-02-26'),
(6, 1, '!!', 1, '2022-03-03');

-- --------------------------------------------------------

--
-- Table structure for table `evenement`
--

DROP TABLE IF EXISTS `evenement`;
CREATE TABLE IF NOT EXISTS `evenement` (
  `reference` int(11) NOT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `localisation` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `nbrParticipant` int(11) NOT NULL,
  `Titre` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`reference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `evenement`
--

INSERT INTO `evenement` (`reference`, `dateDebut`, `dateFin`, `localisation`, `description`, `nbrParticipant`, `Titre`) VALUES
(1, '2022-03-09', '2022-03-11', 'manzah1', 'Room 4 vs 4  (Heure15:00 =>18:00)', 30, 'free fire'),
(4, '2022-03-08', '2022-03-12', 'manzah3', 'Serveur LifeTrap (Heure=20:00->21:00)', 20, 'GTA'),
(7, '2022-03-17', '2022-03-18', 'aindrahem', 'en ligne sur www.nimo.tv/wassimos', 25, 'Fortinate'),
(13, '2022-04-01', '2022-04-27', 'monastir', 'lien serveur : 51.38.60.53:27015', 1, 'CC'),
(15, '2022-03-08', '2022-03-16', 'benzart', 'Room Survive at 21:00pm', 50, 'free fire'),
(107, '2022-03-16', '2022-03-18', 'gammarth', 'Tirage at 21:00pm', 30, 'Trovo ');

-- --------------------------------------------------------

--
-- Table structure for table `lignecommande`
--

DROP TABLE IF EXISTS `lignecommande`;
CREATE TABLE IF NOT EXISTS `lignecommande` (
  `idLigne` int(11) NOT NULL AUTO_INCREMENT,
  `idCommande` int(11) NOT NULL,
  `idProduit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prix` float NOT NULL,
  PRIMARY KEY (`idLigne`,`idCommande`) USING BTREE,
  KEY `fk_ligne_produit` (`idProduit`),
  KEY `fk_ligne_commande` (`idCommande`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `lignecommande`
--

INSERT INTO `lignecommande` (`idLigne`, `idCommande`, `idProduit`, `quantite`, `prix`) VALUES
(2, 20, 6663, 3, 105),
(3, 21, 123, 1, 80),
(7, 0, 107, 1, 170),
(8, 0, 9595, 1, 38),
(11, 26, 107, 1, 170),
(12, 26, 8541, 1, 200),
(15, 27, 6663, 1, 35),
(16, 28, 107, 1, 170),
(17, 28, 66666, 1, 7312.5);

-- --------------------------------------------------------

--
-- Table structure for table `livraison`
--

DROP TABLE IF EXISTS `livraison`;
CREATE TABLE IF NOT EXISTS `livraison` (
  `idCommande` int(11) NOT NULL,
  `idLivreur` int(11) NOT NULL,
  `DateHeure` date NOT NULL,
  PRIMARY KEY (`idCommande`),
  KEY `fk_livraison_commande` (`idCommande`),
  KEY `fk_livraison_livreur` (`idLivreur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `livraison`
--

INSERT INTO `livraison` (`idCommande`, `idLivreur`, `DateHeure`) VALUES
(13, 3, '2022-03-31'),
(14, 3, '2022-04-01');

-- --------------------------------------------------------

--
-- Table structure for table `livreur`
--

DROP TABLE IF EXISTS `livreur`;
CREATE TABLE IF NOT EXISTS `livreur` (
  `idLivreur` int(11) NOT NULL AUTO_INCREMENT,
  `disponibilite` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idLivreur`)
) ENGINE=InnoDB AUTO_INCREMENT=6254 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `livreur`
--

INSERT INTO `livreur` (`idLivreur`, `disponibilite`) VALUES
(3, 1),
(4, 1),
(6, 1),
(6253, 1);

-- --------------------------------------------------------

--
-- Table structure for table `moderateur`
--

DROP TABLE IF EXISTS `moderateur`;
CREATE TABLE IF NOT EXISTS `moderateur` (
  `id_moderateur` int(11) NOT NULL,
  PRIMARY KEY (`id_moderateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `moderateur`
--

INSERT INTO `moderateur` (`id_moderateur`) VALUES
(6254);

-- --------------------------------------------------------

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
CREATE TABLE IF NOT EXISTS `participation` (
  `idParticipation` int(11) NOT NULL AUTO_INCREMENT,
  `idClient` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL,
  `nbrEtoile` int(11) NOT NULL,
  PRIMARY KEY (`idParticipation`,`idClient`,`idEvent`),
  KEY `fk_participation_event` (`idEvent`),
  KEY `fk_client_participation` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `participation`
--

INSERT INTO `participation` (`idParticipation`, `idClient`, `idEvent`, `nbrEtoile`) VALUES
(140, 4, 1, 4),
(141, 1, 4, 4),
(145, 1, 6, 4),
(148, 1, 11, 4),
(174, 1, 1, 4),
(179, 4, 13, 4),
(180, 4, 15, 4),
(181, 4, 107, 4),
(183, 3, 15, 4);

-- --------------------------------------------------------

--
-- Table structure for table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id_personne` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `dateNaissance` date NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telephone` int(11) NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_personne`)
) ENGINE=InnoDB AUTO_INCREMENT=6262 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `personne`
--

INSERT INTO `personne` (`id_personne`, `nom`, `prenom`, `dateNaissance`, `email`, `telephone`, `password`) VALUES
(1, 'zied', 'dridi', '1998-02-26', 'zieddr@gmail.com', 52848053, '00596579909133128857366812370456'),
(2, 'marwa', 'ayari', '2001-02-02', 'marouamouchayari@esprit.tn', 54342461, '00596579909133128857366812370456'),
(3, 'Ahmed', 'Mezni', '2022-02-09', 'ahmed.mezni@esprit.tn', 84562357, '00596579909133128857366812370456'),
(4, 'Azer', 'Lahmer', '2022-02-01', 'azer.lahmar@esprit.tn', 78945612, '00596579909133128857366812370456'),
(5, 'Amir', 'Ben Salah', '2022-02-11', 'amir.bensalah@esprit.tn', 54405584, '24524878684768310101714699625373'),
(6, 'Maher', 'Gasmi', '2022-02-11', 'maher.gasmi@esprit.tn', 54123321, '00596579909133128857366812370456'),
(111, 'marwa', 'ayari', '2001-02-02', 'maroua.ayari@esprit.tn', 54342461, '00596579909133128857366812370456'),
(222, 'cft', 'yugy', '2022-02-09', 'ahmedcft@esprit.tn', 84562357, '00596579909133128857366812370456'),
(6253, 'zied', 'satour', '2022-02-01', 'zied.satour@esprit.tn', 78945612, '00596579909133128857366812370456'),
(6254, 'amir', 'ben salah', '1999-11-15', 'amir123456@gmail.com', 54405584, '88004154284563470631637701913168'),
(6255, 'test', 'test', '1999-11-15', 'amir12345677@gmail.com', 54405584, '88004154284563470631637701913168'),
(6257, 'test', 'test', '1995-03-24', 'ziedziedzied@gmail.com', 12345678, '66080320515123720393070106134373'),
(6258, 'wouh', 'bayalt', '1999-03-25', 'wouhyarabi@gmail.com', 12345678, '70355847977890947371579195958021'),
(6259, 'Wassim', 'Dhieb', '1999-01-01', 'wassim.dhieb@gmail.com', 25508133, '123'),
(6260, 'Syblos', 'jemel', '1997-12-09', 'syblos.jamel@gmail.com', 54405584, 'pwd'),
(6261, 'Ben Mellessa', 'Aziz', '1999-03-18', 'azizos.chafcha@gmail.com', 54405584, '');

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
CREATE TABLE IF NOT EXISTS `plan` (
  `idPlan` int(11) NOT NULL AUTO_INCREMENT,
  `idStreamer` int(11) NOT NULL,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `duree` float NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `idEvenement` int(11) NOT NULL,
  PRIMARY KEY (`idPlan`,`idStreamer`),
  KEY `fk_plan_streamer` (`idStreamer`),
  KEY `fk_plan_evenement` (`idEvenement`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `plan`
--

INSERT INTO `plan` (`idPlan`, `idStreamer`, `date`, `heure`, `duree`, `description`, `idEvenement`) VALUES
(2, 6259, '2022-01-01', '11:11:11', 5, 'GTA 5 3h et ff 2h', 1),
(3, 6259, '2022-01-10', '11:11:11', 5, 'Boutoula 15:00 ff', 1),
(5, 6259, '2022-01-01', '11:11:13', 4, 'FIFA', 1),
(6, 6259, '2022-04-14', '11:11:11', 5, 'Fortenite', 13);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `reference` int(11) NOT NULL,
  `libelle` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `categorie` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prix` float NOT NULL,
  `note` int(11) NOT NULL,
  PRIMARY KEY (`reference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`reference`, `libelle`, `categorie`, `description`, `prix`, `note`) VALUES
(107, 'casquue', 'gaming', 'bla', 170, 2),
(123, 'RedDragonKumara', 'clavier', 'bon', 80, 0),
(6663, 'AsusMouse6', 'souris', 'Souris qui accompagne AsusGaming5', 35, 5),
(8541, 'EcranSamsung', 'ecran', 'ecran cinéma', 200, 0),
(9595, 'DragonBleuRGB', 'souris', 'souris avec lumière ...', 38, 2),
(66666, 'AsusGaming 5', 'PC', 'Tout ce qu\'il vous faut pour jouer à vos jeux préférés', 7312.5, 0),
(963325, 'PRO-M3', 'Souris ', 'Souris gaming', 35, 2),
(121212111, 'AsusVivoBook8', 'PC', 'pc pour étudiants avec options...', 3233.83, 1);

-- --------------------------------------------------------

--
-- Table structure for table `publication`
--

DROP TABLE IF EXISTS `publication`;
CREATE TABLE IF NOT EXISTS `publication` (
  `idPublication` int(11) NOT NULL AUTO_INCREMENT,
  `object` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `archive` tinyint(1) NOT NULL,
  `idClient` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`idPublication`),
  KEY `fk_publication_client` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `publication`
--

INSERT INTO `publication` (`idPublication`, `object`, `description`, `archive`, `idClient`, `date`) VALUES
(1, 'Bonjour, avez vous joué Elden Ring ?', 'J\'arrive pas a le télécharger mois-meme :(', 0, 1, '2022-02-25'),
(2, 'je cherche 4 personnes pour room 4 vs 4 contre op', '3 pc player au moins', 0, 2, '2022-02-26'),
(6, 'Pub archivé', '', 1, 1, '2022-02-26'),
(7, 'Lien azizos svp ', 'Urgent', 1, 1, '2022-02-26'),
(10, 'serveur gtrp', 'quelle est le meilleur serveur gta rp', 1, 5, '2022-02-28'),
(11, 'FIfa', 'skin sakoura failed', 0, 5, '2022-03-07'),
(12, 'On a besoin de qq qui joue a free fire ', 'contactez moi sur contact@email.com', 0, 4, '2022-03-07'),
(13, 'Minecraft en tunisie', 'Avez vous des serveur mc tunisiens ?', 0, 1, '2022-03-07');

-- --------------------------------------------------------

--
-- Table structure for table `streamer`
--

DROP TABLE IF EXISTS `streamer`;
CREATE TABLE IF NOT EXISTS `streamer` (
  `idStreamer` int(11) NOT NULL AUTO_INCREMENT,
  `informations` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lienStreaming` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idStreamer`)
) ENGINE=InnoDB AUTO_INCREMENT=6262 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `streamer`
--

INSERT INTO `streamer` (`idStreamer`, `informations`, `lienStreaming`) VALUES
(6259, 'Tunisien Streamer Born in Sfax ', 'www.nimo.tv/wassimos'),
(6260, 'Morroc Streamers Born in Casa ', 'www.nono.com/syblos'),
(6261, 'Tunisien Streamers Born in Zaghwane ', 'www.nimo.tv/azizos');

-- --------------------------------------------------------

--
-- Table structure for table `vote`
--

DROP TABLE IF EXISTS `vote`;
CREATE TABLE IF NOT EXISTS `vote` (
  `idClient` int(11) NOT NULL,
  `idPublication` int(11) NOT NULL,
  `type` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idClient`,`idPublication`),
  KEY `fk_vote_publication` (`idPublication`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vote`
--

INSERT INTO `vote` (`idClient`, `idPublication`, `type`) VALUES
(0, 1, 'UP'),
(0, 2, 'DOWN'),
(1, 1, 'UP'),
(1, 2, 'DOWN'),
(2, 1, 'UP'),
(3, 1, 'UP'),
(3, 2, 'DOWN'),
(4, 1, 'UP'),
(4, 11, 'UP'),
(5, 1, 'UP'),
(5, 8, 'DOWN'),
(5, 10, 'UP');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_admin_personne` FOREIGN KEY (`id_admin`) REFERENCES `personne` (`id_personne`);

--
-- Constraints for table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `fk_avis_client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`),
  ADD CONSTRAINT `fk_avis_produit` FOREIGN KEY (`referenceProduit`) REFERENCES `produit` (`reference`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `fk_client_personne` FOREIGN KEY (`idClient`) REFERENCES `personne` (`id_personne`);

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_client_commande` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

--
-- Constraints for table `livreur`
--
ALTER TABLE `livreur`
  ADD CONSTRAINT `fk_livreur` FOREIGN KEY (`idLivreur`) REFERENCES `personne` (`id_personne`);

--
-- Constraints for table `participation`
--
ALTER TABLE `participation`
  ADD CONSTRAINT `fk_client_participation` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

--
-- Constraints for table `streamer`
--
ALTER TABLE `streamer`
  ADD CONSTRAINT `fk_streamer_personne` FOREIGN KEY (`idStreamer`) REFERENCES `personne` (`id_personne`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
