-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 19 fév. 2022 à 11:28
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pidev`
--

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `idAvis` int(11) NOT NULL AUTO_INCREMENT,
  `referenceProduit` int(11) NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idAvis`),
  KEY `fk_avis_produit` (`referenceProduit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int(11) NOT NULL AUTO_INCREMENT,
  `nbrAvertissement` int(11) NOT NULL,
  `ban` int(1) NOT NULL,
  `dateDebutBlock` date NOT NULL,
  `dateFinBlock` date NOT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=6254 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`idClient`, `nbrAvertissement`, `ban`, `dateDebutBlock`, `dateFinBlock`) VALUES
(111, 0, 0, '2022-02-08', '2022-02-15'),
(222, 0, 0, '2022-02-13', '2022-02-15'),
(6253, 0, 0, '2022-02-17', '2022-02-22');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
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
) ENGINE=InnoDB AUTO_INCREMENT=457 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`idCommande`, `idClient`, `adresse`, `prix`, `livree`, `DateCommande`) VALUES
(9, 111, 'gammarth', 30, 0, '2020-01-25'),
(10, 6253, 'azertyu', 270, 0, '2022-02-09'),
(456, 222, 'tyuiompù', 100, 1, '2022-02-08');

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `idCommentaire` int(11) NOT NULL AUTO_INCREMENT,
  `idPublication` int(11) NOT NULL,
  `descritption` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`idCommentaire`,`idPublication`),
  KEY `fk_commentaire` (`idPublication`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

DROP TABLE IF EXISTS `evenement`;
CREATE TABLE IF NOT EXISTS `evenement` (
  `reference` int(11) NOT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `localisation` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `descritption` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `nbrParticipant` int(11) NOT NULL,
  PRIMARY KEY (`reference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lignecommande`
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `lignecommande`
--

INSERT INTO `lignecommande` (`idLigne`, `idCommande`, `idProduit`, `quantite`, `prix`) VALUES
(11, 9, 1010, 2, 50),
(12, 9, 1010, 10, 100),
(13, 9, 9512, 5, 725);

-- --------------------------------------------------------

--
-- Structure de la table `livraison`
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
-- Déchargement des données de la table `livraison`
--

INSERT INTO `livraison` (`idCommande`, `idLivreur`, `DateHeure`) VALUES
(9, 111, '2022-02-18'),
(10, 222, '2022-02-11');

-- --------------------------------------------------------

--
-- Structure de la table `livreur`
--

DROP TABLE IF EXISTS `livreur`;
CREATE TABLE IF NOT EXISTS `livreur` (
  `idLivreur` int(11) NOT NULL AUTO_INCREMENT,
  `disponibilité` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idLivreur`)
) ENGINE=InnoDB AUTO_INCREMENT=6254 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `livreur`
--

