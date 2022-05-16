-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 12, 2020 at 11:56 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_carta_forbice_sasso`
--

-- --------------------------------------------------------

--
-- Table structure for table `stats_partita`
--

CREATE TABLE `stats_partita` (
  `id_partita` int(11) NOT NULL,
  `data` date DEFAULT NULL,
  `vincitore` varchar(25) DEFAULT NULL,
  `perdente` varchar(25) DEFAULT NULL,
  `nRound` int(11) DEFAULT NULL,
  `tempo` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stats_partita`
--

INSERT INTO `stats_partita` (`id_partita`, `data`, `vincitore`, `perdente`, `nRound`, `tempo`) VALUES
(1, '2020-06-06', 'marchetto', 'paoletto', 3, '00:00:14'),
(2, '2020-06-06', 'marchetto', 'paoletto', 3, '00:00:14'),
(3, '2020-06-12', 'marchetto', 'paoletto', 3, '00:00:23'),
(4, '2020-06-12', 'marchetto', 'paoletto', 2, '00:01:59'),
(5, '2020-06-12', 'marchetto', 'paoletto', 2, '00:00:12'),
(6, '2020-06-12', 'marchetto', 'paoletto', 3, '00:00:17');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(25) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `logged` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `logged`) VALUES
('marchetto', 'a', 0),
('paoletto', 'b', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `stats_partita`
--
ALTER TABLE `stats_partita`
  ADD PRIMARY KEY (`id_partita`),
  ADD KEY `vincitore` (`vincitore`),
  ADD KEY `perdente` (`perdente`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `stats_partita`
--
ALTER TABLE `stats_partita`
  MODIFY `id_partita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `stats_partita`
--
ALTER TABLE `stats_partita`
  ADD CONSTRAINT `stats_partita_ibfk_1` FOREIGN KEY (`vincitore`) REFERENCES `users` (`username`),
  ADD CONSTRAINT `stats_partita_ibfk_2` FOREIGN KEY (`perdente`) REFERENCES `users` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
