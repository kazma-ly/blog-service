package com.kazma233.blog.service.statistics.impl;

import com.kazma233.blog.dao.mongo.MongoLogDao;
import com.kazma233.blog.service.statistics.IStatisticsService;
import com.kazma233.blog.entity.log.vo.MongoLogQueryVO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class StatisticsService implements IStatisticsService {

    private MongoLogDao mongoLogDao;

    @Override
    public Page findLogs(MongoLogQueryVO mongoLogQueryVO) {

        return mongoLogDao.findAllByVisitTimeBetweenAndPathLikeAndIpLikeOrderByVisitTimeDesc(
                mongoLogQueryVO.getStart(),
                mongoLogQueryVO.getEnd(),
                mongoLogQueryVO.getPath(),
                mongoLogQueryVO.getIp(),
                buildPageRequest(mongoLogQueryVO.getPage(), mongoLogQueryVO.getCount())
        );
    }

    private PageRequest buildPageRequest(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("createTime")));
    }

}
