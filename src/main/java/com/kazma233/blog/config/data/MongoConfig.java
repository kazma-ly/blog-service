package com.kazma233.blog.config.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.kazma233.blog.dao.user", "com.kazma233.blog.dao.statistics"})
public class MongoConfig {
}
