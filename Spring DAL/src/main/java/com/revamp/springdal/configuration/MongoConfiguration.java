package com.revamp.springdal.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${mongo.host}")
    public String host;

    @Value("${mongo.port}")
    public String port;

    @Value("${mongo.database}")
    public String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Primary
    @Override
    public MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString("mongodb://" + host + ":" + port + "/" + database + "");
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Primary
    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.revamp");
    }
}