INSERT INTO `livreur` (`idLivreur`, `disponibilité`) VALUES
(111, NULL),
(222, NULL),
(6253, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `moderateur`
--

DROP TABLE IF EXISTS `moderateur`;
CREATE TABLE IF NOT EXISTS `moderateur` (
  `id_moderateur` int(11) NOT NULL,
  PRIMARY KEY (`id_moderateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `participation`
--

DROP TABLE IF EXISTS `participation`;
CREATE TABLE IF NOT EXISTS `participation` (
  `idParticipation` int(11) NOT NULL AUTO_INCREMENT,
  `idClient` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL,
  `nbrEtoile` int(11) NOT NULL,
  PRIMARY KEY (`idParticipation`),
  KEY `fk_participation_client` (`idClient`),
  KEY `fk_participation_event` (`idEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id_personne` int(11) NOT NULL,
  `nom` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `dateNaissance` date NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telephone` int(11) NOT NULL,
  PRIMARY KEY (`id_personne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id_personne`, `nom`, `prenom`, `dateNaissance`, `email`, `telephone`) VALUES
(111, 'marwa', 'ayari', '2001-02-02', 'maroua.ayari@esprit.tn', 54342461),
(222, 'cft', 'yugy', '2022-02-09', 'maroua.ayari@esprit.tn', 84562357),
(6253, 'zied', 'dridi', '2022-02-01', 'zied.dridi@esprit.tn', 78945612);

-- --------------------------------------------------------

--
-- Structure de la table `plan`
--

DROP TABLE IF EXISTS `plan`;
CREATE TABLE IF NOT EXISTS `plan` (
  `idPlan` int(11) NOT NULL AUTO_INCREMENT,
  `idStreamer` int(11) NOT NULL,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `duree` time NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `idEvenement` int(11) NOT NULL,
  PRIMARY KEY (`idPlan`,`idStreamer`),
  KEY `fk_plan_streamer` (`idStreamer`),
  KEY `fk_plan_evenement` (`idEvenement`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `reference` int(11) NOT NULL,
  `libelle` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `categorie` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prix` float NOT NULL,
  PRIMARY KEY (`reference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`reference`, `libelle`, `categorie`, `description`, `prix`) VALUES
(1010, 'aaaa', 'Clavier', 'testestest', 150.25),
(9512, 'RedDragonKumara', 'Clavier', 'RGBQ lights', 145),
(11111, 'aaaaa', 'moyen', 'dxrftgyhj', 100);

-- --------------------------------------------------------

--
-- Structure de la table `publication`
--

DROP TABLE IF EXISTS `publication`;
CREATE TABLE IF NOT EXISTS `publication` (
  `idPublication` int(11) NOT NULL AUTO_INCREMENT,
  `object` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `nbrVote` int(11) NOT NULL,
  `archive` tinyint(1) NOT NULL,
  `idClient` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`idPublication`),
  KEY `fk_publication_client` (`idClient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `streamer`
--

DROP TABLE IF EXISTS `streamer`;
CREATE TABLE IF NOT EXISTS `streamer` (
  `idStreamer` int(11) NOT NULL AUTO_INCREMENT,
  `informations` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lienStreaming` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idStreamer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `vote`
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
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `fk_avis_produit` FOREIGN KEY (`referenceProduit`) REFERENCES `produit` (`reference`);

--
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `fk_client` FOREIGN KEY (`idClient`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_client_commande` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `fk_commentaire` FOREIGN KEY (`idPublication`) REFERENCES `publication` (`idPublication`);

--
-- Contraintes pour la table `lignecommande`
--
ALTER TABLE `lignecommande`
  ADD CONSTRAINT `fk_ligne_commande` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`),
  ADD CONSTRAINT `fk_ligne_produit` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`reference`);

--
-- Contraintes pour la table `livraison`
--
ALTER TABLE `livraison`
  ADD CONSTRAINT `fk_livraison_commande` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`),
  ADD CONSTRAINT `fk_livraison_livreur` FOREIGN KEY (`idLivreur`) REFERENCES `livreur` (`idLivreur`);

--
-- Contraintes pour la table `livreur`
--
ALTER TABLE `livreur`
  ADD CONSTRAINT `fk_livreur` FOREIGN KEY (`idLivreur`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `moderateur`
--
ALTER TABLE `moderateur`
  ADD CONSTRAINT `fk_moderateur` FOREIGN KEY (`id_moderateur`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `participation`
--
ALTER TABLE `participation`
  ADD CONSTRAINT `fk_participation_client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`),
  ADD CONSTRAINT `fk_participation_event` FOREIGN KEY (`idEvent`) REFERENCES `evenement` (`reference`);

--
-- Contraintes pour la table `plan`
--
ALTER TABLE `plan`
  ADD CONSTRAINT `fk_plan_evenement` FOREIGN KEY (`idEvenement`) REFERENCES `evenement` (`reference`),
  ADD CONSTRAINT `fk_plan_streamer` FOREIGN KEY (`idStreamer`) REFERENCES `streamer` (`idStreamer`);

--
-- Contraintes pour la table `publication`
--
ALTER TABLE `publication`
  ADD CONSTRAINT `fk_publication_client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

--
-- Contraintes pour la table `streamer`
--
ALTER TABLE `streamer`
  ADD CONSTRAINT `fk_streamer_personne` FOREIGN KEY (`idStreamer`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `vote`
--
ALTER TABLE `vote`
  ADD CONSTRAINT `fk_vote_client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`),
  ADD CONSTRAINT `fk_vote_publication` FOREIGN KEY (`idPublication`) REFERENCES `publication` (`idPublication`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
