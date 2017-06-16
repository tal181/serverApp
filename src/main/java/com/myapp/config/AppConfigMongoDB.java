package com.myapp.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by Tal on 16/04/2017.
 */
@Configuration
@PropertySource("classpath:mongo.properties")
public class AppConfigMongoDB {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private String port;

    @Value("${mongo.scheme}")
    private String scheme;

    @Bean
    public MongoClient mongoConfig() throws Exception{
        MongoClientOptions mongoOptions =
                new MongoClientOptions.Builder().maxWaitTime(1000 * 60 * 5).build();
        MongoClient mongo = new MongoClient(host, mongoOptions);

        return mongo;
    }
    @Bean
    public MongoTemplate mongoClient() throws Exception{
        MongoClient mongo = mongoConfig();
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, scheme);
        return new MongoTemplate(mongoDbFactory);

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}



