-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 01, 2019 at 07:46 AM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `loghmeyab`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE IF NOT EXISTS `comments` (
  `comments_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `comments_comment` text CHARACTER SET utf8 NOT NULL,
  `comments_rate` tinyint(4) NOT NULL,
  `comments_sort` tinyint(5) NOT NULL DEFAULT '50',
  `comments_is_show` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`comments_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`comments_id`, `order_id`, `comments_comment`, `comments_rate`, `comments_sort`, `comments_is_show`) VALUES
(1, 1, 'خیلی خوب بود', 5, 50, 1),
(2, 1, 'بنظرم بد بود', 1, 50, 1);

-- --------------------------------------------------------

--
-- Table structure for table `foods`
--

DROP TABLE IF EXISTS `foods`;
CREATE TABLE IF NOT EXISTS `foods` (
  `food_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `place_id` int(11) NOT NULL,
  `menu_id` tinyint(4) NOT NULL,
  `type_id` tinyint(4) NOT NULL DEFAULT '0',
  `food_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `food_desc` text CHARACTER SET utf8 NOT NULL,
  `food_price` int(11) NOT NULL,
  `food_off` int(11) NOT NULL DEFAULT '0',
  `food_stock` tinyint(4) NOT NULL,
  `food_is_show` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`food_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `foods`
--

INSERT INTO `foods` (`food_id`, `place_id`, `menu_id`, `type_id`, `food_name`, `food_desc`, `food_price`, `food_off`, `food_stock`, `food_is_show`) VALUES
(1, 1, 3, 1, 'کباب کوبیده دوسیخ', '', 8000, 0, 10, 1),
(2, 1, 3, 2, 'کباب کوبیده تک سیخ', '', 4000, 0, 10, 1),
(3, 1, 3, 3, 'فلافل بزرگ', '', 8000, 0, 10, 1),
(4, 1, 2, 4, 'فلافل کوچک', '', 4000, 3500, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `kinds`
--

DROP TABLE IF EXISTS `kinds`;
CREATE TABLE IF NOT EXISTS `kinds` (
  `kind_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `kind_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `kind_sort` tinyint(2) NOT NULL,
  `kind_is_show` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`kind_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kinds`
--

INSERT INTO `kinds` (`kind_id`, `kind_name`, `kind_sort`, `kind_is_show`) VALUES
(1, 'رستوران', 1, 1),
(2, 'فست فود', 2, 1),
(3, 'شیرینی', 3, 1),
(4, 'کافه', 4, 1),
(5, 'صبحانه', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
CREATE TABLE IF NOT EXISTS `menus` (
  `menu_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `menu_sort` tinyint(4) NOT NULL DEFAULT '5',
  `menu_is_show` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menus`
--

INSERT INTO `menus` (`menu_id`, `menu_name`, `menu_sort`, `menu_is_show`) VALUES
(1, 'کباب', 1, 1),
(2, 'برگر', 1, 1),
(3, 'خوراک', 1, 1),
(4, 'ساندویچ', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `place_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `order_list` text CHARACTER SET utf8 NOT NULL,
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_have_comment` tinyint(1) NOT NULL DEFAULT '0',
  `order_price` int(11) NOT NULL,
  `order_off` int(11) NOT NULL,
  `order_price_final` int(11) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `place_id`, `user_id`, `order_list`, `order_date`, `order_have_comment`, `order_price`, `order_off`, `order_price_final`) VALUES
(1, 1, 1, '[\"sdc\",\"sdcsdc\",\"sdcsdc\",\"asasd\",\"dfvdfv\",\"sdc\",\"sdcsdc\",\"sdcsdc\",\"asasd\",\"dfvdfv\"]', '2019-09-23 11:09:36', 1, 10000, 0, 10000),
(2, 1, 2, '[\"sdc\",\"sdcsdc\",\"sdcsdc\",\"asasd\",\"dfvdfv\"]', '2019-09-23 11:09:36', 0, 20000, 0, 20000);

-- --------------------------------------------------------

--
-- Table structure for table `places`
--

DROP TABLE IF EXISTS `places`;
CREATE TABLE IF NOT EXISTS `places` (
  `place_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `kind_id` tinyint(3) UNSIGNED NOT NULL,
  `place_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `place_logo_url` varchar(200) CHARACTER SET utf8 NOT NULL,
  `place_courier` varchar(150) CHARACTER SET utf8 NOT NULL,
  `place_time_work` varchar(100) CHARACTER SET utf8 NOT NULL,
  `place_address` text CHARACTER SET utf8 NOT NULL,
  `place_geo` varchar(100) CHARACTER SET utf8 NOT NULL,
  `place_is_active` bit(1) NOT NULL,
  `place_number_of_comments` int(11) NOT NULL,
  `place_points` int(11) NOT NULL,
  `place_min_order` int(11) NOT NULL DEFAULT '20000',
  `place_time_ready` varchar(120) CHARACTER SET utf8 NOT NULL,
  `place_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `place_sort` tinyint(4) NOT NULL DEFAULT '100',
  `place_is_show` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`place_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`place_id`, `kind_id`, `place_name`, `place_logo_url`, `place_courier`, `place_time_work`, `place_address`, `place_geo`, `place_is_active`, `place_number_of_comments`, `place_points`, `place_min_order`, `place_time_ready`, `place_date`, `place_sort`, `place_is_show`) VALUES
(1, 1, 'خانه مرشدی', '', 'تا 4 کیلومتر 4000 تومان', 'ناهار 12-15  شام 20-24', 'میدان شورا', '12365412', b'1', 25, 118, 20000, '35 الی 45 دقیقه', '2019-09-23 09:22:16', 100, 1),
(2, 2, 'دایی علی', '', 'تا 4 کیلومتر 4000 تومان', 'ناهار 12-15  شام 20-24', 'میدان شورا', '12365412', b'1', 25, 118, 20000, '35 الی 45 دقیقه', '2019-09-10 09:22:16', 50, 1),
(3, 1, 'پاندا', '', 'تا 4 کیلومتر 4000 تومان', 'ناهار 12-15  شام 20-24', 'میدان شورا', '12365412', b'1', 25, 118, 20000, '35 الی 45 دقیقه', '2019-09-16 09:22:16', 20, 1),
(4, 3, 'مامانجون', '', 'تا 4 کیلومتر 4000 تومان', 'ناهار 12-15  شام 20-24', 'میدان شورا', '12365412', b'1', 25, 118, 20000, '35 الی 45 دقیقه', '2019-09-22 09:22:16', 80, 1);

-- --------------------------------------------------------

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
CREATE TABLE IF NOT EXISTS `types` (
  `type_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `type_image_url` text CHARACTER SET utf8 NOT NULL,
  `type_sort` tinyint(4) NOT NULL DEFAULT '10',
  `type_is_show` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `types`
--

INSERT INTO `types` (`type_id`, `type_name`, `type_image_url`, `type_sort`, `type_is_show`) VALUES
(1, 'شیرینی', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(2, 'صبحانه', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(3, 'ساندویچ', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(4, 'سوخاری', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(5, 'دریایی', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(6, 'سالاد', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(7, 'برگر', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(8, 'پیتزا', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(9, 'ایتالیایی', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(10, 'ایرانی', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(11, 'آبمیوه', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1),
(12, 'آمریکایی', 'http://192.168.1.103/loghmeyab/image/type_iran.png', 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_userId` varchar(20) NOT NULL,
  `user_mobile` varchar(15) NOT NULL,
  `user_verifyCode` int(11) NOT NULL,
  `user_date` datetime DEFAULT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `user_is_ok` varchar(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_userId`, `user_mobile`, `user_verifyCode`, `user_date`, `user_name`, `user_is_ok`) VALUES
(1, 'user4', '09225563221', 66611, '2019-06-02 17:42:29', 'ahmad', '1'),
(2, 'user1', '09132255221', 77210, '2019-06-02 21:23:03', 'alex', '1'),
(3, 'user2', '09225563221', 66611, '2019-06-02 17:42:29', 'ahmad', '1'),
(4, 'user3', '09333332525', 21934, '2019-05-22 20:02:36', 'fdfv', '1'),
(168, 'user5', '09333332525', 21934, '2019-05-22 20:02:36', 'fdfv', '1'),
(169, 'user1558539156172', '09909909999', 81521, '2019-06-03 16:09:28', 'yas', '1'),
(170, 'user1559904298571', '09111001000', 54137, '2019-06-07 15:26:31', 'yas', '1'),
(171, 'user1559904298573', '09111001000', 59990, '2019-06-07 15:27:04', 'yas', '1'),
(172, 'user1559908853392', '09131005555', 13928, '2019-06-07 16:30:53', 'ali', '1'),
(173, 'user1560111795773', '09132008080', 81567, '2019-06-10 00:53:16', 'ASXS', '1'),
(174, 'user1560667815987', '09131005000', 41638, '2019-06-16 11:20:16', 'ads', '1'),
(175, 'user1557695016822', '09132001001', 59686, '2019-06-21 12:45:59', 'adscsdcdc', '1'),
(176, 'user1561104958866', '09102003000', 76921, '2019-06-22 23:31:20', 'sdcsdcsdc', '1'),
(177, 'user1561298744796', '09375479301', 42006, '2019-06-23 18:35:45', 'ahmad', '1');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
