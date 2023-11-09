IF EXISTS (SELECT 1 FROM sys.schemas WHERE name = N'prn_shop')
BEGIN
    EXEC('DROP SCHEMA prn_shop');
END

-- Create the schema if it doesn't exist
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = N'prn_shop')
BEGIN
    EXEC('CREATE SCHEMA prn_shop');
END

-- Use the prn_shop schema
USE prn_shop;

-- Drop tables if they exist
DECLARE @tableName NVARCHAR(128);

DECLARE tableCursor CURSOR FOR
    SELECT t.name
    FROM sys.tables t
    INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
    WHERE s.name = 'prn_shop';

OPEN tableCursor;

FETCH NEXT FROM tableCursor INTO @tableName;

WHILE @@FETCH_STATUS = 0
BEGIN
    DECLARE @dropTableSQL NVARCHAR(MAX);
    SET @dropTableSQL = 'DROP TABLE prn_shop.' + QUOTENAME(@tableName);
    EXEC sp_executesql @dropTableSQL;
    FETCH NEXT FROM tableCursor INTO @tableName;
END

CLOSE tableCursor;
DEALLOCATE tableCursor;

-- Create the Staff table
CREATE TABLE dbo.Staff (
    StaffID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL,
    Phone NVARCHAR(50) NOT NULL,
    Dob DATE NOT NULL,
    Address NVARCHAR(50),
    Gender BIT
);

-- Create the Customer table
CREATE TABLE dbo.Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL,
    Phone NVARCHAR(50) NOT NULL,
    Address NVARCHAR(50)
);

-- Create the Orders table
CREATE TABLE dbo.Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    StaffID INT NOT NULL,
    CustomerID INT NOT NULL,
    OrderDate DATE NOT NULL,
    TotalPrice INT NOT NULL,
    FOREIGN KEY (StaffID) REFERENCES dbo.Staff(StaffID),
    FOREIGN KEY (CustomerID) REFERENCES dbo.Customer(CustomerID)
);

-- Create the Category table
CREATE TABLE dbo.Category (
    CategoryID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

-- Create the Product table
CREATE TABLE dbo.Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL,
    Price INT NOT NULL,
    Description NVARCHAR(50) NOT NULL,
    CategoryID INT NOT NULL,
    FOREIGN KEY (CategoryID) REFERENCES dbo.Category(CategoryID)
);

-- Create the OrderDetail table
CREATE TABLE dbo.OrderDetail (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    UnitPrice INT NOT NULL,
    Discount INT,
    TotalPrice INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES dbo.Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES dbo.Product(ProductID)
);


	--------------------------- Insert data ---------------------------

	-- Insert data into the Staff table
INSERT INTO dbo.Staff (Name, Phone, Dob, Address, Gender)
VALUES ('John Doe', '123-456-7890', '1980-05-15', '123 Main St', 1),
       ('Jane Smith', '555-123-4567', '1990-03-20', '456 Elm St', 0);

-- Insert data into the Customer table
INSERT INTO dbo.Customer (Name, Phone, Address)
VALUES ('Alice Johnson', '555-987-6543', '789 Oak St'),
       ('Bob Wilson', '111-222-3333', '321 Pine St');

-- Insert data into the Orders table
INSERT INTO dbo.Orders (StaffID, CustomerID, OrderDate, TotalPrice)
VALUES (1, 1, '2023-11-05', 500),
       (2, 2, '2023-11-06', 750);

-- Insert data into the Category table
INSERT INTO dbo.Category (Name)
VALUES ('Electronics'),
       ('Clothing');

-- Insert data into the Product table
INSERT INTO dbo.Product (Name, Price, Description, CategoryID)
VALUES ('Smartphone', 600, 'Latest smartphone model', 1),
       ('Laptop', 1000, 'High-performance laptop', 1),
       ('T-shirt', 20, 'Cotton t-shirt', 2),
       ('Jeans', 50, 'Blue jeans', 2);

-- Insert data into the OrderDetail table
INSERT INTO dbo.OrderDetail (OrderID, ProductID, Quantity, UnitPrice, Discount, TotalPrice)
VALUES (1, 1, 2, 600, 0, 1200),
       (1, 2, 1, 1000, 0, 1000),
       (2, 3, 3, 20, 0, 60),
       (2, 4, 2, 50, 0, 100);