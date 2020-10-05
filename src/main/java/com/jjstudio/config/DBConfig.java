package com.jjstudio.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.jjstudio.resource")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Configuration
public class DBConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getRequiredProperty("spring.data.mongodb.database");
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(env.getRequiredProperty("spring.data.mongodb.uri"));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .build();
        return MongoClients.create(mongoClientSettings);
    }
}
