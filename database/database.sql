DROP SCHEMA IF EXISTS `prj301` ;

-- Create database prj301 if it doesn't exist
CREATE SCHEMA IF NOT EXISTS prj301;

-- Use the prj301 database
USE prj301;

-- Drop tables if they exist
SET @sql = '';

-- Generate DROP TABLE statements for all tables
SELECT GROUP_CONCAT('DROP TABLE IF EXISTS ', table_name, ';') 
INTO @sql
FROM information_schema.tables
WHERE table_schema = 'prj301';

-- Execute the generated SQL statement
-- Uncomment the line below when you want to execute the DROP TABLE statements
-- PREPARE stmt FROM @sql;
-- EXECUTE stmt;
-- DEALLOCATE PREPARE stmt;

-- Create the Account table
CREATE TABLE IF NOT EXISTS Account (
    AccountID INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Phone VARCHAR(50) NOT NULL,
    Address VARCHAR(50),
    Role INT NOT NULL -- 1: admin, 2: user, 3: staff
);

-- Create the Orders table
CREATE TABLE IF NOT EXISTS Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    AccountID INT NOT NULL,
    OrderDate DATE NOT NULL,
    Address VARCHAR(50) NOT NULL,
    TotalPrice INT NOT NULL,
    Status INT NOT NULL -- 1: Completed, 0: Cancelled 
);

-- Create the Category table
CREATE TABLE IF NOT EXISTS Category (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Image VARCHAR(500)
);

-- Create the Product table
CREATE TABLE IF NOT EXISTS Product (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Price INT NOT NULL,
    Description VARCHAR(50) NOT NULL,
    Image VARCHAR(500),
    CategoryID INT NOT NULL,
    Status INT NOT NULL DEFAULT 1 -- 0 delete -- 1: available, 2: not available
);

-- Create the OrderDetail table
CREATE TABLE IF NOT EXISTS OrderDetail (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL
);


	--------------------------- Insert data ---------------------------

	-- account
	insert into Account values ('duc@gmail.com', '1', 'minh duc', '0124727283', 'Ha Dong, Ha Noi', 2);
	insert into Account values ('long@abc.com', '123456', 'viet long', '0199928372', 'Nam Tu Liem, Ha Noi', 2);
	insert into Account values ('dung@fu.com', '123456', 'thi dung', '0123456789', '', 2);
	insert into Account values ('admin', '1', 'admin', '0123456789', '', 1);

	-- category
	insert into Category values ( 'Chicken', 'https://www.lotteria.vn/media/catalog/tmp/category/BG-Menu-Chicken-01-01_2.jpg');
	insert into Category values ('Burger', 'https://www.lotteria.vn/media/catalog/tmp/category/BG_New-02_4.jpg');
	insert into Category values ( 'Pizza', 'https://www.lotteria.vn/media/catalog/tmp/category/BG_New-10_1.jpg');
	insert into Category values ( 'Drink', '	https://www.lotteria.vn/media/catalog/tmp/category/Promotion-10_2.jpg');
	insert into Category values ( 'Combo', '	https://www.lotteria.vn/media/catalog/tmp/category/BG_New-05_1.jpg');

	-- type 1: Chicken
	insert into Product values ('Mala Chicken', 40, 'Chicken made by Mala', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/p/a/pack_loking_set_2.png', 1, 1);
	insert into Product values ('Mala Family', 100, 'Combo 3 Pcs Mala Chicken', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/c/h/chicken_ga_tuyet_vang.png', 1, 1);
	insert into Product values ('Chicken Cheese', 40, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/c/k/ck_set_milkis.png', 1, 1);
	insert into Product values ('Cheese Family', 40, 'Combo 6Pcs Chicken Cheese', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/m/i/milkis_-_c.thu_n_99.png', 1, 1);
	insert into Product values ('Grilled Chicken', 40, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/d/e/dessert-534x374px_squid-ring_2.png', 1, 1);

	-- type 2: Burger
	insert into Product values ('Beef Burger', 33, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/d/e/dessert-534x374px_chicken-skewer_2.png', 2, 1);
	insert into Product values ('Fish Burger', 40, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/v/a/value_mozzarella_1.png', 2, 1);
	insert into Product values ('Shrimp Burger', 48, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/v/a/value_fish_5.png', 2, 1);

	-- type 3: Pizza
	insert into Product values ('Pepperoni Pizza', 40, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/b/u/burger_beef-01_1.png', 3, 1);
	insert into Product values ( 'Cheese Pizza', 40, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/b/u/burger_fish-burger_2.png', 3, 1);
	insert into Product values ( 'Seafood Pizza', 40, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/r/_/r_s_com-gasotdau.png', 3, 1);

	-- type 4: Drink
	insert into Product values ( 'Coca Cola', 10, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/r/i/rice-spaghetti-534x374px._miy-thitbo_2.png', 4, 1);
	insert into Product values ( 'Pepsi', 10, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/z/e/zero_1.jpg', 4, 1);
	insert into Product values ( '7Up', 10, 'No description', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/z/e/zero.jpg', 4, 1);

	-- type 5: Combo
	insert into Product values ( 'LChicken Combo', 80, '01 LChicken Burger - 01 Fried Fries', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/p/a/pack_loking_set_2.png', 5, 1);
	insert into Product values ( 'Beef Combo', 49, '01 LChicken Burger - 01 Fried Fries', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/p/a/pack_loking_set_2.png', 5, 1);
	insert into Product values ( 'Fish Combo', 66, 'Fish Combo', 'https://dscnnwjxnwl3f.cloudfront.net/media/catalog/product/cache/2e1628f5f7131a9eb328ec1fb2c22fd3/p/a/pack_loking_set_2.png', 5, 1);

	-- order
	insert into Orders values (1, '2023-12-12', 'Ha Dong, Ha Noi', 300, 1);

	-- OrderDetail
	insert into OrderDetail values (1, 1, 2);
	insert into OrderDetail values (1, 5, 2);
	insert into OrderDetail values (1, 11, 1);