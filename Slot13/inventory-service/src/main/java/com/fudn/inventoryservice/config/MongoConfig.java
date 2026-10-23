package com.fudn.inventoryservice.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * TODO-2: Khai báo MongoTransactionManager để @Transactional hoạt động với MongoDB.
 * Yêu cầu MongoDB phải chạy ở chế độ replica set (rs0).
 */
@Configuration
public class MongoConfig {

    /**
     * MongoTransactionManager bắt buộc để dùng @Transactional trên MongoDB.
     * Spring Data MongoDB tự auto-configure MongoDatabaseFactory từ application.properties.
     */
    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
