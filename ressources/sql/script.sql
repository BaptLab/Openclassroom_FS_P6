CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(40),
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `ARTICLES` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(400),
  `content` TEXT,
  `theme` VARCHAR(40),
  `user_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`)
);

CREATE TABLE `THEMES` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(50),
  `description` VARCHAR(800)
);

CREATE TABLE `COMMENTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT,
  `article_id` INT,
  `content` TEXT,
  FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`),
  FOREIGN KEY (`article_id`) REFERENCES `ARTICLES` (`id`)
);

-- Insert fake values into USERS table
INSERT INTO `USERS` (`username`, `email`, `password`)
VALUES
  ('john_doe', 'john@example.com', 'hashed_password_1'),
  ('jane_smith', 'jane@example.com', 'hashed_password_2'),
  ('bob_jones', 'bob@example.com', 'hashed_password_3');

-- Insert fake values into ARTICLES table
INSERT INTO `ARTICLES` (`title`, `content`, `theme`, `user_id`)
VALUES
  ('Introduction to SQL', 'This is an introductory article about SQL.', 'Database', 1),
  ('Building a Spring Boot Application', 'Learn how to build a web app with Spring Boot.', 'Java', 2),
  ('The Art of Cooking', 'Explore the world of culinary arts.', 'Cooking', 3);

-- Insert fake values into THEMES table
INSERT INTO `THEMES` (`title`, `description`)
VALUES
  ('Database', 'Discussions about database systems and management.'),
  ('Java', 'Topics related to Java programming language.'),
  ('Cooking', 'Recipes, tips, and discussions about cooking.');

-- Insert fake values into COMMENTS table
INSERT INTO `COMMENTS` (`user_id`, `article_id`, `content`)
VALUES
  (2, 1, 'Great article! Very informative.'),
  (1, 2, 'I enjoyed reading about Spring Boot.'),
  (3, 3, 'The cooking tips are fantastic!');
