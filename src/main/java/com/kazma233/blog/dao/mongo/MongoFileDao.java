package com.kazma233.blog.dao.mongo;

import com.kazma233.blog.entity.mongo.MongoFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFileDao extends MongoRepository<MongoFile, String> {

}
