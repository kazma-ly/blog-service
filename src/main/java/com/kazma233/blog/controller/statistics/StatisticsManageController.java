package com.kazma233.blog.controller.statistics;

import com.kazma233.blog.dao.mongo.MongoLogDao;
import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.mongo.MongoLog;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.vo.log.MongoLogQueryVO;
import com.kazma233.blog.vo.log.MongoWebsiteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsManageController {

    @Autowired
    private MongoLogDao mongoLogDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/")
    public BaseResult article(MongoLogQueryVO mongoLogQueryVO) {
        mongoLogQueryVO.init();
        List<MongoLog> mongoLogs = mongoLogDao.findAllByVisitTimeBetweenAndPathLikeAndIpLikeOrderByVisitTimeDesc(
                mongoLogQueryVO.getStart(),
                mongoLogQueryVO.getEnd(),
                mongoLogQueryVO.getPath(),
                mongoLogQueryVO.getIp()
        );

        return BaseResult.createResult(1, mongoLogs);
    }

    @GetMapping("/website")
    public BaseResult webSiteInfo(MongoLogQueryVO mongoLogQueryVO) {
        mongoLogQueryVO.init();

        Long allViewSize = mongoLogDao.countAllByVisitTimeBetween(mongoLogQueryVO.getStart(), mongoLogQueryVO.getEnd());

        Criteria criteria = Criteria.where("visitTime").gt(mongoLogQueryVO.getStart()).lt(mongoLogQueryVO.getEnd());
        List<String> ips = mongoTemplate.findDistinct(new Query(criteria), "ip", MongoLog.class, String.class);

        MongoWebsiteInfo websiteInfo = MongoWebsiteInfo.builder().allApiRequestSize(allViewSize).allIpRequestSize(ips.size()).build();

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, websiteInfo);
    }
}
