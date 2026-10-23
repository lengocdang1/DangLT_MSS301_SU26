-- V1__init_schema.sql
-- TODO-3: Tao bang wallets va payments cho SQL Server

-- Tao bang wallets neu chua ton tai
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'wallets')
BEGIN
    CREATE TABLE wallets (
        customer_id  VARCHAR(50)    NOT NULL PRIMARY KEY,
        balance      DECIMAL(15, 2) NOT NULL DEFAULT 0
    );
END;

-- Tao bang payments neu chua ton tai
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'payments')
BEGIN
    CREATE TABLE payments (
        id          BIGINT         NOT NULL IDENTITY(1,1) PRIMARY KEY,
        order_id    VARCHAR(100)   NOT NULL UNIQUE,
        customer_id VARCHAR(50)    NOT NULL,
        amount      DECIMAL(15, 2) NOT NULL,
        status      VARCHAR(20)    NOT NULL
    );
END;
