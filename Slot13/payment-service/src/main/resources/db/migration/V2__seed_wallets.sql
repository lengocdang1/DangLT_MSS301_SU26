-- V2__seed_wallets.sql
-- TODO-3: Nap du lieu mau vi - idempotent (MERGE = INSERT OR UPDATE)

-- Vi C1: so du 5000
MERGE INTO wallets AS target
USING (SELECT 'C1' AS customer_id, CAST(5000.00 AS DECIMAL(15,2)) AS balance) AS src
    ON target.customer_id = src.customer_id
WHEN MATCHED THEN
    UPDATE SET balance = src.balance
WHEN NOT MATCHED THEN
    INSERT (customer_id, balance) VALUES (src.customer_id, src.balance);

-- Vi C2: so du 100
MERGE INTO wallets AS target
USING (SELECT 'C2' AS customer_id, CAST(100.00 AS DECIMAL(15,2)) AS balance) AS src
    ON target.customer_id = src.customer_id
WHEN MATCHED THEN
    UPDATE SET balance = src.balance
WHEN NOT MATCHED THEN
    INSERT (customer_id, balance) VALUES (src.customer_id, src.balance);
