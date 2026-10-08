-- V1__init.sql: Create customers table
CREATE TABLE customers (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    email       NVARCHAR(255) NOT NULL UNIQUE,
    password    NVARCHAR(255) NOT NULL,
    full_name   NVARCHAR(255) NOT NULL,
    phone       NVARCHAR(20),
    role        NVARCHAR(50)  NOT NULL DEFAULT 'CUSTOMER',
    status      NVARCHAR(50)  NOT NULL DEFAULT 'ACTIVE',
    created_at  DATETIME2     DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME2     DEFAULT CURRENT_TIMESTAMP
);
