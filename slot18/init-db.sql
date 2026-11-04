-- [TODO-2.1] Khởi tạo databases cho hệ thống Watch Management
-- Chạy với quyền sa hoặc sysadmin trên SQL Server
-- Thực thi: sqlcmd -S localhost -U sa -P YourStrong@Password123 -i init-db.sql

CREATE DATABASE DBAccount;
CREATE DATABASE DBBrand;
CREATE DATABASE DBWatch;
GO

-- Kích hoạt sa login (nếu dùng SQL Server Authentication)
ALTER LOGIN sa ENABLE;
ALTER LOGIN sa WITH PASSWORD = 'YourStrong@Password123';
GO

