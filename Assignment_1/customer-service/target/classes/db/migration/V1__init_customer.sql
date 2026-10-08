CREATE TABLE customers (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(255) NOT NULL,
    phone NVARCHAR(20),
    role NVARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
    status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME2 DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME2 DEFAULT CURRENT_TIMESTAMP
);

-- Seed Customers (Password: 123456)
-- ID 1: ACTIVE
INSERT INTO customers (email, password, full_name, phone, role, status)
VALUES (N'an@gmail.com', N'$2a$10$8.UnVuG9HHgffUDAlk8qfOUVGkqRzgVymGe07xd00DMxs.AQubh4a', N'Nguyễn Văn An', N'0901234567', N'CUSTOMER', N'ACTIVE');

-- ID 2: ACTIVE
INSERT INTO customers (email, password, full_name, phone, role, status)
VALUES (N'binh@gmail.com', N'$2a$10$8.UnVuG9HHgffUDAlk8qfOUVGkqRzgVymGe07xd00DMxs.AQubh4a', N'Trần Thị Bình', N'0907654321', N'CUSTOMER', N'ACTIVE');

-- ID 3: INACTIVE (login returns 403 Forbidden)
INSERT INTO customers (email, password, full_name, phone, role, status)
VALUES (N'chi@gmail.com', N'$2a$10$8.UnVuG9HHgffUDAlk8qfOUVGkqRzgVymGe07xd00DMxs.AQubh4a', N'Lê Văn Chi', N'0909999888', N'CUSTOMER', N'INACTIVE');
