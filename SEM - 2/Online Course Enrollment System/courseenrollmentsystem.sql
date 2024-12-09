-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 23, 2024 at 11:33 PM
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
-- Database: `courseenrollmentsystem`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_instructor` (IN `instructor_ID` INT)   BEGIN
Delete from instructors where instructorID=instructor_ID;
Delete from users where userID=instructor_ID;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_student` (IN `student_ID` INT)   BEGIN
Delete from students where studentID=student_ID;
Delete from users where userID=student_ID;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `confirm_student`
-- (See below for the actual view)
--
CREATE TABLE `confirm_student` (
`studentID` int(11)
,`enrolledCourseCode` varchar(20)
,`marks` decimal(10,0)
,`mentorID` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `courseCode` varchar(20) NOT NULL,
  `courseName` varchar(50) DEFAULT NULL,
  `courseFee` decimal(10,0) DEFAULT NULL,
  `maxCapacity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`courseCode`, `courseName`, `courseFee`, `maxCapacity`) VALUES
('CS101', 'Introduction to Computer Science', 500, 30),
('CS102', 'Advanced Programming', 600, 25),
('MATH101', 'Calculus I', 400, 40),
('MATH201', 'Statistics', 450, 35);

-- --------------------------------------------------------

--
-- Table structure for table `instructors`
--

CREATE TABLE `instructors` (
  `instructorID` int(11) NOT NULL,
  `teachingCourseCode` varchar(20) DEFAULT NULL,
  `teachingExperience` int(11) DEFAULT NULL,
  `specialization` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructors`
--

INSERT INTO `instructors` (`instructorID`, `teachingCourseCode`, `teachingExperience`, `specialization`) VALUES
(11, 'CS101', 5, 'Computer Science'),
(16, 'CS102', 8, 'Software Engineering'),
(18, 'MATH201', 7, 'Statistics');

--
-- Triggers `instructors`
--
DELIMITER $$
CREATE TRIGGER `delete_instructor` AFTER DELETE ON `instructors` FOR EACH ROW BEGIN
INSERT into instructors_backup values (old.instructorID, old.teachingCourseCode, old.teachingExperience, old.specialization);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `instructors_backup`
--

CREATE TABLE `instructors_backup` (
  `instructorID` int(11) NOT NULL,
  `teachingCourseCode` varchar(20) DEFAULT NULL,
  `teachingExperience` int(11) DEFAULT NULL,
  `specialization` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructors_backup`
--

INSERT INTO `instructors_backup` (`instructorID`, `teachingCourseCode`, `teachingExperience`, `specialization`) VALUES
(13, NULL, 10, 'Mathematics');

-- --------------------------------------------------------

--
-- Table structure for table `lists`
--

CREATE TABLE `lists` (
  `courseCode` varchar(20) NOT NULL,
  `enrolledStudents` longtext DEFAULT NULL,
  `confirmedList` longtext DEFAULT NULL,
  `waitingList` longtext DEFAULT NULL,
  `rejectedList` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lists`
--

INSERT INTO `lists` (`courseCode`, `enrolledStudents`, `confirmedList`, `waitingList`, `rejectedList`) VALUES
('CS101', 'Course ID: CS101\r\n\r\n---------------------------------------------\r\nStudent ID: 10\r\nStudent Name: John Doe\r\nStudent Marks: 86.0\r\n---------------------------------------------\r\n\r\nStudent ID: 15\r\nStudent Name: David Wilson\r\nStudent Marks: 88.0\r\n---------------------------------------------\r\n', 'Course ID: CS101\r\n\r\n---------------------------------------------\r\nStudent ID: 10\r\nStudent Name: John Doe\r\nStudent Marks: 86.0\r\n---------------------------------------------\r\n\r\nStudent ID: 15\r\nStudent Name: David Wilson\r\nStudent Marks: 88.0\r\n---------------------------------------------\r\n', 'Course ID: CS101\r\n\r\n---------------------------------------------\r\nStudent ID: 10\r\nStudent Name: John Doe\r\nStudent Marks: 86.0\r\n---------------------------------------------\r\n\r\nStudent ID: 15\r\nStudent Name: David Wilson\r\nStudent Marks: 88.0\r\n---------------------------------------------\r\n', 'Course ID: CS101\r\n\r\n---------------------------------------------\r\nStudent ID: 10\r\nStudent Name: John Doe\r\nStudent Marks: 86.0\r\n---------------------------------------------\r\n\r\nStudent ID: 15\r\nStudent Name: David Wilson\r\nStudent Marks: 88.0\r\n---------------------------------------------\r\n'),
('CS102', 'Course ID: CS102\r\n\r\n---------------------------------------------\r\nStudent ID: 17\r\nStudent Name: Frank Lewis\r\nStudent Marks: 82.0\r\n---------------------------------------------\r\n', 'Course ID: CS102\r\n\r\n---------------------------------------------', 'Course ID: CS102\r\n\r\n---------------------------------------------', 'Course ID: CS102\r\n\r\n---------------------------------------------'),
('MATH101', 'Course ID: MATH101\r\n\r\n---------------------------------------------\r\nStudent ID: 12\r\nStudent Name: Alice Johnson\r\nStudent Marks: 78.0\r\n---------------------------------------------\r\n', 'Course ID: MATH101\r\n\r\n---------------------------------------------\r\nStudent ID: 12\r\nStudent Name: Alice Johnson\r\nStudent Marks: 78.0\r\n---------------------------------------------\r\n', 'Course ID: MATH101\r\n\r\n---------------------------------------------\r\nStudent ID: 12\r\nStudent Name: Alice Johnson\r\nStudent Marks: 78.0\r\n---------------------------------------------\r\n', 'Course ID: MATH101\r\n\r\n---------------------------------------------\r\nStudent ID: 12\r\nStudent Name: Alice Johnson\r\nStudent Marks: 78.0\r\n---------------------------------------------\r\n'),
('MATH201', 'Course ID: MATH201\r\n\r\n---------------------------------------------\r\nStudent ID: 19\r\nStudent Name: Hannah Scott\r\nStudent Marks: 91.0\r\n---------------------------------------------\r\n', 'Course ID: MATH201\r\n\r\n---------------------------------------------\r\nStudent ID: 19\r\nStudent Name: Hannah Scott\r\nStudent Marks: 91.0\r\n---------------------------------------------\r\n', 'Course ID: MATH201\r\n\r\n---------------------------------------------\r\nStudent ID: 19\r\nStudent Name: Hannah Scott\r\nStudent Marks: 91.0\r\n---------------------------------------------\r\n', 'Course ID: MATH201\r\n\r\n---------------------------------------------\r\nStudent ID: 19\r\nStudent Name: Hannah Scott\r\nStudent Marks: 91.0\r\n---------------------------------------------\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `studentID` int(11) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `marks` decimal(10,0) DEFAULT NULL,
  `enrolledCourseCode` varchar(20) DEFAULT NULL,
  `enrollStatus` varchar(15) NOT NULL DEFAULT 'Not Enrolled',
  `paymentStatus` tinyint(1) DEFAULT 0,
  `mentorID` int(11) NOT NULL DEFAULT 0,
  `transactionID` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentID`, `address`, `marks`, `enrolledCourseCode`, `enrollStatus`, `paymentStatus`, `mentorID`, `transactionID`) VALUES
(10, '123 Elm St', 86, 'CS101', 'Confirm', 1, 11, 1004),
(12, '456 Oak St', 78, 'MATH101', 'Pending', 0, 0, 0),
(14, '789 Pine St', 90, NULL, 'Not Enrolled', 0, 0, 0),
(15, '321 Maple St', 88, 'CS101', 'Cancelled', 0, 0, 0),
(17, '654 Cedar St', 82, 'CS102', 'Cancelled', 0, 0, 0),
(19, '987 Birch St', 91, 'MATH201', 'Pending', 0, 0, 0);

--
-- Triggers `students`
--
DELIMITER $$
CREATE TRIGGER `delete_student` AFTER DELETE ON `students` FOR EACH ROW BEGIN
Insert into students_backup VALUES (old.studentID, old.address, old.marks, old.enrolledCourseCode, old.enrollStatus);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `students_backup`
--

CREATE TABLE `students_backup` (
  `studentID` int(11) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `marks` decimal(10,0) DEFAULT NULL,
  `enrolledCourseCode` varchar(20) DEFAULT NULL,
  `enrollStatus` varchar(15) NOT NULL DEFAULT 'Not Enrolled'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transactionID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `amount` double NOT NULL,
  `date` date NOT NULL,
  `paymentMethod` varchar(14) NOT NULL,
  `number` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transactionID`, `studentID`, `amount`, `date`, `paymentMethod`, `number`) VALUES
(1001, 10, 500, '2024-08-22', 'Card', 1234567812345678),
(1002, 19, 450, '2024-08-23', 'Net-banking', 123456789012),
(1003, 15, 500, '2024-08-25', 'Card', 2345678923456789),
(1004, 10, 500, '2024-08-24', 'Net-banking', 123465789012);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `contactNumber` varchar(10) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `password`, `name`, `email`, `contactNumber`, `role`) VALUES
(1, '001@MeetAD', 'Meet', 'meet@mail.com', '9876543210', 'Admin'),
(10, '10@John', 'John Doe', 'john.doe@example.com', '1234567890', 'Student'),
(11, '11@Jane', 'Jane Smith', 'jane.smith@example.com', '2345678901', 'Instructor'),
(12, '12@Alice', 'Alice Johnson', 'alice.johnson@example.com', '3456789012', 'Student'),
(14, '14@Charlie', 'Charlie Green', 'charlie.green@example.com', '5678901234', 'Student'),
(15, '15@David', 'David Wilson', 'david.wilson@example.com', '6789012345', 'Student'),
(16, '16@Emily', 'Emily Clark', 'emily.clark@example.com', '7890123456', 'Instructor'),
(17, '17@Frank', 'Frank Lewis', 'frank.lewis@example.com', '8901234567', 'Student'),
(18, '18@Grace', 'Grace Adams', 'grace.adams@example.com', '9012345678', 'Instructor'),
(19, '19@Hannah', 'Hannah Scott', 'hannah.scott@example.com', '0123456789', 'Student');

-- --------------------------------------------------------

--
-- Structure for view `confirm_student`
--
DROP TABLE IF EXISTS `confirm_student`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `confirm_student`  AS SELECT `students`.`studentID` AS `studentID`, `students`.`enrolledCourseCode` AS `enrolledCourseCode`, `students`.`marks` AS `marks`, `students`.`mentorID` AS `mentorID` FROM (`students` join `users` on(`students`.`studentID` = `users`.`userID`)) WHERE `students`.`enrollStatus` = 'Confirm' ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`courseCode`);

--
-- Indexes for table `instructors`
--
ALTER TABLE `instructors`
  ADD PRIMARY KEY (`instructorID`);

--
-- Indexes for table `lists`
--
ALTER TABLE `lists`
  ADD PRIMARY KEY (`courseCode`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`studentID`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1005;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
