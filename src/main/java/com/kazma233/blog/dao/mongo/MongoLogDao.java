package com.kazma233.blog.dao.mongo;

import com.kazma233.blog.entity.mongo.MongoLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MongoLogDao extends MongoRepository<MongoLog, String> {

    List<MongoLog> findAllByVisitTimeBetween(Date start, Date end);

    List<MongoLog> findAllByVisitTimeBetweenAndPathLikeAndIpLikeOrderByVisitTimeDesc(Date start, Date end, String path, String ip);

    Long countAllByVisitTimeBetween(Date start, Date end);

}
