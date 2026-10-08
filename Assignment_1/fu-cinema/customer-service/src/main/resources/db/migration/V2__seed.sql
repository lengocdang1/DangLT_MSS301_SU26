-- V2__seed.sql: Seed test customers
-- Password for all: 123456 (BCrypt encoded)
INSERT INTO customers (email, password, full_name, phone, role, status) VALUES
(N'an@gmail.com',   N'$2a$10$8.UnVuG9HHgffUDAlk8qfOUVGkqRzgVymGe07xd00DMxs.AQubh4a', N'Nguyen Van An',  N'0901234567', N'CUSTOMER', N'ACTIVE'),
(N'binh@gmail.com', N'$2a$10$8.UnVuG9HHgffUDAlk8qfOUVGkqRzgVymGe07xd00DMxs.AQubh4a', N'Tran Thi Binh', N'0907654321', N'CUSTOMER', N'ACTIVE'),
(N'chi@gmail.com',  N'$2a$10$8.UnVuG9HHgffUDAlk8qfOUVGkqRzgVymGe07xd00DMxs.AQubh4a', N'Le Van Chi',   N'0909999888', N'CUSTOMER', N'INACTIVE');
