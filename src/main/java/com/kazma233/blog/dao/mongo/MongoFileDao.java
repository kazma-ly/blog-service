package com.kazma233.blog.dao.mongo;

import com.kazma233.blog.entity.log.MongoFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoFileDao extends MongoRepository<MongoFile, String> {

    Optional<MongoFile> findMongoFileByUid(String uid);

    void deleteMongoFileByUid(String uid);

}
