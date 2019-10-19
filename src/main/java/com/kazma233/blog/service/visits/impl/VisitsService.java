package com.kazma233.blog.service.visits.impl;

import com.kazma233.blog.dao.mongo.MongoLogDao;
import com.kazma233.blog.entity.log.MongoLog;
import com.kazma233.blog.entity.log.vo.MongoLogQuery;
import com.kazma233.blog.entity.log.vo.MongoWebsiteInfo;
import com.kazma233.blog.service.visits.IVisitsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Log4j2
@Service
public class VisitsService implements IVisitsService {

    private MongoLogDao mongoLogDao;
    private MongoTemplate mongoTemplate;

    @Override
    public void insert(MongoLog mongoLog) {
        mongoLogDao.save(mongoLog);
    }

    @Override
    public Page findLogs(MongoLogQuery mongoLogQueryVO) {

        return mongoLogDao.findAllByVisitTimeBetweenAndPathLikeAndIpLikeOrderByVisitTimeDesc(
                mongoLogQueryVO.getStart(),
                mongoLogQueryVO.getEnd(),
                mongoLogQueryVO.getPath(),
                mongoLogQueryVO.getIp(),
                buildPageRequest(mongoLogQueryVO.getPageNo(), mongoLogQueryVO.getPageSize())
        );
    }

    @Override
    public MongoWebsiteInfo websiteInfo(MongoLogQuery mongoLogQueryVO) {

        Long allViewSize = mongoLogDao.countAllByVisitTimeBetween(mongoLogQueryVO.getStart(), mongoLogQueryVO.getEnd());

        Criteria criteria = Criteria.where("visitTime").gt(mongoLogQueryVO.getStart()).lt(mongoLogQueryVO.getEnd());
        List<String> ips = mongoTemplate.findDistinct(new Query(criteria), "ip", MongoLog.class, String.class);

        return MongoWebsiteInfo.builder().allApiRequestSize(allViewSize).allIpRequestSize(ips.size()).build();
    }

    private PageRequest buildPageRequest(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("createTime")));
    }

}
