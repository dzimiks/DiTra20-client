package com.example.si_broker.config;

import com.example.si_broker.utils.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.si_broker.repositories")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(
                Constants.MONGO_DB_NAME,
                Constants.MONGO_DB_NAME,
                Constants.MONGO_DB_PASSWORD.toCharArray()
        );
        ServerAddress serverAddress = new ServerAddress(Constants.DB_HOST, Constants.MONGO_DB_PORT);
        MongoClientOptions options = MongoClientOptions.builder().build();
        return new MongoClient(serverAddress, credential, options);
    }

    @Override
    protected String getDatabaseName() {
        return Constants.MONGO_DB_NAME;
    }
}
