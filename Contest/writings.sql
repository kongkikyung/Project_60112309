CREATE DATABASE kong DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

GRANT ALL ON kong.* TO 'kid7258'@'localhost' IDENTIFIED BY '4789';

use kong;


CREATE TABLE writings (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	title VARCHAR(100) NOT NULL,
	userName VARCHAR(100) NOT NULL,
	text1 text NOT NULL
);

INSERT INTO Twritings VALUES (1, '1234', 'kikyung', 'come on.');
INSERT INTO Twritings VALUES (2, '1235', 'jihyun', 'come on.');
INSERT INTO Twritings VALUES (3, '1236', 'daehoon', 'come on.');
INSERT INTO Twritings VALUES (4, '1237', 'jinhyun', 'come on.');
INSERT INTO Twritings VALUES (5, '1238', 'gook', 'come on.');
INSERT INTO Twritings VALUES (6, '1239', 'hyunki', 'come on.');
INSERT INTO Twritings VALUES (7, '1240', 'hanbyul', 'come on.');
INSERT INTO Twritings VALUES (8, '1241', 'taehyung', 'come on.');
INSERT INTO Twritings VALUES (9, '1242', 'taejun', 'come on.');
INSERT INTO Twritings VALUES (10, '1243', 'seokho', 'come on.');
INSERT INTO Twritings VALUES (11, '1244', 'haemin', 'come on.');
INSERT INTO Twritings VALUES (12, '1245', 'dongjoo', 'come on.');
