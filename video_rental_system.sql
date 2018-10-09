-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 10, 2018 at 01:28 AM
-- Server version: 5.6.25
-- PHP Version: 5.5.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `video_rental_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` bigint(20) unsigned NOT NULL,
  `first_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `first_name`, `last_name`, `phone`, `address`) VALUES
(1, 'BIng BIng', 'Zhao', '+1 (504) 303-8311', '1000 N. 4th St. Fairfield, Iowa 52557 MR#11'),
(2, 'Arun', 'Magar', '+1 (818) 472-3806', '1000 N. 4th St. Fairfield, Iowa 52557 MR#62'),
(3, 'Kiran', 'Khaniya', '+1 (641) 541-3334', '1000 N. 4th St. Fairfield, Iowa 52557 MR#55'),
(5, 'Sunil', 'Tandukar', '98656365', 'blah'),
(6, 'Ram', 'than', '9866', 'asd');

-- --------------------------------------------------------

--
-- Table structure for table `rental_record`
--

CREATE TABLE IF NOT EXISTS `rental_record` (
  `id` bigint(20) unsigned NOT NULL,
  `staff_id` bigint(20) unsigned NOT NULL,
  `video_id` bigint(30) NOT NULL,
  `customer_id` bigint(20) unsigned NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expected_return_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actual_return_date` timestamp NULL DEFAULT NULL,
  `fine_amount` decimal(10,2) NOT NULL DEFAULT '0.00'
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `rental_record`
--

INSERT INTO `rental_record` (`id`, `staff_id`, `video_id`, `customer_id`, `date`, `expected_return_date`, `actual_return_date`, `fine_amount`) VALUES
(90, 3, 2, 1, '2018-04-02 05:00:00', '2018-04-10 05:00:00', NULL, '0.00'),
(91, 3, 1, 1, '2018-04-09 05:00:00', '2018-04-18 05:00:00', NULL, '0.00'),
(93, 3, 5, 3, '2018-04-03 05:00:00', '2018-04-10 05:00:00', NULL, '0.00'),
(94, 3, 6, 3, '2018-04-14 05:00:00', '2018-05-17 05:00:00', NULL, '0.00'),
(95, 3, 3, 2, '2018-10-02 05:00:00', '2018-10-24 05:00:00', NULL, '0.00');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `id` bigint(20) unsigned NOT NULL,
  `account_username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `account_password` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `salary` decimal(10,2) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `account_username`, `account_password`, `first_name`, `last_name`, `phone`, `address`, `salary`, `role`) VALUES
(3, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Johnny', 'Smith', '+1 (818) 345-875', '192 Mariposa Creek Way, Porter Ranch, CA 19873', '200000.00', 0),
(5, 'sdf', '827ccb0eea8a706c4c34a16891f84e7b', 'as', 'asd', 'ads', 'asd', '10000.00', 1),
(6, 'kiran', '827ccb0eea8a706c4c34a16891f84e7b', 'kiran', 'kiran', '121312312312', 'qdqwe', '12312.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE IF NOT EXISTS `videos` (
  `id` bigint(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `rating` enum('1','2','3','4','5') NOT NULL,
  `genre` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `director` varchar(200) NOT NULL,
  `rentalFee` decimal(5,2) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `videos`
--

INSERT INTO `videos` (`id`, `name`, `rating`, `genre`, `description`, `director`, `rentalFee`, `price`, `status`) VALUES
(1, 'Thor Ragnarok', '5', 'Superhero', 'Babball', 'Arun ', '5.00', '50.00', 0),
(2, 'Spiderman', '3', 'a', 'a', 's', '2.00', '45.00', 0),
(3, 'Star Wars', '4', 'a', '', 'a', '2.00', '2.00', 0),
(4, 'megamind 4', '4', 'a', '', 'a', '2.00', '2.00', 1),
(5, 'The Invincible', '3', 'Animation', 'Superhero family related movie', 'xyz', '10.00', '50.00', 0),
(6, 'Yes Man', '5', 'Comedy', '', 'pqr', '2.00', '12.00', 0),
(7, 'Predestination', '2', 'Sci Fi', 'asd', 'asd', '10.00', '60.00', 1),
(8, 'Triangle', '2', 'Horror', 'this is something about this movie', 'uvw', '4.00', '32.00', 1),
(9, 'The Mask', '5', 'Comedy', 'asda', 'kpk', '2.00', '40.00', 1),
(10, 'Iron Man', '5', 'Superhero', 'Awesome Movie', 'l', '5.00', '45.00', 1),
(11, 'Forest Gump', '4', 'Drama', 'Man making money without knowing.. running without thinking', 'zz', '4.00', '45.00', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `rental_record`
--
ALTER TABLE `rental_record`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `fk_staff_id` (`staff_id`),
  ADD KEY `fk_car_id` (`video_id`),
  ADD KEY `fk_customer_id` (`customer_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `rental_record`
--
ALTER TABLE `rental_record`
  MODIFY `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=96;
--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `videos`
--
ALTER TABLE `videos`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
