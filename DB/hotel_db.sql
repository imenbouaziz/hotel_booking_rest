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
-- Database: `hotel_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` bigint(20) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `locality` varchar(255) DEFAULT NULL,
  `position_gps` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `country`, `city`, `street`, `number`, `locality`, `position_gps`) VALUES
(1, 'France', 'Paris', 'Rue de Rivoli', '1', 'Central', '48.8566,2.3522'),
(2, 'USA', 'New York', '5th Avenue', '725', 'Manhattan', '40.7128,-74.0060');

-- --------------------------------------------------------

--
-- Table structure for table `agency`
--

CREATE TABLE `agency` (
  `id` bigint(20) NOT NULL,
  `agency_name` varchar(255) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `agency`
--

INSERT INTO `agency` (`id`, `agency_name`, `login`, `password`) VALUES
(1, 'Travelers Inc', 'hello', 'hello'),
(2, 'Agency2', 'hello2', 'hello2');

-- --------------------------------------------------------

--
-- Table structure for table `availability`
--

CREATE TABLE `availability` (
  `id` bigint(20) NOT NULL,
  `room_id` bigint(20) NOT NULL,
  `start_availability` date NOT NULL,
  `end_availability` date NOT NULL,
  `is_booked` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`id`, `room_id`, `start_availability`, `end_availability`, `is_booked`) VALUES
(1, 1, '2024-11-01', '2024-11-30', 1),
(45, 3, '2024-11-01', '2024-11-30', 0),
(47, 1, '2024-11-01', '2024-11-04', 0),
(48, 1, '2024-11-11', '2024-11-30', 0),
(49, 1, '2024-11-01', '2024-11-10', 0),
(50, 1, '2024-11-17', '2024-11-30', 0);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL,
  `offer_id` bigint(20) DEFAULT NULL,
  `client_fname` varchar(255) DEFAULT NULL,
  `client_lname` varchar(255) DEFAULT NULL,
  `client_age` int(11) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `offer_id`, `client_fname`, `client_lname`, `client_age`, `total_price`, `start_date`, `end_date`) VALUES
(2, 2, 'Jane', 'Smith', 28, 255, '0000-00-00', '0000-00-00'),
(48, 1, 'John', 'Doe', 30, 900, '2024-11-05', '2024-11-10'),
(49, 1, 'John', 'Doe', 30, 900, '2024-11-11', '2024-11-16');

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `hotel_category` varchar(255) DEFAULT NULL,
  `stars_nb` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`id`, `address_id`, `hotel_name`, `hotel_category`, `stars_nb`) VALUES
(1, 1, 'Le Meurice', 'Luxury', 5),
(2, 1, 'The Plaza', 'Luxury', 4);

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `id` bigint(20) NOT NULL,
  `hotel_id` bigint(20) DEFAULT NULL,
  `agency_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `percentage` double DEFAULT NULL,
  `new_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `offer`
--

INSERT INTO `offer` (`id`, `hotel_id`, `agency_id`, `room_id`, `percentage`, `new_price`) VALUES
(1, 1, 1, 1, 10, 180),
(2, 2, 2, 3, 15, 127.5),
(3, 1, 1, 1, 10, 180),
(4, 2, 2, 3, 15, 127.5);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `hotel_id` bigint(20) DEFAULT NULL,
  `nb_beds` int(11) DEFAULT NULL,
  `price_per_night` double DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `hotel_id`, `nb_beds`, `price_per_night`, `capacity`) VALUES
(1, 1, 2, 200, 2),
(2, 1, 3, 300, 3),
(3, 2, 2, 150, 2),
(4, 2, 3, 50, 3);

-- --------------------------------------------------------

--
-- Table structure for table `room_images`
--

CREATE TABLE `room_images` (
  `room_id` bigint(20) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room_images`
--

INSERT INTO `room_images` (`room_id`, `image_url`) VALUES
(1, 'https://plus.unsplash.com/premium_photo-1661630735823-9e6d82ebcd74?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8aG90ZWxyb29tfGVufDB8fDB8fHww'),
(3, 'https://plus.unsplash.com/premium_photo-1661964151110-79a96d913e78?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8dGhlJTIwcGxhemF8ZW58MHx8MHx8fDA%3D');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `agency`
--
ALTER TABLE `agency`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `availability`
--
ALTER TABLE `availability`
  ADD PRIMARY KEY (`id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `offer_id` (`offer_id`);

--
-- Indexes for table `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `address_id` (`address_id`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hotel_id` (`hotel_id`),
  ADD KEY `agency_id` (`agency_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hotel_id` (`hotel_id`);

--
-- Indexes for table `room_images`
--
ALTER TABLE `room_images`
  ADD KEY `FKs41bv2s4er22lml5l9k9c3sys` (`room_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `agency`
--
ALTER TABLE `agency`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `availability`
--
ALTER TABLE `availability`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `offer`
--
ALTER TABLE `offer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `availability_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`);

--
-- Constraints for table `hotel`
--
ALTER TABLE `hotel`
  ADD CONSTRAINT `hotel_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`);

--
-- Constraints for table `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `offer_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`),
  ADD CONSTRAINT `offer_ibfk_2` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`id`),
  ADD CONSTRAINT `offer_ibfk_3` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Constraints for table `room_images`
--
ALTER TABLE `room_images`
  ADD CONSTRAINT `FKs41bv2s4er22lml5l9k9c3sys` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
