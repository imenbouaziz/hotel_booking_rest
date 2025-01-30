-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 24, 2024 at 10:55 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `agency_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `agencies`
--

CREATE TABLE `agencies` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `base_uri` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `agencies`
--

INSERT INTO `agencies` (`id`, `name`, `login`, `password`, `base_uri`) VALUES
(1, 'Travelers Inc', 'hello', 'hello', 'http://localhost:8081/\r\n'),
(2, 'Agency2', 'hello2', 'hello2', '');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL,
  `offer_id` bigint(20) NOT NULL,
  `client_id` bigint(20) NOT NULL,
  `client_fname` varchar(255) NOT NULL,
  `client_lname` varchar(255) NOT NULL,
  `client_age` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `offer_id`, `client_id`, `client_fname`, `client_lname`, `client_age`, `total_price`, `start_date`, `end_date`) VALUES
(54, 1, 1, 'John', 'Doe', 30, 900, '2024-11-05', '2024-11-10'),
(55, 1, 1, 'John', 'Doe', 30, 900, '2024-11-05', '2024-11-10'),
(56, 1, 1, 'John', 'Doe', 30, 900, '2024-11-11', '2024-11-16');

-- --------------------------------------------------------

--
-- Table structure for table `card_info`
--

CREATE TABLE `card_info` (
  `id` bigint(20) NOT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `card_holder` varchar(255) NOT NULL,
  `expiration_date` varchar(255) DEFAULT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `card_info`
--

INSERT INTO `card_info` (`id`, `card_number`, `card_holder`, `expiration_date`, `cvv`, `client_id`) VALUES
(1, '1234567890123456', 'John Doe', '12/25', '123', 1),
(11, '1234567891234567', 'mr smith', '12/12', '123', 1);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `first_name`, `last_name`, `age`) VALUES
(1, 'John', 'Doe', 30),
(2, 'Jane', 'Smith', 25);

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `id` bigint(20) NOT NULL,
  `hotel_id` bigint(20) NOT NULL,
  `agency_id` bigint(20) NOT NULL,
  `room_id` bigint(20) NOT NULL,
  `percentage` double NOT NULL,
  `new_price` double NOT NULL,
  `hotel_name` varchar(255) NOT NULL,
  `hotel_stars` int(11) NOT NULL,
  `room_image_urls` varbinary(255) DEFAULT NULL,
  `agency_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `offer`
--

INSERT INTO `offer` (`id`, `hotel_id`, `agency_id`, `room_id`, `percentage`, `new_price`, `hotel_name`, `hotel_stars`, `room_image_urls`, `agency_name`) VALUES
(1, 1, 1, 1, 10, 180, 'Le Meurice', 5, NULL, NULL),
(2, 2, 2, 3, 15, 127.5, 'The Plaza', 4, 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078, 'Agency2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agencies`
--
ALTER TABLE `agencies`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `offer_id` (`offer_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `card_info`
--
ALTER TABLE `card_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_card_number` (`card_number`),
  ADD KEY `fk_client` (`client_id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `hotel_id` (`hotel_id`,`agency_id`,`room_id`),
  ADD KEY `agency_id` (`agency_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agencies`
--
ALTER TABLE `agencies`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `card_info`
--
ALTER TABLE `card_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `offer`
--
ALTER TABLE `offer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Constraints for table `card_info`
--
ALTER TABLE `card_info`
  ADD CONSTRAINT `fk_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `offer_ibfk_1` FOREIGN KEY (`agency_id`) REFERENCES `agencies` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
