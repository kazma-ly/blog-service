package com.kazma233.blog.dao.mongo;

import com.kazma233.blog.entity.log.MongoLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MongoLogDao extends PagingAndSortingRepository<MongoLog, String> {

    List<MongoLog> findAllByVisitTimeBetween(Date start, Date end);

    Page<MongoLog> findAllByVisitTimeBetweenAndPathLikeAndIpLikeOrderByVisitTimeDesc(Date start, Date end, String path, String ip, PageRequest pageRequest);

    Long countAllByVisitTimeBetween(Date start, Date end);

}
