IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'cinema_customer')
BEGIN
    CREATE DATABASE cinema_customer;
END
GO
