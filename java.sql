-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2023 at 02:43 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` varchar(255) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `total` float DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `date_created`, `date_updated`, `total`, `user_id`) VALUES
('e06e7e27-5a40-4d53-aa31-64fe41c1138a', '2023-11-29 20:33:40.000000', NULL, 4000000, '3c9c1247-2ec5-4dc1-b333-d05ef9c07dc4');

-- --------------------------------------------------------

--
-- Table structure for table `cart_detail`
--

CREATE TABLE `cart_detail` (
  `cart_id` varchar(255) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `date_created`, `date_updated`, `name`) VALUES
('1', '2023-11-10 01:31:00.000000', '2023-11-17 13:33:07.000000', 'Xiaomi Redmi Note 12 4GB 128GB'),
('2', '2023-11-10 01:31:00.000000', '2023-11-17 13:33:07.000000', 'iPhone 15 128GB'),
('3', '2023-11-10 01:31:00.000000', '2023-11-17 13:33:07.000000', 'iPhone 12 64GB'),
('4', '2023-11-17 13:35:51.000000', '2023-11-17 13:35:51.000000', 'iPhone 14 64GB');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `total` int(11) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id`, `address`, `date_created`, `date_updated`, `phone`, `total`, `user_id`) VALUES
('157b3a2c-f966-4803-a391-5283bcdd2d48', 'hcm', '2023-11-29 20:42:36.000000', NULL, '0862040542', 2000000, '3c9c1247-2ec5-4dc1-b333-d05ef9c07dc4');

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `order_id` varchar(255) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`order_id`, `product_id`, `date_created`, `date_updated`) VALUES
('157b3a2c-f966-4803-a391-5283bcdd2d48', '1', '2023-11-29 20:42:36.000000', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` varchar(255) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `category_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `brand`, `color`, `date_created`, `date_updated`, `name`, `price`, `quantity`, `category_id`) VALUES
('1', 'Xiaomi', 'red', '2023-11-29 20:36:51.000000', '2023-11-29 20:42:22.000000', 'Xiaomi Redmi Note 12 4GB 128GB', 2000000, 9, '1'),
('2', 'iPhone', 'green', '2023-11-30 20:38:33.000000', '2023-11-25 20:38:33.000000', 'iPhone 15 128GB', 1900000, 8, '2'),
('3', 'iPhone', 'deep purple', '2023-11-30 20:39:52.000000', '2023-11-17 13:35:51.000000', 'iPhone 12 64GB', 4900000, 2, '3'),
('4', 'iPhone', 'grey', '2023-11-24 20:39:52.000000', '2023-11-11 20:39:52.000000', 'iPhone 12 64GB', 3900000, 10, '4');

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `id` varchar(255) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` enum('BEARER') DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`id`, `date_created`, `date_updated`, `expired`, `revoked`, `token`, `token_type`, `user_id`) VALUES
('151d2e44-a08b-454b-b67b-349a1118725a', '2023-11-29 20:42:13.000000', NULL, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMTI2NTMzMywiZXhwIjoxNzAxMzUxNzMzfQ.Asb90K5igfQfFKgZqkQlKKZ2z_CYhd8xoTFGSuZwys8', 'BEARER', '3c9c1247-2ec5-4dc1-b333-d05ef9c07dc4'),
('1e98a772-d332-497b-b39a-ab54d89656db', '2023-11-29 20:33:46.000000', '2023-11-29 20:42:13.000000', b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMTI2NDgyNiwiZXhwIjoxNzAxMzUxMjI2fQ.WAYH2g3oVp8ZdvY_JEI3E7Gc1tbYRV30rc8Rvq6Nd5k', 'BEARER', '3c9c1247-2ec5-4dc1-b333-d05ef9c07dc4'),
('8bcf6a83-cc3a-4175-9edc-4b536b062258', '2023-11-29 20:33:40.000000', '2023-11-29 20:33:46.000000', b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMTI2NDgyMCwiZXhwIjoxNzAxMzUxMjIwfQ.Zvyj9CeLTmLew2nRP3sZAEUnrn8iZ_nNYz7mtvmPGjw', 'BEARER', '3c9c1247-2ec5-4dc1-b333-d05ef9c07dc4');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `date_created`, `date_updated`, `email`, `password`, `role`, `username`) VALUES
('3c9c1247-2ec5-4dc1-b333-d05ef9c07dc4', '2023-11-29 20:33:40.000000', NULL, 'admin@gmail.com', '$2a$10$3ZRWha./t1FO2ZYeBrx6eOU2ZBLGS00nvBCdDKRmun4AKuVQ0/8g2', 'USER', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`);

--
-- Indexes for table `cart_detail`
--
ALTER TABLE `cart_detail`
  ADD PRIMARY KEY (`cart_id`,`product_id`),
  ADD KEY `FK37hai783jhfcqo6h0pkiqmc9s` (`product_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcpl0mjoeqhxvgeeeq5piwpd3i` (`user_id`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`order_id`,`product_id`),
  ADD KEY `FKb8bg2bkty0oksa3wiq5mp5qnc` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_pddrhgwxnms2aceeku9s2ewy5` (`token`),
  ADD KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `cart_detail`
--
ALTER TABLE `cart_detail`
  ADD CONSTRAINT `FK37hai783jhfcqo6h0pkiqmc9s` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKrg4yopd2252nwj8bfcgq5f4jp` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `FKcpl0mjoeqhxvgeeeq5piwpd3i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKlb8mofup9mi791hraxt9wlj5u` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Constraints for table `token`
--
ALTER TABLE `token`
  ADD CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
