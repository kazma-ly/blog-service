package com.kazma233.blog.dao.statistics;

import com.kazma233.blog.entity.statistics.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitDao extends MongoRepository<Visit, String> {
}
